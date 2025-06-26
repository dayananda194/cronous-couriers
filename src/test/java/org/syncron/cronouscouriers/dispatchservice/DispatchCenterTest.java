package org.syncron.cronouscouriers.dispatchservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.syncron.cronouscouriers.entities.Package;
import org.syncron.cronouscouriers.entities.Rider;
import org.syncron.cronouscouriers.enums.PackageType;
import org.syncron.cronouscouriers.enums.RiderStatus;
import org.syncron.cronouscouriers.geo.Location;

import static org.junit.jupiter.api.Assertions.*;

public class DispatchCenterTest {

    private DispatchCenter dispatchCenter;

    @BeforeEach
    public void setUp() {
        dispatchCenter = new DispatchCenter();
    }

    @Test
    void testAddRider() {
        Rider rider = new Rider("Dayananda", 0.9, true, new Location(0, 0));
        dispatchCenter.addRider(rider);

        assertFalse(dispatchCenter.getRiders().isEmpty());
        assertEquals(rider, dispatchCenter.getRiders().get("Dayananda"));
    }

    @Test
    void testPlaceOrder(){

        Package pkg = new Package("Offer-Letter", PackageType.EXPRESS,System.currentTimeMillis(),System.currentTimeMillis()+60*60*1000,false,"Chittoor");
        dispatchCenter.placeOrder(pkg);
        assertFalse(dispatchCenter.getPendingPackages().isEmpty());
        assertEquals(pkg, dispatchCenter.getAllPackages().get("Offer-Letter"));

    }

    @Test
    void testPlaceOrderWithNoPackage(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()->dispatchCenter.placeOrder(null));
        assertEquals(exception.getMessage(),"Package cannot be null");

    }

    @Test
    void testPlaceSameOrderAgain(){

        Package pkg = new Package("Dayananda", PackageType.EXPRESS,System.currentTimeMillis(),System.currentTimeMillis(),false,"Chittoor");
        dispatchCenter.placeOrder(pkg);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()->dispatchCenter.placeOrder(pkg));
        assertEquals(exception.getMessage(),"Package with this ID already exists");
    }

    @Test
    void testUpdateRiderStatus(){

        Rider rider =   new Rider("Dayananda", 0.9, true, new Location(0, 0));
        dispatchCenter.addRider(rider);

        // now we can test the update rider status method

        dispatchCenter.updateRiderStatus(rider.getId(), RiderStatus.OFFLINE);
        assertEquals(RiderStatus.OFFLINE,rider.getStatus());

    }

    @Test
    void testUpdateRiderStatusWhenRiderNotAvailable(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()->dispatchCenter.updateRiderStatus("UnknownRiderID",RiderStatus.OFFLINE));
        assertEquals("Rider not found", exception.getMessage());
    }

}
