package org.syncron.cronouscouriers.logging;

import lombok.Getter;
import lombok.Setter;
import org.syncron.cronouscouriers.enums.PackageStatus;
import org.syncron.cronouscouriers.enums.PackageType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PackageDeliveryAudit {

    private static final List<StatusChangeRecord> statusChanges = new ArrayList<>();
    private static final List<DeliveryEvent> deliveryEvents = new ArrayList<>();
    private static final Map<String, PackageAssignment> packageAssignments = new HashMap<>();

    // Record package status changes
    public static void logStatusChange(String packageId, PackageStatus oldStatus,
                                       PackageStatus newStatus, long timestamp) {
        statusChanges.add(new StatusChangeRecord(packageId, oldStatus, newStatus, timestamp));
    }

    // Record delivery events (pickup/delivery)
    public static void logDeliveryEvent(String packageId, EventType eventType,
                                 long timestamp, boolean onTime) {
        deliveryEvents.add(new DeliveryEvent(packageId, eventType, timestamp, onTime));
    }

    // Record package assignment to rider
    public static void logAssignment(String packageId, String riderId,
                                     PackageType packageType, long timestamp) {
        packageAssignments.put(packageId, new PackageAssignment(riderId, packageType, timestamp));
    }

    // Query: Get all packages delivered by a specific rider in last 24 hours
    public static List<String>   getPackagesDeliveredByRider(String riderId, long sinceTime) {
        List<String> results = new ArrayList<>();

        for (DeliveryEvent event : deliveryEvents) {
            if (event.eventType == EventType.DELIVERY &&
                    event.timestamp >= sinceTime) {

                PackageAssignment assignment = packageAssignments.get(event.packageId);
                if (assignment != null && assignment.riderId.equals(riderId)) {
                    results.add(event.packageId);
                }
            }
        }
        return results;
    }

    // Query: Get all Express packages that missed delivery window
    public static List<String> getLateExpressPackages(long sinceTime) {
        List<String> results = new ArrayList<>();

        for (DeliveryEvent event : deliveryEvents) {
            if (event.eventType == EventType.DELIVERY &&
                    !event.onTime &&
                    event.timestamp >= sinceTime) {

                PackageAssignment assignment = packageAssignments.get(event.packageId);
                if (assignment != null && assignment.packageType == PackageType.EXPRESS) {
                    results.add(event.packageId);
                }
            }
        }
        return results;
    }



}
