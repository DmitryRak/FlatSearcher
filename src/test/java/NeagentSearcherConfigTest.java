import flatsearcher.searchers.NeagentSearcher;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by dmitry on 2/3/15.
 */
public class NeagentSearcherConfigTest {
    private NeagentSearcher searcher;

    @Test
    public void shouldBeAGoodURLWithZeroPriceMin(){
        searcher = new NeagentSearcher("http://neagent.by/board/minsk/?", "Neagent.by");
        int priceMin = 0;
        searcher.setPriceMin(priceMin);
        assertThat(searcher.getUrl()).isEqualTo("http://neagent.by/board/minsk/?catid=1&roomCount=1,2,3,4,5");
    }
    @Test
    public void shouldBeAGoodURLWithZeroPriceMax(){
        searcher = new NeagentSearcher("http://neagent.by/board/minsk/?", "Neagent.by");
        int priceMax = 0;
        searcher.setPriceMax(priceMax);
        assertThat(searcher.getUrl()).isEqualTo("http://neagent.by/board/minsk/?catid=1&roomCount=1,2,3,4,5");
    }
    @Test
    public void shouldBeAGoodURLWithPriceMin(){
        searcher = new NeagentSearcher("http://neagent.by/board/minsk/?", "Neagent.by");
        int priceMin = 200;
        searcher.setPriceMin(priceMin);
        assertThat(searcher.getUrl()).isEqualTo("http://neagent.by/board/minsk/?catid=1&roomCount=1,2,3,4,5&priceMin=200&currency=2");
    }
    @Test
    public void shouldBeAGoodURLWithPriceMax(){
        searcher = new NeagentSearcher("http://neagent.by/board/minsk/?", "Neagent.by");
        int priceMax = 500;
        searcher.setPriceMax(priceMax);
        assertThat(searcher.getUrl()).isEqualTo("http://neagent.by/board/minsk/?catid=1&roomCount=1,2,3,4,5&priceMax=500&currency=2");
    }
}
