package org.syncron.cronouscouriers.logging;

import lombok.Getter;
import lombok.Setter;
import org.syncron.cronouscouriers.enums.PackageStatus;

@Getter
@Setter
public class StatusChangeRecord {

    String packageId;
    PackageStatus oldStatus;
    PackageStatus newStatus;
    long timestamp;

    public StatusChangeRecord(String packageId, PackageStatus oldStatus,
                       PackageStatus newStatus, long timestamp) {
        this.packageId = packageId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.timestamp = timestamp;
    }
}
