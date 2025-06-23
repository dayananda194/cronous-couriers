package org.syncron.cronouscouriers.entities;

import lombok.Getter;
import lombok.Setter;
import org.syncron.cronouscouriers.enums.AssignmentStatus;

@Getter
@Setter
public class Assignment {

    private final String id;
    private final String packageId;
    private final String riderId;
    private long pickupTime;
    private long deliveryTime;
    private AssignmentStatus status;

    public Assignment(String id, String packageId, String riderId) {
        this.id = id;
        this.packageId = packageId;
        this.riderId = riderId;
        this.status = AssignmentStatus.ASSIGNED;
    }
}
