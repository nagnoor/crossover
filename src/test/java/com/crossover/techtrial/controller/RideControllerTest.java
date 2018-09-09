package com.crossover.techtrial.controller;

import com.crossover.techtrial.com.crossover.techtrial.model.PersonModel;
import com.crossover.techtrial.com.crossover.techtrial.model.RideModel;
import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.techtrial.repositories.RideRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RideControllerTest {

    MockMvc mockMvc;

    @Mock
    private RideController rideController;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    RideRepository rideRepository;

    @Autowired
    PersonRepository personRepository;


    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();
    }

    @After
    public void deleteIds() {
        personRepository.deleteAll();
        personRepository.deleteAll();

    }


    @Test
    public void createNewRide_test() {
        PersonModel driver = new PersonModel("Driver", "driver@gmail.com", "12345");
        PersonModel rider = new PersonModel("Rider", "rider@gmail.com", "12345");

        HttpEntity<Object> dr = getHttpEntity(driver);
        ResponseEntity<PersonModel> driverModel = template.postForEntity("/api/person", dr, PersonModel.class);

        HttpEntity<Object> rd = getHttpEntity(rider);
        ResponseEntity<PersonModel> riderModel = template.postForEntity("/api/person", rd, PersonModel.class);


        RideModel rideObject = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusHours(1).toString(), 100l, driverModel.getBody(), riderModel.getBody());
        HttpEntity<Object> ride = getHttpEntity(rideObject);
        ResponseEntity<RideModel> rideModel = template.postForEntity(
                "/api/ride", ride, RideModel.class);

        Assert.assertEquals("Driver", rideModel.getBody().getDriver().getName());
        Assert.assertEquals("Rider", rideModel.getBody().getRider().getName());
        Assert.assertEquals(HttpStatus.OK, rideModel.getStatusCode());
    }

    @Test
    public void createNewRide_test_endTime_lessThan() {
        PersonModel driver = new PersonModel("Driver", "driver@gmail.com", "12345");
        PersonModel rider = new PersonModel("Rider", "rider@gmail.com", "12345");

        HttpEntity<Object> dr = getHttpEntity(driver);
        ResponseEntity<PersonModel> driverModel = template.postForEntity("/api/person", dr, PersonModel.class);

        HttpEntity<Object> rd = getHttpEntity(rider);
        ResponseEntity<PersonModel> riderModel = template.postForEntity("/api/person", rd, PersonModel.class);


        RideModel rideObject = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().toString(), 100l, driverModel.getBody(), riderModel.getBody());
        HttpEntity<Object> ride = getHttpEntity(rideObject);
        ResponseEntity<RideModel> rideModel = template.postForEntity(
                "/api/ride", ride, RideModel.class);

        Assert.assertEquals(HttpStatus.EXPECTATION_FAILED, rideModel.getStatusCode());
    }


    @Test
    public void getRideById_test() {
        PersonModel driver = new PersonModel("Driver", "driver@gmail.com", "12345");
        PersonModel rider = new PersonModel("Rider", "rider@gmail.com", "12345");
        HttpEntity<Object> dr = getHttpEntity(driver);
        ResponseEntity<PersonModel> driverModel = template.postForEntity("/api/person", dr, PersonModel.class);

        HttpEntity<Object> rd = getHttpEntity(rider);
        ResponseEntity<PersonModel> riderModel = template.postForEntity("/api/person", rd, PersonModel.class);


        RideModel rideObject = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(30).toString(), 100l, driverModel.getBody(), riderModel.getBody());
        HttpEntity<Object> ride = getHttpEntity(rideObject);
        ResponseEntity<RideModel> rideModel = template.postForEntity(
                "/api/ride", ride, RideModel.class);

        String url = "/api/ride/" + rideModel.getBody().getId();
        rideModel = template.getForEntity(url, RideModel.class);
        Assert.assertEquals(HttpStatus.OK, rideModel.getStatusCode());
        Assert.assertEquals(100, rideModel.getBody().getDistance().intValue());

    }


    @Test
    public void getRideById_test_negative() {
        PersonModel driver = new PersonModel("Driver", "driver@gmail.com", "12345");
        PersonModel rider = new PersonModel("Rider", "rider@gmail.com", "12345");
        HttpEntity<Object> dr = getHttpEntity(driver);
        ResponseEntity<PersonModel> driverModel = template.postForEntity("/api/person", dr, PersonModel.class);

        HttpEntity<Object> rd = getHttpEntity(rider);
        ResponseEntity<PersonModel> riderModel = template.postForEntity("/api/person", rd, PersonModel.class);

        RideModel rideObject = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusHours(2).toString(), 100l, driverModel.getBody(), riderModel.getBody());
        HttpEntity<Object> ride = getHttpEntity(rideObject);
        ResponseEntity<RideModel> rideModel = template.postForEntity(
                "/api/ride", ride, RideModel.class);

        rideRepository.deleteById(rideModel.getBody().getId());
        String url = "/api/ride/" + rideModel.getBody().getId();
        rideModel = template.getForEntity(url, RideModel.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, rideModel.getStatusCode());

    }

    @Test
    public void getTopDriver_test() {

        PersonModel person1 = new PersonModel("Driver1", "driver1@gmail.com", "12345");
        PersonModel person2 = new PersonModel("Driver2", "driver2@gmail.com", "12346");
        PersonModel person3 = new PersonModel("Driver3", "driver3@gmail.com", "12347");
        PersonModel person4 = new PersonModel("Driver4", "driver4@gmail.com", "12348");
        PersonModel person5 = new PersonModel("Driver5", "driver5@gmail.com", "12349");
        PersonModel person6 = new PersonModel("Driver6", "driver6@gmail.com", "12310");

        PersonModel rider = new PersonModel("Rider", "rider@gmail.com", "12311");

        //Adding Rider and drivers
        ResponseEntity<PersonModel> resRider = template.postForEntity("/api/person", getHttpEntity(rider), PersonModel.class);

        ResponseEntity<PersonModel> ds1 = template.postForEntity("/api/person", getHttpEntity(person1), PersonModel.class);

        ResponseEntity<PersonModel> ds2 = template.postForEntity("/api/person", getHttpEntity(person2), PersonModel.class);

        ResponseEntity<PersonModel> ds3 = template.postForEntity("/api/person", getHttpEntity(person3), PersonModel.class);

        ResponseEntity<PersonModel> ds4 = template.postForEntity("/api/person", getHttpEntity(person4), PersonModel.class);

        ResponseEntity<PersonModel> ds5 = template.postForEntity("/api/person", getHttpEntity(person5), PersonModel.class);

        ResponseEntity<PersonModel> ds6 = template.postForEntity("/api/person", getHttpEntity(person6), PersonModel.class);


        RideModel ride1 = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusHours(1).toString(), 22l, ds1.getBody(), resRider.getBody());
        RideModel ride2 = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusHours(2).toString(), 15l, ds1.getBody(), resRider.getBody());
        RideModel ride3 = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(30).toString(), 38l, ds1.getBody(), resRider.getBody());
        RideModel ride4 = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(10).toString(), 42l, ds2.getBody(), resRider.getBody());
        RideModel ride5 = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusHours(15).toString(), 99l, ds3.getBody(), resRider.getBody());
        RideModel ride6 = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(20).toString(), 12l, ds4.getBody(), resRider.getBody());
        RideModel ride7 = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusHours(1).toString(), 25l, ds5.getBody(), resRider.getBody());
        RideModel ride8 = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(13).toString(), 97l, ds6.getBody(), resRider.getBody());
        RideModel ride9 = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(35).toString(), 85l, ds2.getBody(), resRider.getBody());
        RideModel ride10 = new RideModel(LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(27).toString(), 15l, ds3.getBody(), resRider.getBody());


        template.postForEntity("/api/ride", getHttpEntity(ride1), RideModel.class);

        template.postForEntity("/api/ride", getHttpEntity(ride2), RideModel.class);

        template.postForEntity("/api/ride", getHttpEntity(ride3), RideModel.class);

        template.postForEntity("/api/ride", getHttpEntity(ride4), RideModel.class);

        template.postForEntity("/api/ride", getHttpEntity(ride5), RideModel.class);

        template.postForEntity("/api/ride", getHttpEntity(ride6), RideModel.class);

        template.postForEntity("/api/ride", getHttpEntity(ride7), RideModel.class);

        template.postForEntity("/api/ride", getHttpEntity(ride8), RideModel.class);

        template.postForEntity("/api/ride", getHttpEntity(ride9), RideModel.class);

        template.postForEntity("/api/ride", getHttpEntity(ride10), RideModel.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        URI uri = UriComponentsBuilder
                .fromUriString("/api/top-rides")
                .queryParam("max", 5l)
                .queryParam("startTime", LocalDateTime.now().minusDays(1).format(formatter).toString())
                .queryParam("endTime", LocalDateTime.now().plusDays(1).format(formatter).toString())
                .build()
                .toUri();

        ResponseEntity<TopDriverDTO[]> topDriverList = template.exchange(uri, HttpMethod.GET, null, TopDriverDTO[].class);
        Assert.assertNotNull(topDriverList.getBody());
        Assert.assertEquals(5, topDriverList.getBody().length);


    }

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }


}



