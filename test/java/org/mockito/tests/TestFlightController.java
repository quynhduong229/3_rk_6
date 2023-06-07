package org.mockito.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import backend.Flights.Flight;
import backend.Flights.FlightController;
import backend.Flights.FlightRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author asher and Wyatt
 */
public class TestFlightController {

    @InjectMocks
    FlightController flightController;

    @Mock
    FlightRepository repo;

    @Before
    public void init() { MockitoAnnotations.initMocks(this); }

    @Test
    public void getFlightByIdTest() {
        when(repo.findById(1)).thenReturn(new Flight("Delta", 350.00, "12/1/2021", "Ames", "Minneapolis"));

        Flight flight  = flightController.getFlightById(1);

        assertEquals("Delta", flight.getAirlineName());
        assertEquals(350.00, flight.getPrice(), 0);
        assertEquals("12/1/2021", flight.getDate());
        assertEquals("Ames", flight.getDeparting());
        assertEquals("Minneapolis", flight.getArriving());
    }

    @Test
    public void getAllFlightTest() {
        List<Flight> list = new ArrayList<>();
        Flight flightOne = new Flight("Delta", 350.00, "12/1/2021", "Ames", "Minneapolis");
        Flight flightTwo = new Flight("American", 300.00, "12/5/2021", "Minneapolis", "Lutsen");
        Flight flightThree = new Flight("United", 275.00, "12/10/2021", "Lutsen", "Ely");

        list.add(flightOne);
        list.add(flightTwo);
        list.add(flightThree);

        when(repo.findAll()).thenReturn(list);

        List<Flight> flightList = flightController.getAllFlights();

        assertEquals(3, flightList.size());
        verify(repo, times(1)).findAll();
    }

    /**
     * @author asher
     */
    @Test
    public void getMockFlightTest(){
        FlightController flightController = new FlightController();
        try {
            List<Flight> list = flightController.getMockFLights("X", "X", "X");

            assertEquals(51, list.size());
            assertEquals(255,Math.round(list.get(0).getPrice())); //Actual value is 255.22744352 but assertEquals for doubles is deprecated, hence the rounding.
            assertEquals("Spirit Airlines",list.get(2).getAirlineName());
            assertEquals("X", list.get(0).getDeparting());

        } catch (IOException e){
            assertEquals("this really shouldn't actually be possible","but if this actually happens, something went horribly, horribly wrong");
        }catch ( InterruptedException e2){
            assertEquals("this really shouldn't actually be possible","time to switch majors...");
        }
    }

}
