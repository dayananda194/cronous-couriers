package org.syncron.cronouscouriers.entities;

import org.junit.jupiter.api.Test;
import org.syncron.cronouscouriers.geo.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RiderTest {

    @Test
    public void testRider() {

        Rider rider = new Rider("Dayananda",0.6,true,new Location( -34.23,56.234));
        assertEquals("Dayananda",rider.getId());
        assertEquals(0.6,rider.getReliabilityRating());
        assertEquals(true,rider.isCanHandleFragile());
        assertEquals(-34.23,rider.getCurrentLocation().getLatitude());
        assertEquals(56.234,rider.getCurrentLocation().getLongitude());


    }

    @Test
    public void testRiderToString(){

        Rider rider = new Rider("Dayananda",0.6,true,new Location( -34.23,56.234));
        String riderToString = rider.toString();
        assertTrue(riderToString.contains("Dayananda"));
        assertTrue(riderToString.contains("0.6"));
        assertTrue(riderToString.contains("-34.23"));
        assertTrue(riderToString.contains("56.234"));
        assertTrue(riderToString.contains("true"));
    }
}
