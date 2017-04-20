package pw.duke.h1z1alert;
// Hello there, This is my Alert system for telling you when H1Z1: Just Survive servers unlock. :) 
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class Main {

    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        while (true) {
            checkStatus();
            Thread.sleep(45);
        }
    }

    private static void displayTray() throws AWTException, java.net.MalformedURLException {
        SystemTray tray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "H1Z1Alerter");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("H1Z1Alerter");
        tray.add(trayIcon);
        trayIcon.displayMessage("H1Z1Alerter", "H1Z1 Servers are back online!", MessageType.INFO);
    }

    private static void checkStatus() throws IOException, AWTException {
        // Check from MY API because i really don't want others taking credit for the solid 20 minutes i spent on this lol
        Connection con = Jsoup.connect("http://atom.duke.pw/h1z1check.php/");
        Response res = con.execute();
        String rawPageStr = res.body();

        if (rawPageStr.contains("up")){
            if (SystemTray.isSupported()) {
                displayTray();
                System.exit(0);
            } else {
                System.err.println("System tray not supported!");
            }
        }
        
    }

}
