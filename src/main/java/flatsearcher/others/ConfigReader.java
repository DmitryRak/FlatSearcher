package flatsearcher.others;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigReader {
    private Scanner in;
    private String configFileName;
    private HashMap<String, String> configs;

    public ConfigReader(String filename) throws FileNotFoundException {
        configFileName = filename;
        in = new Scanner(new File(configFileName));
        String temp;
        String[] tempConf;
        configs = new HashMap<String, String>();
        try {
            while (in.hasNextLine()) {
                temp = in.nextLine();
                tempConf = temp.split("=");
                configs.put(tempConf[0], tempConf[1]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + ": It seems that config file is corrupted. Starting app with default settings", "InfoBox: " + e.getClass(), JOptionPane.INFORMATION_MESSAGE);

        }
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }

    public HashMap<String, String> getConfigs() {
        return configs;
    }

    public void setConfigs(HashMap<String, String> configs) {
        this.configs = configs;
    }
}
