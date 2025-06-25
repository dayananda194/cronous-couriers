package org.syncron.cronouscouriers.geo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {

    private double latitude; //  x - axis   , -90 to 90 degrees of equator
    private double longitude; // y-axis also called meridians  , ranges from -180  degrees to 180 degrees of prime meridian

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString(){

        return ",[ Latitude : "+ latitude + " Longitude : " + longitude +"] ";
    }

}
