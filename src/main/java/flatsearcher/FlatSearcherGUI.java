package flatsearcher;

import flatsearcher.searchers.Searcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class FlatSearcherGUI implements ActionListener {

    private ArrayList<Searcher> searchers;
    private ArrayList<CheckboxMenuItem> checkboxes;
    private ArrayList<MenuItem> menuItems;
    private MenuItem exitItem;
    private TrayIcon trayIcon;
    private String urlToOpen;

    public FlatSearcherGUI() {
        trayIcon = new TrayIcon(new ImageIcon("resources/FlatSearcher.png", "Flat Searcher").getImage());
        menuItems = new ArrayList<MenuItem>();
        checkboxes = new ArrayList<CheckboxMenuItem>();
        searchers = new ArrayList<Searcher>();
        exitItem = new MenuItem("Exit");
    }

    public void addNewSearcher(Searcher searcher) {
        searchers.add(searcher);
        checkboxes.add(new CheckboxMenuItem(searcher.getName() + " validation"));
        menuItems.add(new MenuItem("open " + searcher.getName()));
    }

    public void start() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            // create a popup menu
            PopupMenu popup = new PopupMenu();
            for (int i = 0; i < searchers.size(); i++) {

                checkboxes.get(i).setState(true);
                popup.add(checkboxes.get(i));
                popup.add(menuItems.get(i));
                checkboxes.get(i).addActionListener(this);
                menuItems.get(i).addActionListener(this);
                popup.addSeparator();
            }
            exitItem.addActionListener(this);
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);
            trayIcon.addActionListener(this);
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
        }
    }

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(exitItem)) {
            System.exit(0);
        } else if (e.getSource().equals(trayIcon)) {
            try {
                openWebpage(new URI(urlToOpen));
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        } else {
            for (int i = 0; i < searchers.size(); i++) {
                if (e.getSource().equals(menuItems.get(i))) {
                    try {
                        System.out.println(searchers.get(i).getUrl());
                        openWebpage(new URI(searchers.get(i).getUrl()));
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    public String getUrlToOpen() {
        return urlToOpen;
    }

    public ArrayList<CheckboxMenuItem> getCheckboxes() {
        return checkboxes;
    }

    public void setUrlToOpen(String urlToOpen) {
        this.urlToOpen = urlToOpen;
    }

    public ArrayList<Searcher> getSearchers() {
        return searchers;
    }

    public void setSearchers(ArrayList<Searcher> searchers) {
        this.searchers = searchers;
    }

    public void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}