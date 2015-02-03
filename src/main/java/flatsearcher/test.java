package flatsearcher;

import flatsearcher.searchers.KvartirantSearcher;
import flatsearcher.searchers.NeagentSearcher;
import flatsearcher.searchers.OnlinerSearcher;

public class test {

    public static void main(String[] args) {
        FlatSearcher f = new FlatSearcher("resources/configs.cfg");
        //use the method below to add new searchers.
        //f.getTray().addNewSearcher(new OnlinerSearcher("http://baraholka.onliner.by/viewforum.php?f=62&cat=5", "Onliner.by"));

        f.getTray().addNewSearcher(new NeagentSearcher("http://neagent.by/board/minsk/?", "Neagent.by"));
      //  f.getTray().addNewSearcher(new KvartirantSearcher("http://www.kvartirant.by/ads/flats/type/rent/?tx_uedbadsboard_pi1%5Bsearch%5D%5Bowner%5D=on", "Kvartirant.by"));
        f.run();
    }

}
