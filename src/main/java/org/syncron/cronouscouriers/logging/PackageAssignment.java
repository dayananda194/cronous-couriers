package org.syncron.cronouscouriers.logging;

import lombok.Getter;
import lombok.Setter;
import org.syncron.cronouscouriers.enums.PackageType;

@Getter
@Setter
public class PackageAssignment {

    String riderId;
    PackageType packageType;
    long assignmentTime;

    PackageAssignment(String riderId, PackageType packageType, long assignmentTime) {
        this.riderId = riderId;
        this.packageType = packageType;
        this.assignmentTime = assignmentTime;
    }
}
