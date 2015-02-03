import flatsearcher.searchers.NeagentSearcher;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dmitry on 2/2/15.
 */
public class NeagentSearcherCreatorTest {

    @Test
    public void testNeagentSearcherInvalidCreationOfUrl(){
        NeagentSearcher searcher = new NeagentSearcher(null,null);
        Assert.assertEquals("Check default value for Neagent if null is passed into constructor", "http://neagent.by/board/minsk/?catid="+searcher.getCategory(), searcher.getUrl() );
    }
    @Test
    public void testNeagentSearcherInvalidCreationOfName(){
        NeagentSearcher searcher = new NeagentSearcher(null,null);
        Assert.assertEquals("Check default value for Neagent if null is passed into constructor", "Neagent.by",searcher.getName());
    }
    @Test
    public void testNeagentSearcherValidCreation(){
        NeagentSearcher searcher = new NeagentSearcher("http://neagent.by/board/minsk/?", "Neagent.by");
        Assert.assertEquals("Validation of the correct using of constructor","http://neagent.by/board/minsk/?catid=1&roomCount=1,2,3,4,5",searcher.getUrl());
    }
    @Test
    public void testNeagentGetPagination() throws IOException {
        NeagentSearcher searcher = new NeagentSearcher("http://neagent.by/board/minsk/?", "Neagent.by");
        searcher.findAds();
        Assert.assertEquals(7, searcher.getPages().size());
        ArrayList<String> expectedPages = new ArrayList<String>();
        expectedPages.add("http://neagent.by/board/minsk/?catid=1&roomCount=1,2,3,4,5");
        expectedPages.add("http://neagent.by/board/minsk/13?catid=1&roomCount=1,2,3,4,5");
        expectedPages.add("http://neagent.by/board/minsk/26?catid=1&roomCount=1,2,3,4,5");
        expectedPages.add("http://neagent.by/board/minsk/39?catid=1&roomCount=1,2,3,4,5");
        expectedPages.add("http://neagent.by/board/minsk/52?catid=1&roomCount=1,2,3,4,5");
        expectedPages.add("http://neagent.by/board/minsk/65?catid=1&roomCount=1,2,3,4,5");
        expectedPages.add("http://neagent.by/board/minsk/78?catid=1&roomCount=1,2,3,4,5");
        for(int i = 0; i < searcher.getPages().size();i++){
            Assert.assertEquals("check that each url of the pagination - expected",expectedPages.get(i),searcher.getPages().get(i));
        }
    }
}