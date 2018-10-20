package rs.devlabs.clockwidget;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IQooLogic
 */
public class Settings {

    private static final String FILENAME = "weather-widget.properties";
    private static final Properties prop = new Properties();

    public Settings() {
        if (Files.exists(Paths.get(FILENAME))) {
            try {
                prop.load(new FileReader(FILENAME));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Point getLocation() {
        int x = Integer.valueOf(prop.getProperty("locationOnScreen.x", "0"));
        int y = Integer.valueOf(prop.getProperty("locationOnScreen.y", "0"));
        return new Point(x, y);
    }

    public void setLocation(Point location) {
        prop.setProperty("locationOnScreen.x", String.valueOf(location.x));
        prop.setProperty("locationOnScreen.y", String.valueOf(location.y));
        try {
            prop.store(new FileWriter(FILENAME), FILENAME);
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean exists() {
        return Files.exists(Paths.get(FILENAME));
    }
}
