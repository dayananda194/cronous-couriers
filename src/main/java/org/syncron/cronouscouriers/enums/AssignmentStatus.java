package org.syncron.cronouscouriers.enums;

public enum AssignmentStatus {
    ASSIGNED,     // Package assigned to rider but not yet picked up
    PICKED_UP,    // Rider has collected the package
    DELIVERED,    // Successfully delivered to destination
    FAILED        // Delivery attempt failed (returned/lost)
}
