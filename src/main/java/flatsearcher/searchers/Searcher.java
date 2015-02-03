package flatsearcher.searchers;

import flatsearcher.ads.Ad;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class Searcher {
    protected String url;

    protected String name;
    protected LinkedBlockingDeque<Ad> advertisements = new LinkedBlockingDeque<Ad>();
    protected LinkedBlockingDeque<Ad> premiumAdvertisements = new LinkedBlockingDeque<Ad>();
    protected ArrayList<String> pagesForSearching = new ArrayList<String>();
    protected String theFirstAd = "";

    public String getTheFirstPremiumAd() {
        return theFirstPremiumAd;
    }

    public void setTheFirstPremiumAd(String theFirstPremiumAd) {
        this.theFirstPremiumAd = theFirstPremiumAd;
    }

    protected String theFirstPremiumAd = "";
    protected String stub = "NULL";
    protected int sleepInterval = 10000;
    protected boolean isSearchEnabled = true;
    protected boolean isPremiumEnabled = false;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public LinkedBlockingDeque<Ad> getAdvertisements() {
        return advertisements;
    }

    public abstract void findAds() throws IOException, SocketTimeoutException;

    public String getTheFirstAd() {
        return theFirstAd;
    }

    public void setTheFirstAd(String theFirstAd) {
        this.theFirstAd = theFirstAd;
    }

    public int getSleepInterval() {
        return sleepInterval;
    }

    public void setSleepInterval(int sleepInterval) {
        this.sleepInterval = sleepInterval;
    }

    public boolean isSearchEnabled() {
        return isSearchEnabled;
    }

    public void setSearchEnabled(boolean isSearchEnabled) {
        this.isSearchEnabled = isSearchEnabled;
    }

    public LinkedBlockingDeque<Ad> getPremiumAdvertisements() {
        return premiumAdvertisements;
    }


    public ArrayList<String> getPages() {
        return pagesForSearching;
    }

}
