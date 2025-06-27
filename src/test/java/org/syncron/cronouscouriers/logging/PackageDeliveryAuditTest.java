package org.syncron.cronouscouriers.logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.syncron.cronouscouriers.dispatchservice.DispatchCenter;
import org.syncron.cronouscouriers.entities.Package;
import org.syncron.cronouscouriers.entities.Rider;
import org.syncron.cronouscouriers.enums.PackageType;
import org.syncron.cronouscouriers.geo.Location;

import java.sql.SQLOutput;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackageDeliveryAuditTest {


    DispatchCenter dispatchCenter;
    @BeforeEach
    public void init() {
        dispatchCenter = new DispatchCenter();
    }
    @Test
    public void testPackagesDeliveredByRider() {

        Package pkg = new Package("Offer-letter", PackageType.EXPRESS,System.currentTimeMillis(),System.currentTimeMillis(),false,"Chittoor");
        Rider rider = new Rider("Dayananda", 0.9, true, new Location(0, 0));
        dispatchCenter.placeOrder(pkg);
        dispatchCenter.addRider(rider);
        dispatchCenter.recordPickup(dispatchCenter.getAssignments().getFirst().getId());
        dispatchCenter.recordDelivery(dispatchCenter.getAssignments().getFirst().getId(),false);
        List<String> packages= PackageDeliveryAudit.getPackagesDeliveredByRider(rider.getId(),System.currentTimeMillis()-60*60*1000);
        assertFalse(packages.isEmpty());

    }

    @Test
    public void testLateExpressPackages(){

        Package pkg = new Package("Offer-letter", PackageType.EXPRESS,System.currentTimeMillis(),System.currentTimeMillis(),false,"Chittoor");
        Rider rider = new Rider("Dayananda", 0.9, true, new Location(0, 0));
        dispatchCenter.placeOrder(pkg);
        dispatchCenter.addRider(rider);
        dispatchCenter.recordPickup(dispatchCenter.getAssignments().getFirst().getId());
        dispatchCenter.recordDelivery(dispatchCenter.getAssignments().getFirst().getId(),true);
        List<String> expresPackages = PackageDeliveryAudit.getLateExpressPackages(System.currentTimeMillis()-60*60*1000);
        System.out.println( "Expres packages" + expresPackages);
        //assertTrue(expresPackages.isEmpty());
    }

}
