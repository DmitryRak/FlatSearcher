import flatsearcher.searchers.OnlinerSearcher;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by dmitry on 9.2.15.
 */
public class OnlinerSearcherCreatorTest {

    private String url = "https://r.onliner.by/ak/?bounds[lb][lat]=53.63095269118382&bounds[lb][long]=27.098025636718724&bounds[rt][lat]=54.16382444725692&bounds[rt][long]=28.026370363281234";
    private String roomCount1 = "0,1,2,3,4,5,6";
    private String roomCount2 = "$%^&*()_+0,qwe1,2wertetyui,3,4hjkcvbnmmnzxf,sdgnm.lkj;'5p]!@#';,6";
    private String roomCount3 = "0, 1, 2, 3, 4, 5,6";
    private String roomCount4 = "";
    private String roomCount5 = "0,1,2,3,4,5,6,7,8,9,10,11,123";
    private String roomCount6 = ",,,,";
    private String roomCount7 = "???";

    @Test
    public void shouldMakeGoodUrlWithValidRoomsSpecified(){
        OnlinerSearcher searcher = new OnlinerSearcher(url,null,roomCount1);
        assertThat(searcher.getUrl()).isEqualTo(url+"&rent_type[]=room&rent_type[]=1_room&rent_type[]=2_rooms&rent_type[]=3_rooms&rent_type[]=4_rooms&rent_type[]=5_rooms&rent_type[]=6_rooms&price[min]=500000&price[max]=40000000&only_owner=true");
    }
    @Test
    public void shouldMakeGoodUrlWithInValidRoomsSpecified(){
        OnlinerSearcher searcher = new OnlinerSearcher(url,null,roomCount2);
        assertThat(searcher.getUrl()).isEqualTo(url+"&rent_type[]=room&rent_type[]=1_room&rent_type[]=2_rooms&rent_type[]=3_rooms&rent_type[]=4_rooms&rent_type[]=5_rooms&rent_type[]=6_rooms&price[min]=500000&price[max]=40000000&only_owner=true");
    }
    @Test
    public void shouldMakeGoodUrlWithISpacesInRooms(){
        OnlinerSearcher searcher = new OnlinerSearcher(url,null,roomCount3);
        assertThat(searcher.getUrl()).isEqualTo(url+"&rent_type[]=room&rent_type[]=1_room&rent_type[]=2_rooms&rent_type[]=3_rooms&rent_type[]=4_rooms&rent_type[]=5_rooms&rent_type[]=6_rooms&price[min]=500000&price[max]=40000000&only_owner=true");
    }
    @Test
    public void shouldMakeGoodUrlWithIEmptyRoomCount(){
        OnlinerSearcher searcher = new OnlinerSearcher(url,null,roomCount4);
        assertThat(searcher.getUrl()).isEqualTo(url+"&rent_type[]=room&rent_type[]=1_room&rent_type[]=2_rooms&rent_type[]=3_rooms&rent_type[]=4_rooms&rent_type[]=5_rooms&rent_type[]=6_rooms&price[min]=500000&price[max]=40000000&only_owner=true");
    }
    @Test
    public void shouldMakeGoodUrlWithITooBigRooms(){
        OnlinerSearcher searcher = new OnlinerSearcher(url,null,roomCount5);
        assertThat(searcher.getUrl()).isEqualTo(url+"&rent_type[]=room&rent_type[]=1_room&rent_type[]=2_rooms&rent_type[]=3_rooms&rent_type[]=4_rooms&rent_type[]=5_rooms&rent_type[]=6_rooms&price[min]=500000&price[max]=40000000&only_owner=true");
    }
    @Test
    public void shouldMakeGoodURLWithOnlyCommas(){
        OnlinerSearcher searcher = new OnlinerSearcher(url,null,roomCount6);
        assertThat(searcher.getUrl()).isEqualTo(url+"&rent_type[]=room&rent_type[]=1_room&rent_type[]=2_rooms&rent_type[]=3_rooms&rent_type[]=4_rooms&rent_type[]=5_rooms&rent_type[]=6_rooms&price[min]=500000&price[max]=40000000&only_owner=true");
    }
    @Test
    public void shouldMakeGoodURLWithoutCommasAndNumbers(){
        OnlinerSearcher searcher = new OnlinerSearcher(url,null,roomCount7);
        assertThat(searcher.getUrl()).isEqualTo(url+"&rent_type[]=room&rent_type[]=1_room&rent_type[]=2_rooms&rent_type[]=3_rooms&rent_type[]=4_rooms&rent_type[]=5_rooms&rent_type[]=6_rooms&price[min]=500000&price[max]=40000000&only_owner=true");
    }
}
