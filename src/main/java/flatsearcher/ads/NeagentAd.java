package flatsearcher.ads;

/**
 * @author by_drak
 * @version 1.00
 */

public class NeagentAd extends Ad {
    private String address; //the field is added because there is an appropriate column at neagent.by
    private String numberOfRooms; //the field is added because there is an appropriate column at neagent.by


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumberOfRooms(String numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }
}
