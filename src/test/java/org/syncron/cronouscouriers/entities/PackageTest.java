package org.syncron.cronouscouriers.entities;

import org.junit.jupiter.api.Test;
import org.syncron.cronouscouriers.enums.PackageStatus;
import org.syncron.cronouscouriers.enums.PackageType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackageTest {

    @Test
    void testPackageCreation() {
        Package pkg = new Package("Offer-Letter", PackageType.EXPRESS,
                System.currentTimeMillis(), System.currentTimeMillis() + 3600000,
                true, "SyncronTechnologies");

        assertEquals("Offer-Letter", pkg.getId());
        assertEquals(PackageType.EXPRESS, pkg.getType());
        assertTrue(pkg.isFragile());
        assertEquals("SyncronTechnologies", pkg.getDestination());
        assertEquals(PackageStatus.PENDING, pkg.getStatus());
    }

    @Test
    void testPackageToString(){

        Package pkg = new Package("Bank-Passbook",PackageType.EXPRESS,System.currentTimeMillis(),System.currentTimeMillis()+60*60*1000,true,"Banglore");
        String pkgToString = pkg.toString();
        assertTrue(pkgToString.contains("Bank-Passbook"));
        assertTrue(pkgToString.contains("EXPRESS"));
        assertTrue(pkgToString.contains("true"));
        assertTrue(pkgToString.contains("Banglore"));
    }
}
