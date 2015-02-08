package flatsearcher.searchers;

import flatsearcher.ads.NeagentAd;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NeagentSearcher extends Searcher {
    private int loadTimeOut = 20;

    public NeagentSearcher(String url, String searcherName) {
        if(null == searcherName) {
            searcherName = "Neagent.by";
        }
        setName(searcherName);
        setCurrency(2);
        setPremiumEnabled(true);

        if(null == url){
            url = "http://neagent.by/board/minsk/?catid="+getCategory();
        }else{
            setInitialUrl(url);
            url+="catid="+getCategory()+"&";
            url+="roomCount="+getRoomCount();

            if(0 != getPriceMin()){
                url+="&priceMin="+getPriceMin()+"&";
                if(0 != getPriceMax()){
                    url+="priceMax="+getPriceMax()+"&";
                    url+="currency="+getCurrency();
                } else{
                    url+="currency="+getCurrency();
                }
            }else  if(0 != getPriceMax()){
                url+="&priceMax="+getPriceMax()+"&";
                url+="currency="+getCurrency();
            }
        }
        setUrl(url);
    }
    public String updateUrl(){
        this.url=getInitialUrl()+"catid="+getCategory()+"&";
        this.url+="roomCount="+getRoomCount();

        if(0 != getPriceMin()){
            this.url+="&priceMin="+getPriceMin()+"&";
            if(0 != getPriceMax()){
                this.url+="priceMax="+getPriceMax()+"&";
                this.url+="currency="+getCurrency();
            } else{
                this.url+="currency="+getCurrency();
            }
        }else  if(0 != getPriceMax()){
            this.url+="&priceMax="+getPriceMax()+"&";
            this.url+="currency="+getCurrency();
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
        if (isSearchEnabled()) {
            this.cleanListOfAds();
            if(isTheFirstRun()){
                this.getPagesFromPagination("page_numbers");
                setTheFirstRun(false);
            }
            for(String url:getPages()){
                Document pageWithAds = Jsoup.connect(url).timeout(loadTimeOut*1000).get();
                Elements ads = pageWithAds.getElementsByClass("imd");
                for(Element element:ads){
                    NeagentAd newAd = new NeagentAd();
                    newAd.setUniqueID(Integer.valueOf(element.id().substring(1))); //to get unique ID
                    newAd.setUrl(element.getElementsByTag("a").get(0).attr("href")); //to get link
                    newAd.setAddress(element.getElementsByClass("imd_street").get(0).ownText());
                    String priceToInt = element.getElementsByClass("itm_price").get(0).ownText().replaceAll("[^\\d]", ""); //replace all the non-numerical
                    newAd.setPrice(Integer.parseInt(priceToInt)); //to get price
                    newAd.setTitle(element.getElementsByTag("h2").get(0).ownText());
                    newAd.setNumberOfRooms(element.getElementsByClass("itm_komnat").get(0).ownText());
                    newAd.setShortDescription(element.ownText());
                    if(element.classNames().contains("up")){
                        if(isPremiumEnabled()) {
                            this.getPremiumAdvertisements().add(newAd);
                        }
                    }else{
                        this.getAdvertisements().add(newAd);
                    }
                }
            }
       }
   }

    public void setPriceMin(int priceMin) {
        super.setPriceMin(priceMin);
        updateUrl();
        setTheFirstRun(true);
    }

    public void setPriceMax(int priceMax) {
        super.setPriceMax(priceMax);
        updateUrl();
        setTheFirstRun(true);
    }

    public void setRoomCount(String roomCount) {
        super.setRoomCount(roomCount);
        updateUrl();
        setTheFirstRun(true);
    }

    public void setCategory(int category) {
        super.setCategory(category);
        updateUrl();
        setTheFirstRun(true);
    }

    public void setCurrency(int currency) {
        super.setCurrency(currency);
        updateUrl();
        setTheFirstRun(true);
    }
}
