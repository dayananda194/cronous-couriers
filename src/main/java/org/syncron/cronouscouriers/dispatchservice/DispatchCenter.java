package org.syncron.cronouscouriers.dispatchservice;


import lombok.Getter;
import lombok.Setter;
import org.syncron.cronouscouriers.entities.Assignment;
import org.syncron.cronouscouriers.entities.Rider;
import org.syncron.cronouscouriers.entities.Package;
import org.syncron.cronouscouriers.enums.AssignmentStatus;
import org.syncron.cronouscouriers.enums.PackageStatus;
import org.syncron.cronouscouriers.enums.RiderStatus;
import org.syncron.cronouscouriers.logging.EventType;
import org.syncron.cronouscouriers.logging.PackageDeliveryAudit;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class DispatchCenter {

    private final PriorityQueue<Package> pendingPackages;
    private final Map<String, Rider> riders;
    private final Map<String, Package> allPackages;
    private final List<Assignment> assignments;
    private int assignmentCounter;


    public DispatchCenter() {
        this.pendingPackages = new PriorityQueue<>(
                (p1, p2) -> {
                    if (p1.getType() != p2.getType()) {
                        return p2.getType().compareTo(p1.getType()); // EXPRESS first
                    }
                    if (p1.getDeadline() != p2.getDeadline()) {
                        return Long.compare(p1.getDeadline(), p2.getDeadline()); // Sooner first
                    }
                    return Long.compare(p1.getOrderTime(), p2.getOrderTime()); // Older first
                }
        );
        this.riders = new HashMap<>();
        this.allPackages = new HashMap<>();
        this.assignments = new ArrayList<>();
        this.assignmentCounter = 1;
    }
    public void placeOrder(Package pkg) {
        if (pkg == null) {
            throw new IllegalArgumentException("Package cannot be null");
        }
        if (allPackages.containsKey(pkg.getId())) {
            throw new IllegalArgumentException("Package with this ID already exists");
        }
        pendingPackages.add(pkg);
        allPackages.put(pkg.getId(), pkg);
        System.out.println("Package Created Successfully : " + pkg.toString() );
        PackageDeliveryAudit.logStatusChange(pkg.getId(), null, pkg.getStatus(),System.currentTimeMillis());
    }

    public void updateRiderStatus(String riderId, RiderStatus newStatus) {
        Rider rider = riders.get(riderId);
        if (rider == null) {
            throw new IllegalArgumentException("Rider not found");
        }
        rider.setStatus(newStatus);
        // If rider became available, try to assign a package
        if (newStatus == RiderStatus.AVAILABLE) {
            assignPackage();
        }
        System.out.println("Rider Updated Successfully : " + rider.toString());
    }
    public Optional<Assignment> assignPackage() {

        if (pendingPackages.isEmpty()) {
            return Optional.empty();
        }

        Package pkg = pendingPackages.peek(); // Peek without removing
        List<Rider> suitableRiders = findSuitableRiders(pkg);

        if (suitableRiders.isEmpty()) {
            return Optional.empty();
        }

        // Select the best rider (simple strategy: highest reliability rating)
        Rider bestRider = suitableRiders.stream()
                .max(Comparator.comparingDouble(Rider::getReliabilityRating))
                .get();

        // Create assignment
        pendingPackages.poll(); // Now remove the package
        pkg.setStatus(PackageStatus.ASSIGNED);

        String assignmentId = "ASSIGN-" + assignmentCounter++;
        Assignment assignment = new Assignment(assignmentId, pkg.getId(), bestRider.getId());
        assignments.add(assignment);

        bestRider.setStatus(RiderStatus.BUSY);

        PackageDeliveryAudit.logStatusChange(pkg.getId(), PackageStatus.PENDING, PackageStatus.ASSIGNED,System.currentTimeMillis());
        PackageDeliveryAudit.logAssignment(pkg.getId(), bestRider.getId(),pkg.getType(),System.currentTimeMillis());
        return Optional.of(assignment);
    }


    public void recordPickup(String assignmentId) {
        Assignment assignment = findAssignment(assignmentId);
        Package pkg = allPackages.get(assignment.getPackageId());

        assignment.setPickupTime(System.currentTimeMillis());
        pkg.setStatus(PackageStatus.IN_TRANSIT); // if the driver picks up the package then it means it is in on the way to destination
        PackageDeliveryAudit.logAssignment(pkg.getId(),assignment.getRiderId(),pkg.getType(),System.currentTimeMillis());
        System.out.println("Package Picked Up Successfully : " + assignment.toString());
    }

    private Assignment findAssignment(String assignmentId) {
        return assignments.stream()
                .filter(a -> a.getId().equals(assignmentId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
    }


    private List<Rider> findSuitableRiders(Package pkg) {
        return riders.values().stream()
                .filter(r -> r.getStatus() == RiderStatus.AVAILABLE)
                .filter(r -> !pkg.isFragile() || r.isCanHandleFragile())
                .collect(Collectors.toList());
    }


    public void recordDelivery(String assignmentId,boolean isOnTime) {
        Assignment assignment = findAssignment(assignmentId);
        Package pkg = allPackages.get(assignment.getPackageId());
        Rider rider = riders.get(assignment.getRiderId());

        assignment.setDeliveryTime(System.currentTimeMillis());
        pkg.setStatus(PackageStatus.DELIVERED);
        rider.setStatus(RiderStatus.AVAILABLE);

        PackageDeliveryAudit.logDeliveryEvent(pkg.getId(), EventType.DELIVERY,System.currentTimeMillis(),isOnTime);
        PackageDeliveryAudit.logStatusChange(pkg.getId(), PackageStatus.IN_TRANSIT, PackageStatus.DELIVERED,System.currentTimeMillis());

        System.out.println("Package Delivery Successfully : " + pkg.toString());
        Optional<Assignment> newAssignment = assignPackage();
        System.out.println("Rider is Available , Trying to assign new package.... Is Package Assigned : " + newAssignment.isPresent()  );
        if(newAssignment.isPresent()) {
            System.out.println("Assigned Package is : " + newAssignment.get().toString());
        }
        // Rider is now available, try to assign another package

    }
    public void addRider(Rider rider) {
        if (rider == null) {
            throw new IllegalArgumentException("Rider cannot be null");
        }
        if (riders.containsKey(rider.getId())) {
            throw new IllegalArgumentException("Rider with this ID already exists");
        }
        riders.put(rider.getId(), rider);
        System.out.println("Rider Added Successfully : " +  rider.toString());
        Optional<Assignment> newAssignment = assignPackage();
        System.out.println("Trying to Assign new Package Since Rider is Added ....... Is Package Assigned : " + newAssignment.isPresent() );
        if(newAssignment.isPresent()) {
            System.out.println("Newly Assigned Package is : " + newAssignment.get().toString());
        }
    }

    public void recordDeliveryFailure(String assignmentId) {
        Assignment assignment = findAssignment(assignmentId);
        Package pkg = allPackages.get(assignment.getPackageId());
        Rider rider = riders.get(assignment.getRiderId());

        assignment.setStatus(AssignmentStatus.FAILED);
        pkg.setStatus(PackageStatus.FAILED);
        rider.setStatus(RiderStatus.AVAILABLE);

        PackageDeliveryAudit.logStatusChange(pkg.getId(), PackageStatus.IN_TRANSIT, PackageStatus.FAILED,System.currentTimeMillis());
        // Rider is now available, try to assign another package
        System.out.println("Package Delivery Failure : " + pkg.toString());
        Optional<Assignment> newAssigment = assignPackage();
        System.out.println("Trying to Assign new Package Since Rider is AVAILABLE....... Is Package Assigned : " + newAssigment.isPresent() );
        if(newAssigment.isPresent()) {
            System.out.println("Newly Assigned Package is : " + newAssigment.get().toString());
        }

    }

    // Reporting methods

    public List<Package> getPendingPackages() {
        return new ArrayList<>(pendingPackages);
    }

    public List<Assignment> getActiveAssignments() {
        return assignments.stream()
                .filter(a -> a.getStatus() != AssignmentStatus.DELIVERED &&
                        a.getStatus() != AssignmentStatus.FAILED)
                .collect(Collectors.toList());
    }



}
