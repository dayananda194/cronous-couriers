package org.syncron.cronouscouriers.enums;

public enum PackageStatus {

    PENDING,      // Waiting for rider assignment
    ASSIGNED,     // Assigned to a rider ( we can think of it matches AssignmentStatus.ASSIGNED)
    IN_TRANSIT,   // Rider is transporting it (matches PICKED_UP in AssigmentStatus.PICKED)
    DELIVERED,    // Successfully delivered (matches DELIVERED)
    FAILED        // Delivery failed (matches FAILED)
}
