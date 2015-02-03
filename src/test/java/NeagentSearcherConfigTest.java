import flatsearcher.searchers.NeagentSearcher;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by dmitry on 2/3/15.
 */
public class NeagentSearcherConfigTest {
    private static NeagentSearcher searcher;
    @BeforeClass
    public static void setUpSearcher(){
        searcher = new NeagentSearcher("http://neagent.by/board/minsk/?", "Neagent.by");

    }
    @Test
    public void testDefaultCategory(){
        Assert.assertEquals(1,searcher.getCategory());
    }
    @Test
    public void testDefaultRoomCount(){
        Assert.assertEquals("1,2,3,4,5",searcher.getRoomCount());
    }
    @Test
    public void testDefaultCurrency(){
        Assert.assertEquals(2,searcher.getCurrency());
    }
    @Test
    public void testPriceMinChangeToZero(){
        int priceMin = 0;
        searcher.setPriceMin(priceMin);
        Assert.assertEquals("http://neagent.by/board/minsk/?catid=1&roomCount=1,2,3,4,5", searcher.getUrl());
    }
    @Test
    public void testPriceMaxChangeToZero(){
        int priceMax = 0;
        searcher.setPriceMax(priceMax);
        Assert.assertEquals("http://neagent.by/board/minsk/?catid=1&roomCount=1,2,3,4,5", searcher.getUrl());
    }
    @Test
    public void testPriceMinChange(){
        int priceMin = 200;
        searcher.setPriceMin(priceMin);
        Assert.assertEquals("http://neagent.by/board/minsk/?catid=1&roomCount=1,2,3,4,5&priceMin=200&currency=2", searcher.getUrl());
    }
    @Test
    public void testPriceMaxChange(){
        int priceMax = 500;
        searcher.setPriceMax(priceMax);
        Assert.assertEquals("http://neagent.by/board/minsk/?catid=1&roomCount=1,2,3,4,5&priceMin=200&priceMax=500&currency=2", searcher.getUrl());
    }
}
    /*
private String roomCount = "1,2,3,4,5";
private int category = 1;
private int currency = 1;
private int priceMin, priceMax;
private boolean isTheFirstRun = true;
private int loadTimeOut = 20;
    */