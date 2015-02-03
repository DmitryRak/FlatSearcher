package flatsearcher.searchers;

import flatsearcher.ads.NeagentAd;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NeagentSearcher extends Searcher {
    private String roomCount = "1,2,3,4,5";
    private String initialUrl;
    private int category = 1;
    private int currency = 2;
    private int priceMin, priceMax;
    private boolean isTheFirstRun = true;
    private int loadTimeOut = 20;


    public NeagentSearcher(String url, String searcherName) {
        if(null ==searcherName){
            searcherName = "Neagent.by";
        }
        this.name = searcherName;

        if(null == url){
            url = "http://neagent.by/board/minsk/?catid="+category;
        }else{
            this.initialUrl = url;
            url+="catid="+category+"&";
            url+="roomCount="+roomCount;

            if(0 != priceMin){
                this.url+="&priceMin="+priceMin+"&";
                if(0 != priceMax){
                    this.url+="priceMax="+priceMax+"&";
                    this.url+="currency="+currency;
                } else{
                    this.url+="currency="+currency;
                }
            }else  if(0 != priceMax){
                this.url+="priceMax="+priceMax+"&";
                this.url+="currency="+currency;
            }
        }
        this.url = url;
    }
    public String updateUrl(){
        this.url=initialUrl+"catid="+category+"&";
        this.url+="roomCount="+roomCount;

        if(0 != priceMin){
            this.url+="&priceMin="+priceMin+"&";
            if(0 != priceMax){
                this.url+="priceMax="+priceMax+"&";
                this.url+="currency="+currency;
            } else{
                this.url+="currency="+currency;
            }
        }else  if(0 != priceMax){
            this.url+="priceMax="+priceMax+"&";
            this.url+="currency="+currency;
        }

        return this.url;
    }
    private void cleanListOfAds(){
        this.getAdvertisements().clear();
        this.getPremiumAdvertisements().clear();
    }
    private int getPagesFromPagination(String paginationClass) throws IOException {
        this.getPages().clear();
        Document doc = Jsoup.connect(url).timeout(loadTimeOut * 1000).get();
        Elements pagination = doc.getElementsByClass(paginationClass);//"page_numbers"
        Elements refsToThePages = pagination.get(0).getElementsByTag("a");
        this.getPages().add(this.url);
        for(Element page:refsToThePages){
            this.getPages().add(page.attr("href"));
        }
        //block to remove pagination to the next\last pages.
        this.getPages().remove(getPages().size() - 1);
        this.getPages().remove(getPages().size() - 1);
        return this.getPages().size();
    }
    @Override
    public void findAds() throws IOException {
        if (isSearchEnabled) {
            this.cleanListOfAds();
            if(isTheFirstRun){
                this.getPagesFromPagination("page_numbers");
                isTheFirstRun = false;
            }
            for(String url:getPages()){
                Document pageWithAds = Jsoup.connect(url).timeout(loadTimeOut*1000).get();
                Elements ads = pageWithAds.getElementsByClass("imd");
                for(Element element:ads){
                    NeagentAd newAd = new NeagentAd();
                    newAd.setUniqueID(element.id()); //to get unique ID
                    newAd.setUrl(element.getElementsByTag("a").get(0).attr("href")); //to get link
                    newAd.setAddress(element.getElementsByClass("imd_street").get(0).ownText());
                    String priceToInt = element.getElementsByClass("itm_price").get(0).ownText().replaceAll("[^\\d]", ""); //replace all the non-numerical
                    newAd.setPrice(Integer.parseInt(priceToInt)); //to get price
                    newAd.setTitle(element.getElementsByTag("h2").get(0).ownText());
                    newAd.setNumberOfRooms(element.getElementsByClass("itm_komnat").get(0).ownText());
                    newAd.setShortDescription(element.ownText());
                    if(element.classNames().contains("up")){
                        this.getPremiumAdvertisements().add(newAd);
                    }else{
                        this.getAdvertisements().add(newAd);
                    }
                }
            }
       }
   }
    public int getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(int priceMin) {
        this.priceMin = priceMin;
        updateUrl();
        isTheFirstRun = true;
    }

    public int getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(int priceMax) {
        this.priceMax = priceMax;
        updateUrl();
        isTheFirstRun = true;
    }

    public String getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(String roomCount) {
        this.roomCount = roomCount;
        updateUrl();
        isTheFirstRun = true;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
        updateUrl();
        isTheFirstRun = true;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
        updateUrl();
        isTheFirstRun = true;
    }
}
