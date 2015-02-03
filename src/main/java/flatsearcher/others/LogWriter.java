package flatsearcher.others;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter {
    private StringBuilder sb = new StringBuilder();
    private StringBuilder log = new StringBuilder();
    private String date;
    private SimpleDateFormat time = new SimpleDateFormat();
    private FileOutputStream out;
    private boolean logging = false;
    private char logDelim = ',';

    public LogWriter(boolean logging) {
        if (logging) startLog();
        time.applyPattern("HH:mm:ss.SSS");

    }

    private void startLog() {

        try {
            time.applyPattern("yyyyMMdd_HHmmss");
            out = new FileOutputStream("./log_" + time.format(new Date()) + ".csv");
            log.append("Time").append(logDelim).append("Message").append(logDelim).append("Domain").append('\n');
            out.write(sb2Array(log));
            out.flush();
        } catch (FileNotFoundException e) {
            System.err.println("Fails to create output file. Logging is disabled....");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Fails to write to output log file. Logging is disabled....");
            e.printStackTrace();
        }
        logging = true;
    }

    private void writeToLog(String message) {
        log.setLength(0);
        log.append(date).append(logDelim).append(message).append('\n');
        try {
            out.write(sb2Array(log));
            out.flush();
        } catch (IOException e) {
            System.err.println("Fails to write to output log file. Logging is disabled....");
            logging = false;
        }
    }

    private void writeToScreen(String message) {
        sb.setLength(0);
        sb.append('\n').append(date).append(" ").append(message);
        System.out.println(sb.toString());
    }

    public synchronized void print(String message) {
        date = time.format(new Date());
        if (logging) writeToLog(message);
        writeToScreen(message);
    }

    private byte[] sb2Array(StringBuilder str) {
        char[] ch = new char[str.length()];
        str.getChars(0, log.length(), ch, 0);
        byte[] tmp = new byte[ch.length];
        for (int i = 0; i < ch.length; i++) {
            tmp[i] = (byte) ch[i];
        }
        return tmp;
    }
}
