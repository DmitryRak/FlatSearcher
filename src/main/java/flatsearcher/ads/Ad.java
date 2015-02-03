package flatsearcher.ads;

/**
 * @author by_drak
 * @version 1.00
 */

public abstract class Ad {
    private String uniqueID;
    private int price;
    private String title;
    private String shortDescription;
    protected String url;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getUniqueID() {
        return uniqueID;
    }
}
