package org.syncron.cronouscouriers.logging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class DeliveryEvent {
    String packageId;
    EventType eventType; // PICKUP or DELIVERY
    long timestamp;
    boolean onTime;

    DeliveryEvent(String packageId, EventType eventType,
                  long timestamp, boolean onTime) {
        this.packageId = packageId;
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.onTime = onTime;
    }
}
