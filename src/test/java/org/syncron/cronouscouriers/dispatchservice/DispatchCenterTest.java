package org.syncron.cronouscouriers.dispatchservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.syncron.cronouscouriers.entities.Assignment;
import org.syncron.cronouscouriers.entities.Package;
import org.syncron.cronouscouriers.entities.Rider;
import org.syncron.cronouscouriers.enums.AssignmentStatus;
import org.syncron.cronouscouriers.enums.PackageStatus;
import org.syncron.cronouscouriers.enums.PackageType;
import org.syncron.cronouscouriers.enums.RiderStatus;
import org.syncron.cronouscouriers.geo.Location;
import org.syncron.cronouscouriers.logging.PackageDeliveryAudit;

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

    @Test
    void testPackageAssignedToRider(){

        Package pkg = new Package("Offer-Letter", PackageType.EXPRESS,System.currentTimeMillis(),System.currentTimeMillis(),true,"Chittoor");
        assertTrue(dispatchCenter.getAssignments().isEmpty()); // before the rider is available , the assigment should not happen
        dispatchCenter.placeOrder(pkg);
        Rider rider = new Rider("Dayananda", 0.9, false, new Location(0, 0));
        dispatchCenter.addRider(rider);
        assertTrue(dispatchCenter.getAssignments().isEmpty()); // since fragile items handled drivers are not available , no assigment should be done
        Rider fragileRider = new Rider("Naveen", 0.9, true, new Location(0, 0));
        dispatchCenter.addRider(fragileRider); // now the assigment should happen
        System.out.println("List of assignments are "+ dispatchCenter.getAssignments());
        assertFalse(dispatchCenter.getAssignments().isEmpty());
        assertEquals(fragileRider.getStatus(),RiderStatus.BUSY);

    }

    @Test
    void testRecordDelivery(){

        Package pkg = new Package("Offer-letter", PackageType.EXPRESS,System.currentTimeMillis(),System.currentTimeMillis(),false,"Chittoor");
        Rider rider = new Rider("Dayananda", 0.9, true, new Location(0, 0));
        dispatchCenter.placeOrder(pkg);
        dispatchCenter.addRider(rider);
        Assignment assignment = dispatchCenter.getAssignments().get(0);
        dispatchCenter.recordDelivery(assignment.getId(),false);
        assertFalse(dispatchCenter.getAssignments().isEmpty());
        assertEquals(PackageStatus.DELIVERED,pkg.getStatus());
        assertEquals(RiderStatus.AVAILABLE,rider.getStatus()); // since there is no package , rider will be avaiable
        assertEquals(PackageDeliveryAudit.deliveryEvents.size(),1);
        assertFalse(PackageDeliveryAudit.statusChanges.isEmpty());
    }

    @Test
    void testRecordPickUp(){
        Package pkg = new Package("Offer-Letter", PackageType.EXPRESS,System.currentTimeMillis(),System.currentTimeMillis(),false,"Chittoor");
        Rider rider = new Rider("Dayananda", 0.9, true, new Location(0, 0));
        dispatchCenter.placeOrder(pkg);
        dispatchCenter.addRider(rider);
        Assignment assignment = dispatchCenter.getAssignments().getFirst();
        assertEquals(assignment.getStatus(), AssignmentStatus.ASSIGNED);
        assertEquals(assignment.getPickupTime(),0);
        dispatchCenter.recordPickup(assignment.getId());
        assertEquals(pkg.getStatus(), PackageStatus.IN_TRANSIT);
        assertNotEquals(0, assignment.getPickupTime());

    }

    @Test
    void testRecordDeliveryFailure(){
        Package pkg = new Package("Offer-letter", PackageType.EXPRESS,System.currentTimeMillis(),System.currentTimeMillis(),false,"Chittoor");
        Rider rider = new Rider("Dayananda", 0.9, true, new Location(0, 0));
        dispatchCenter.placeOrder(pkg);
        dispatchCenter.addRider(rider);
        Assignment assignment = dispatchCenter.getAssignments().getFirst();
        assertEquals(assignment.getStatus(), AssignmentStatus.ASSIGNED);
        dispatchCenter.recordDeliveryFailure(assignment.getId());
        assertEquals(assignment.getStatus(), AssignmentStatus.FAILED);
        assertEquals(rider.getStatus(), RiderStatus.AVAILABLE);

    }

}
