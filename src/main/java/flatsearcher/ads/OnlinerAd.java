package flatsearcher.ads;

/**
 * @author by_drak
 * @version 1.00
 */

public class OnlinerAd extends Ad {
    private String address; //the field is added because there is an appropriate column at onliner.by

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
