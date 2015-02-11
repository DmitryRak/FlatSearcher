package flatsearcher;

import flatsearcher.ads.Ad;
import flatsearcher.ads.KvartirantAd;
import flatsearcher.ads.NeagentAd;
import flatsearcher.ads.OnlinerAd;
import flatsearcher.others.ConfigReader;
import flatsearcher.others.LogWriter;
import flatsearcher.searchers.Searcher;

import javax.swing.*;
import java.awt.TrayIcon.MessageType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class FlatSearcher implements Runnable {
    private FlatSearcherGUI tray;
    private boolean isLogEnabled = true;
    private boolean notificationMode = true; //if set to true - the new tab will not appear, just a notification from tray
    private LogWriter log;
    private ConfigReader configReader;
    private String messageToShow;

    public FlatSearcherGUI getTray() {
        return tray;
    }

    public FlatSearcher(String confFileName) {
        try {
            configReader = new ConfigReader(confFileName);
            isLogEnabled = Boolean.valueOf(configReader.getConfigs().get("log"));
            notificationMode = Boolean.valueOf(configReader.getConfigs().get("notification"));
        } catch (FileNotFoundException e) {
            /*block to insert default settings if config file is corrupted*/
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage() + ": It is recommended to locate " + confFileName + " file in the same folder as jar executable. Starting app with default settings", "InfoBox: " + e.getClass(), JOptionPane.INFORMATION_MESSAGE);
        }
        log = new LogWriter(isLogEnabled);
        tray = new FlatSearcherGUI();
    }

    @Override
    public void run() {
        tray.start();
        while (true) {
            for (int i = 0; i < tray.getSearchers().size(); i++) {
                Searcher searcher = tray.getSearchers().get(i);
                searcher.setSearchEnabled(tray.getCheckboxes().get(i).getState());
                if (searcher.isSearchEnabled()) {
                    try {
                        searcher.findAds();
                    } catch (UnknownHostException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage() + ": It seems that internet connection is refused. Please check", "InfoBox: " + e.getClass(), JOptionPane.INFORMATION_MESSAGE);
                        e.printStackTrace();
                        log.print(e.getClass() + ": " + e.getMessage() + ", " + searcher.getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.print(e.getClass() + ": " + e.getMessage() + ", " + searcher.getName());
                    }
                    int newFirstAd;
                    //block for validation of normal ads
                    if(searcher.getAdvertisements().size()>0){
                        newFirstAd = searcher.getAdvertisements().getFirst().getUniqueID();
                        if (searcher.getTheFirstAd()!=(newFirstAd)) {
                            searcher.setTheFirstAd(newFirstAd);
                            log.print("New ad: " + newFirstAd + ", " + searcher.getName());
                            if (notificationMode) {
                                if (searcher.getAdvertisements().getFirst() instanceof NeagentAd) {
                                    NeagentAd tempAd = (NeagentAd) searcher.getAdvertisements().getFirst();
                                    messageToShow = null;
                                    addMessageToShow("Цена: " + String.valueOf(tempAd.getPrice()));
                                    addMessageToShow("Кол-во комнат: " + String.valueOf(tempAd.getNumberOfRooms()));
                                    addMessageToShow("Адрес: " + String.valueOf(tempAd.getAddress()));
                                    tray.getTrayIcon().displayMessage(tempAd.getTitle(), messageToShow, MessageType.INFO);
                                }else if (searcher.getAdvertisements().getFirst() instanceof OnlinerAd){
                                    OnlinerAd tempAd = (OnlinerAd) searcher.getAdvertisements().getFirst();
                                    messageToShow = null;
                                    addMessageToShow("Цена: " + String.valueOf(tempAd.getPrice()));
                                    addMessageToShow("Кол-во комнат: " + String.valueOf(tempAd.getNumberOfRooms()));
                                    addMessageToShow("Адрес: " + String.valueOf(tempAd.getAddress()));
                                    tray.getTrayIcon().displayMessage(tempAd.getTitle(), messageToShow, MessageType.INFO);
                                }
                                tray.setUrlToOpen(searcher.getAdvertisements().getFirst().getUrl());
                            } else{
                                try{
                                    tray.openWebpage(new URI(searcher.getAdvertisements().getFirst().getUrl()));
                                }catch (URISyntaxException e) {
                                    e.printStackTrace();
                                    log.print(e.getClass() + ": " + e.getMessage() + ", " + searcher.getName());
                                }
                            }
                        } else{
                            System.out.println("Normal: " + searcher.getTheFirstAd() + " " + searcher.getName());
                        }
                    }
                    try {
                        Thread.sleep(searcher.getSleepInterval()/2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        log.print(e.getClass() + ": " + e.getMessage() + ", " + searcher.getName());
                    }
                    //block for validation of Premium ads
                    if(searcher.isPremiumEnabled()) {
                        if (searcher.getPremiumAdvertisements().size() > 0) {
                            newFirstAd = searcher.getPremiumAdvertisements().getFirst().getUniqueID();
                            if (searcher.getTheFirstPremiumAd()!=(newFirstAd)) {
                                searcher.setTheFirstPremiumAd(newFirstAd);
                                log.print("New ad: " + newFirstAd + ", " + searcher.getName());
                                if (notificationMode) {
                                    if (searcher.getPremiumAdvertisements().getFirst() instanceof NeagentAd) {
                                        NeagentAd tempAd = (NeagentAd) searcher.getPremiumAdvertisements().getFirst();
                                        messageToShow = null;
                                        addMessageToShow("Цена: " + String.valueOf(tempAd.getPrice()));
                                        addMessageToShow("Кол-во комнат: " + String.valueOf(tempAd.getNumberOfRooms()));
                                        addMessageToShow("Адрес: " + String.valueOf(tempAd.getAddress()));
                                        tray.getTrayIcon().displayMessage(tempAd.getTitle(), messageToShow, MessageType.INFO);
                                    }
                                    tray.setUrlToOpen(searcher.getPremiumAdvertisements().getFirst().getUrl());
                                } else {
                                    try {
                                        tray.openWebpage(new URI(searcher.getPremiumAdvertisements().getFirst().getUrl()));
                                    } catch (URISyntaxException e) {
                                        e.printStackTrace();
                                        log.print(e.getClass() + ": " + e.getMessage() + ", " + searcher.getName());
                                    }
                                }
                            } else {
                                System.out.println("Premium: " + searcher.getTheFirstPremiumAd() + " " + searcher.getName());
                            }
                        }
                        try {
                            Thread.sleep(searcher.getSleepInterval() / 2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            log.print(e.getClass() + ": " + e.getMessage() + ", " + searcher.getName());
                        }
                    }
                }
            }
        }
    }

    /*This approach is not showing ellipses (...) at the end of the last line*/
    //http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6440297
    private void addMessageToShow(String messageToShow) {
        if (this.messageToShow == null) {
            this.messageToShow = messageToShow;
        } else {
            if (System.getProperty("os.name").contains("Linux")) {
                /*
                 Fill with blank spaces to get the next message in next line.
                 Checking with mod operator to handle the line which wraps
                 into multiple lines
                */
                while (this.messageToShow.length() % 56 > 0) {
                    this.messageToShow += " ";
                }
            } else {
                this.messageToShow += "\n";// Works properly with windows
            }
            this.messageToShow += messageToShow;
        }
    }
}