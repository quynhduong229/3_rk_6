package org.mockito.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import backend.Flights.Flight;
import backend.Lodgings.Lodging;
import backend.Lodgings.LodgingType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import backend.Trips.Trip;
import backend.Trips.TripRepository;
import backend.Trips.TripController;

public class TestTripController {

    @InjectMocks
    TripController tripController;

    @Mock
    TripRepository repo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getTripByIdTest() {
        Lodging lodging = new Lodging("AmericInn", 1000, "Tuesday", "Friday", "Atlanta", LodgingType.HOTEL);
        Flight flight = new Flight("Delta", 1000, "2021-11-1", "Monday", "Saturday");
        when(repo.findById(1)).thenReturn(new Trip(lodging, flight, "4 days", lodging.getLocation()));

        Trip trip = tripController.getTripById(1);

        // Check Lodging Information
        assertEquals("AmericInn", trip.getLodging().getName());
        assertEquals(1000, trip.getLodging().getPrice(), 0);
        assertEquals("Tuesday", trip.getLodging().getCheckIn());
        assertEquals("Friday", trip.getLodging().getCheckOut());
        assertEquals("Atlanta", trip.getLodging().getLocation());
        assertEquals(LodgingType.HOTEL, trip.getLodging().getType());

        // Check Flight Information
        assertEquals("Delta", trip.getFlight().getAirlineName());
        assertEquals(1000, trip.getFlight().getPrice(), 0);
        assertEquals("2021-11-1", trip.getFlight().getDate());
        assertEquals("Monday", trip.getFlight().getDeparting());
        assertEquals("Saturday", trip.getFlight().getArriving());

        // Check Trip Information
        assertEquals("4 days", trip.getDuration());
        assertEquals("Atlanta", trip.getLocation());

    }

    @Test
    public void getAllTripsTest() {
        List<Trip> list = new ArrayList<>();
        Lodging lodging = new Lodging("AmericInn", 1000, "Tuesday", "Friday", "Atlanta", LodgingType.HOTEL);
        Flight flight = new Flight("Delta", 1000, "2021-11-1", "Monday", "Saturday");
        Trip trip1 = new Trip(lodging, flight, "4 days", lodging.getLocation());
        trip1.setId(1);
        Trip trip2 = new Trip(lodging, flight, "4 days", lodging.getLocation());
        trip2.setId(2);
        Trip trip3 = new Trip(lodging, flight, "4 days", lodging.getLocation());
        trip3.setId(3);

        list.add(trip1);
        list.add(trip2);
        list.add(trip3);

        when(repo.findAll()).thenReturn(list);

        List<Trip> tripsList = tripController.getAllTrips();

        assertEquals(3, tripsList.size());
        assertEquals(1, tripsList.get(0).getId());
        assertEquals(2, tripsList.get(1).getId());
        assertEquals(3, tripsList.get(2).getId());
    }

}
