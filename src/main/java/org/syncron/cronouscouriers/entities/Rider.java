package org.syncron.cronouscouriers.entities;

import lombok.Getter;
import lombok.Setter;
import org.syncron.cronouscouriers.enums.RiderStatus;
import org.syncron.cronouscouriers.geo.Location;


@Getter
@Setter
public class Rider {
    private final String id;
    private RiderStatus status;
    private final double reliabilityRating;
    private final boolean canHandleFragile;
    private Location currentLocation;

    public Rider(String id, double reliabilityRating, boolean canHandleFragile, Location location) {
        this.id = id;
        this.reliabilityRating = reliabilityRating;
        this.canHandleFragile = canHandleFragile;
        this.currentLocation = location;
        this.status = RiderStatus.AVAILABLE;
    }

    @Override
    public String toString(){

        return "[ Id : "+id+",Rider Status :"+status+",Reliability Rating : "+reliabilityRating + ", Can Handle Fragile :" + canHandleFragile + " " +currentLocation.toString() +" ]";
    }
}
