package flatsearcher.searchers;

import flatsearcher.ads.OnlinerAd;
import flatsearcher.others.JSonReader;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class OnlinerSearcher extends Searcher {

    private JSonReader jSonReader;

    public OnlinerSearcher(String url, String searcherName, String roomCount) {
        setRoomCount(roomCount);
        setPriceMin(500000);
        setPriceMax(40000000);
        //change settings to default of roomCount configs contains incorrect parameters
        if(null == getRoomCount() || "".equals(getRoomCount()) || getRoomCount().contains(",,") || !(getRoomCount().contains("[^0-9,]"))){
            setRoomCount("0,1,2,3,4,5,6");
        }
        //https://ak.api.onliner.by/search/apartments?rent_type%5B%5D=room&rent_type%5B%5D=1_room&price%5Bmin%5D=500000&price%5Bmax%5D=40000000&only_owner=true&bounds%5Blb%5D%5Blat%5D=53.811773799860156&bounds%5Blb%5D%5Blong%5D=27.31225903515595&bounds%5Brt%5D%5Blat%5D=53.98169786454532&bounds%5Brt%5D%5Blong%5D=27.776431398437207&page=1
        String[] roomCountOneByOne = {};
        try {
            roomCountOneByOne = getRoomCount().replaceAll("[^0-9,]", "").split(",");
        }catch(Exception ex){/*TODO: catch any exceptions if occurred*/}

        for(int i = 0;i< roomCountOneByOne.length;i++) {
            if (Integer.valueOf(roomCountOneByOne[i]) <= 6) {
                if ("0".equals(roomCountOneByOne[i])) {
                    url += "&rent_type%5B%5D=" + "room";
                } else if ("1".equals(roomCountOneByOne[i])) {
                    url += "&rent_type%5B%5D=" + roomCountOneByOne[i] + "_room";
                } else {
                    url += "&rent_type%5B%5D=" + roomCountOneByOne[i] + "_rooms";
                }
            }
        }
        url+="&price%5Bmin%5D="+ getPriceMin();
        url+="&price%5Bmax%5D="+ getPriceMax();
        if(!isPremiumEnabled()){
            url+="&only_owner=true";
        }
        jSonReader = new JSonReader();
        setUrl(url);
        setName(searcherName);
    }

    @Override
    public void findAds() throws IOException {
        if (isSearchEnabled()) {
            this.getAdvertisements().clear();
        }
        JSONObject ads = jSonReader.readJsonFromUrl(getUrl());
        JSONArray array = ads.getJSONArray("apartments");
        for(int i = 0 ; i < array.length() ; i++){
            OnlinerAd newAd = new OnlinerAd();
            JSONObject jsonAd = array.getJSONObject(i);
            newAd.setUniqueID(jsonAd.getInt("id"));
            newAd.setUrl(jsonAd.getString("url"));
            newAd.setPrice(jsonAd.getJSONObject("price").getInt("usd"));
            newAd.setNumberOfRooms(jsonAd.getString("rent_type"));
            newAd.setAddress(jsonAd.getJSONObject("location").getString("address"));
            newAd.setTitle(newAd.getAddress()
            );
            this.getAdvertisements().add(newAd);
        }
    }
}
