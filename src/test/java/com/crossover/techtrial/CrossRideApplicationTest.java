/**
 *
 */
package com.crossover.techtrial;

import com.crossover.techtrial.com.crossover.techtrial.model.PersonModel;
import com.crossover.techtrial.controller.PersonController;
import com.crossover.techtrial.controller.RideController;
import com.crossover.techtrial.dto.PersonDTO;
import com.crossover.techtrial.dto.RideDTO;
import com.crossover.techtrial.service.PersonServiceImpl;
import com.crossover.techtrial.service.RideServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author crossover
 */
@RunWith(MockitoJUnitRunner.class)
public class CrossRideApplicationTest {


    @InjectMocks
    @Spy
    private RideController rideController;

    @InjectMocks
    @Spy
    private PersonController personController;

    @Mock
    private RideServiceImpl rideService;

    @Mock
    private PersonServiceImpl personService;

    @Test(expected = Exception.class)
    public void person_save_excpetion_test() {

        PersonDTO personDTO = Mockito.mock(PersonDTO.class);
        Mockito.when(personService.save(personDTO)).thenThrow(Exception.class);
        personController.register(personDTO);
    }


    @Test(expected = Exception.class)
    public void person_find_excpetion_test() {
        Mockito.when(personService.findById(Mockito.anyLong())).thenThrow(Exception.class);
        personController.getPersonById(10l);
    }


    @Test(expected = Exception.class)
    public void ride_save_excpetion_test() {
        RideDTO rideDTO = Mockito.mock(RideDTO.class);
        Mockito.when(rideService.save(rideDTO)).thenThrow(Exception.class);
        rideService.save(rideDTO);
    }


    @Test(expected = Exception.class)
    public void ride_find_excpetion_test() {
        Mockito.when(rideService.findById(Mockito.anyLong())).thenThrow(Exception.class);
        rideController.getRideById(10l);
    }


}
