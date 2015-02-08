package flatsearcher.searchers;

import flatsearcher.ads.Ad;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class Searcher {
    protected String url;
    private String initialUrl;
    private String roomCount = "1,2,3,4,5";
    private int category = 1;
    private int currency;
    private int priceMin, priceMax;
    private boolean isTheFirstRun = true;
    private String name;
    private LinkedBlockingDeque<Ad> advertisements = new LinkedBlockingDeque<Ad>();
    private LinkedBlockingDeque<Ad> premiumAdvertisements = new LinkedBlockingDeque<Ad>();
    private ArrayList<String> pagesForSearching = new ArrayList<String>();
    private int theFirstAd;
    private int theFirstPremiumAd;
    private String stub = "NULL";
    private int sleepInterval = 10000;
    private boolean isSearchEnabled = true;
    private boolean isPremiumEnabled = false;

    public int getTheFirstPremiumAd() {
        return theFirstPremiumAd;
    }

    public void setTheFirstPremiumAd(int theFirstPremiumAd) {
        this.theFirstPremiumAd = theFirstPremiumAd;
    }

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

    public int getTheFirstAd() {
        return theFirstAd;
    }

    public void setTheFirstAd(int theFirstAd) {
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
    public String getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(String roomCount) {
        this.roomCount = roomCount;
    }

    public String getInitialUrl() {
        return initialUrl;
    }

    public void setInitialUrl(String initialUrl) {
        this.initialUrl = initialUrl;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(int priceMin) {
        this.priceMin = priceMin;
    }

    public int getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(int priceMax) {
        this.priceMax = priceMax;
    }

    public boolean isTheFirstRun() {
        return isTheFirstRun;
    }

    public void setTheFirstRun(boolean isTheFirstRun) {
        this.isTheFirstRun = isTheFirstRun;
    }
    public boolean isPremiumEnabled() {
        return isPremiumEnabled;
    }

    public void setPremiumEnabled(boolean isPremiumEnabled) {
        this.isPremiumEnabled = isPremiumEnabled;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdvertisements(LinkedBlockingDeque<Ad> advertisements) {
        this.advertisements = advertisements;
    }

    public void setPremiumAdvertisements(LinkedBlockingDeque<Ad> premiumAdvertisements) {
        this.premiumAdvertisements = premiumAdvertisements;
    }

    public ArrayList<String> getPagesForSearching() {
        return pagesForSearching;
    }

    public void setPagesForSearching(ArrayList<String> pagesForSearching) {
        this.pagesForSearching = pagesForSearching;
    }
}
