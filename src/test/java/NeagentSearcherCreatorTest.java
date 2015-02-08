import flatsearcher.searchers.NeagentSearcher;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by dmitry on 2/2/15.
 */
public class NeagentSearcherCreatorTest {

    @Test
    public void testNeagentSearcherInvalidCreationOfUrl(){
        NeagentSearcher searcher = new NeagentSearcher(null,null);
        assertThat(searcher.getUrl()).isEqualTo("http://neagent.by/board/minsk/?catid="+searcher.getCategory());
    }

    @Test
    public void testNeagentSearcherValidCreation(){
        NeagentSearcher searcher = new NeagentSearcher("http://neagent.by/board/minsk/?", "Neagent.by");
        assertThat(searcher.getUrl()).isEqualTo("http://neagent.by/board/minsk/?catid=1&roomCount=1,2,3,4,5");

    }
    @Test
    public void testNeagentGetPagination() throws IOException {
        NeagentSearcher searcher = new NeagentSearcher("http://neagent.by/board/minsk/?", "Neagent.by");
        searcher.findAds();
        assertThat(searcher.getPages().size()).isEqualTo(7);
        ArrayList<String> expectedPages = new ArrayList<String>();
        expectedPages.add("http://neagent.by/board/minsk/?catid=1&roomCount=1,2,3,4,5");
        expectedPages.add("http://neagent.by/board/minsk/13?catid=1&roomCount=1,2,3,4,5");
        expectedPages.add("http://neagent.by/board/minsk/26?catid=1&roomCount=1,2,3,4,5");
        expectedPages.add("http://neagent.by/board/minsk/39?catid=1&roomCount=1,2,3,4,5");
        expectedPages.add("http://neagent.by/board/minsk/52?catid=1&roomCount=1,2,3,4,5");
        expectedPages.add("http://neagent.by/board/minsk/65?catid=1&roomCount=1,2,3,4,5");
        expectedPages.add("http://neagent.by/board/minsk/78?catid=1&roomCount=1,2,3,4,5");

        //TODO fest
        //Assert.assertEquals("check that each url of the pagination - expected",expectedPages.get(i),searcher.getPages().get(i));
        assertThat(searcher.getPages()).isSameAs(expectedPages);

    }
}