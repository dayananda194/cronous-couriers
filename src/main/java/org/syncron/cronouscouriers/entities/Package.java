package org.syncron.cronouscouriers.entities;

import lombok.Getter;
import lombok.Setter;
import org.syncron.cronouscouriers.enums.PackageStatus;
import org.syncron.cronouscouriers.enums.PackageType;

@Getter
@Setter

public class Package {

    private final String id;
    private PackageType type;
    private final long orderTime;
    private final long deadline;
    private final boolean isFragile;
    private PackageStatus status;
    private final String destination;

    public Package(String id,PackageType type , long orderTime , long deadline,boolean isFragile,String destination){

        this.id = id;
        this.type = type;
        this.orderTime = orderTime;
        this.deadline = deadline;
        this.isFragile = isFragile;
        this.status = PackageStatus.PENDING;
        this.destination = destination;
    }

    @Override
    public String toString() {

        return "[ Id : "+id+", Type:"+type+" ,OrderTime: "+orderTime+" ,isFragile : " + isFragile + " ,Status :"  + status + ", Destination : " +destination+"]";
    }

}
