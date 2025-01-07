package legacybob.oebwf2ij.Config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    public static final String FILENAME = "config/LegacyBobbing.json";
    private static final Logger logger = Logger.getLogger(Config.class.getName());

    public boolean LegacyBobEnabled = true;

    public Config() {
        try {
            FileReader reader = new FileReader(FILENAME);
            JsonElement rootElement = JsonParser.parseReader(reader);
            if (!rootElement.isJsonObject()) {
                throw new Exception("Root element is not a JSON object!");
            }
            LegacyBobEnabled = ((JsonObject) rootElement).get("LegacyBob").getAsBoolean();
        } catch (Exception e) {
            File file = new File(FILENAME);
            if (!file.exists()) {
                createConfig();
            }
        }
    }

    private static void createConfig() {
        try (FileWriter fileWriter = new FileWriter(FILENAME)) {
            fileWriter.write("{\"LegacyBob\": true}");
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void save() {
        try (FileWriter fileWriter = new FileWriter(FILENAME)) {
            fileWriter.write(String.format("{\"LegacyBob\": %s}", LegacyBobEnabled));
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public boolean getLegacyBob() {
        return this.LegacyBobEnabled;
    }

    public void setLegacyBob(Boolean option) {
        this.LegacyBobEnabled = option;
    }
}