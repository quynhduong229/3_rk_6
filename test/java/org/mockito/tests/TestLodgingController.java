package org.mockito.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import backend.Lodgings.LodgingType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import backend.Lodgings.Lodging;
import backend.Lodgings.LodgingController;
import backend.Lodgings.LodgingRepository;

/**
 * @author asher and Wyatt
 */
public class TestLodgingController {

    @InjectMocks
    LodgingController lodgingController;

    @Mock
    LodgingRepository repo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getLodgingByIdTest() {
        when(repo.findById(1)).thenReturn(new Lodging("Hilton", 150.00, "12/1/2021", "12/5/2021", "Minneapolis", LodgingType.HOTEL));

        Lodging lodging = lodgingController.getLodgingById(1);

        assertEquals("Hilton", lodging.getName());
        assertEquals(150.00, lodging.getPrice(), 0);
        assertEquals("12/1/2021", lodging.getCheckIn());
        assertEquals("12/5/2021", lodging.getCheckOut());
        assertEquals("Minneapolis", lodging.getLocation());
        assertEquals(LodgingType.HOTEL, lodging.getType());
    }

    @Test
    public void getAllLodgingsByIdTest() {
        List<Lodging> list = new ArrayList<>();
        Lodging lodgeOne = new Lodging("Hilton", 150.00, "12/1/2021", "12/5/2021", "Minneapolis", LodgingType.HOTEL);
        Lodging lodgeTwo = new Lodging("Phil's BNB", 100.00, "12/5/2021", "12/10/2021", "Lutsen", LodgingType.BNB);
        Lodging lodgeThree = new Lodging("Loggers Log Cabins", 250.00, "12/10/2021", "12/15/2021", "Ely", LodgingType.CABIN);

        list.add(lodgeOne);
        list.add(lodgeTwo);
        list.add(lodgeThree);

        when(repo.findAll()).thenReturn(list);

        List<Lodging> acctList = lodgingController.getAllLodgings();

        assertEquals(3, acctList.size());
        verify(repo, times(1)).findAll();
    }

    /**
     * @author asher
     */
    @Test
    public void getRealLodgingsTest(){
        LodgingController lodgingController = new LodgingController();
        List<Lodging> list = lodgingController.getRealLodgings("chicago","2022-01-12","2022-01-15");

        System.out.println(list);

    }

}
