/**
 *
 */
package com.crossover.techtrial.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.crossover.techtrial.Util;
import com.crossover.techtrial.dto.PersonDTO;
import com.crossover.techtrial.dto.RideDTO;
import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.RideRepository;

/**
 * @author crossover
 */
@Service
public class RideServiceImpl implements RideService {

    @Autowired
    RideRepository rideRepository;

    @Autowired
    Util util;

    public RideDTO save(RideDTO ride) {
        Ride r = convertRideDTO(ride);
        Ride res = rideRepository.save(r);
        RideDTO dto = convertRide(res);
        return dto;
    }

    public RideDTO findById(Long rideId) {
        Optional<Ride> optionalRide = rideRepository.findById(rideId);
        if (optionalRide.isPresent()) {
            return convertRide(optionalRide.get());
        } else return null;
    }

    @Override
    public List<TopDriverDTO> getTopDrivers(Long max, LocalDateTime startDate, LocalDateTime endDate) {
        Iterator<Ride> allRides = rideRepository.findAll().iterator();
        List<Ride> allRidesList = new ArrayList<>();
        allRides.forEachRemaining(allRidesList::add);
        List<Ride> rideList = allRidesList.stream().filter(
                ride ->
                        ((startDate.isBefore(util.convertStringToLocalDateTime(ride.getStartTime())))
                                && endDate.isAfter(util.convertStringToLocalDateTime(ride.getEndTime())))
        ).collect(Collectors.toList());

        Set<Person> drivers = rideList.stream().map(Ride::getDriver).collect(Collectors.toSet());
        List<TopDriverDTO> topDriverDTOList = new ArrayList<>();
        for (Person driver : drivers) {
            List<Ride> driverSpecificRides = rideList.stream().filter(ride -> ride.getDriver().getId().equals(driver.getId())).collect(Collectors.toList());
            topDriverDTOList.add(createDriverDTO(driverSpecificRides, driver.getEmail(), driver.getName()));
        }


        if (!topDriverDTOList.isEmpty() && topDriverDTOList.size() > 5) {
            topDriverDTOList.sort((o1, o2) -> o2.getMaxRideDurationInSecods().compareTo(o1.getMaxRideDurationInSecods()));
            return topDriverDTOList.subList(0, 5);
        }
        return topDriverDTOList;
    }


    private TopDriverDTO createDriverDTO(List<Ride> rides, String email, String name) {
        Double avgDistance = 0d;
        Long totalRideDurationInSeconds = 0l;
        Long maxRideDurationInSecods = 0l;
        for (Ride ride : rides) {
            long endSeconds = (Timestamp.valueOf(util.convertStringToLocalDateTime(ride.getEndTime())).getTime() / 1000);
            long startSeconds = (Timestamp.valueOf(util.convertStringToLocalDateTime(ride.getStartTime())).getTime() / 1000);
            long duration = (endSeconds) - (startSeconds);
            totalRideDurationInSeconds = totalRideDurationInSeconds + duration;
            if (duration > maxRideDurationInSecods)
                maxRideDurationInSecods = duration;
            avgDistance = avgDistance + ride.getDistance();
        }
        avgDistance = (avgDistance / rides.size());
        return new TopDriverDTO(name,
                email,
                totalRideDurationInSeconds,
                maxRideDurationInSecods,
                avgDistance);
    }


    private Person convertPersonDTO(PersonDTO dto) {
        Person person = new Person();
        person.setRegistrationNumber(dto.getRegistrationNumber());
        person.setName(dto.getName());
        person.setEmail(dto.getEmail());
        person.setId(dto.getId());
        return person;
    }

    private PersonDTO convertPerson(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setRegistrationNumber(person.getRegistrationNumber());
        dto.setName(person.getName());
        dto.setEmail(person.getEmail());
        dto.setId(person.getId());
        return dto;
    }


    private RideDTO convertRide(Ride ride) {
        RideDTO r = new RideDTO();
        r.setStartTime(ride.getStartTime());
        r.setEndTime(ride.getEndTime());
        r.setDistance(ride.getDistance());
        r.setRider(convertPerson(ride.getRider()));
        r.setDriver(convertPerson(ride.getDriver()));
        r.setId(ride.getId());
        return r;
    }


    private Ride convertRideDTO(RideDTO dto) {
        Ride r = new Ride();
        r.setStartTime(dto.getStartTime());
        r.setEndTime(dto.getEndTime());
        r.setDistance(dto.getDistance());
        r.setRider(convertPersonDTO(dto.getRider()));
        r.setDriver(convertPersonDTO(dto.getDriver()));
        return r;
    }

}
