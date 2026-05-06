package com.example.shirjeelplays.cleanhitbox.config;
import com.example.shirjeelplays.cleanhitbox.config.ModConfig;
import net.fabricmc.loader.api.FabricLoader;
import java.io.*;
import java.util.Properties;

public class ModConfig {
    public static boolean showBoundingBox = true;
    public static boolean showEyeHeight = true;
    public static boolean showLookVector = true;
    public static float lineWidth = 1.0f;
    public static int boundingBoxColor = 0xFFFFFF; // White
    public static int eyeHeightColor = 0xFF0000; // Red
    public static int lookVectorColor = 0x0000FF; // Blue
    public static int targetHighlightColor = 0xFF0000; // Red

    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("clean-hitbox.properties").toFile();

    public static void load() {
        if (!CONFIG_FILE.exists()) {
            save();
            return;
        }

        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            Properties prop = new Properties();
            prop.load(input);

            showBoundingBox = Boolean.parseBoolean(prop.getProperty("showBoundingBox", "true"));
            showEyeHeight = Boolean.parseBoolean(prop.getProperty("showEyeHeight", "true"));
            showLookVector = Boolean.parseBoolean(prop.getProperty("showLookVector", "true"));
            lineWidth = Float.parseFloat(prop.getProperty("lineWidth", "1.0"));
            boundingBoxColor = Integer.parseInt(prop.getProperty("boundingBoxColor", "16777215"));
            eyeHeightColor = Integer.parseInt(prop.getProperty("eyeHeightColor", "16711680"));
            lookVectorColor = Integer.parseInt(prop.getProperty("lookVectorColor", "255"));
            targetHighlightColor = Integer.parseInt(prop.getProperty("targetHighlightColor", "16711680"));
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            Properties prop = new Properties();
            prop.setProperty("showBoundingBox", String.valueOf(showBoundingBox));
            prop.setProperty("showEyeHeight", String.valueOf(showEyeHeight));
            prop.setProperty("showLookVector", String.valueOf(showLookVector));
            prop.setProperty("lineWidth", String.valueOf(lineWidth));
            prop.setProperty("boundingBoxColor", String.valueOf(boundingBoxColor));
            prop.setProperty("eyeHeightColor", String.valueOf(eyeHeightColor));
            prop.setProperty("lookVectorColor", String.valueOf(lookVectorColor));
            prop.setProperty("targetHighlightColor", String.valueOf(targetHighlightColor));
            prop.store(output, "Clean Hitbox Config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
