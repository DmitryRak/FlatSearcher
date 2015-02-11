package flatsearcher;

import flatsearcher.others.JSonReader;
import flatsearcher.searchers.KvartirantSearcher;
import flatsearcher.searchers.NeagentSearcher;
import flatsearcher.searchers.OnlinerSearcher;

public class test {

    public static void main(String[] args) {
        FlatSearcher f = new FlatSearcher("resources/configs.cfg");
        //use the method below to add new searchers.
        f.getTray().addNewSearcher(new OnlinerSearcher("https://ak.api.onliner.by/search/apartments?bounds%5Blb%5D%5Blat%5D=53.811773799860156&bounds%5Blb%5D%5Blong%5D=27.31225903515595&bounds%5Brt%5D%5Blat%5D=53.98169786454532&bounds%5Brt%5D%5Blong%5D=27.776431398437207&page=1", "Onliner.by","0,1,2,3,4,5,6"));
        f.getTray().addNewSearcher(new NeagentSearcher("http://neagent.by/board/minsk/?", "Neagent.by"));
      //  f.getTray().addNewSearcher(new KvartirantSearcher("http://www.kvartirant.by/ads/flats/type/rent/?tx_uedbadsboard_pi1%5Bsearch%5D%5Bowner%5D=on", "Kvartirant.by"));
        f.run();
    }

}
