package net.vitacraft.discordbotmanager.config;
import org.simpleyaml.configuration.file.FileConfiguration;
import org.simpleyaml.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
public class ConfigUtil {
    private final File file;
    private final FileConfiguration config;

    public ConfigUtil(Path configDir, String configFileName) {
        this(configDir + "/" + configFileName);
    }

    public ConfigUtil(String path) {
        this.file = new File(path);
        try {
            if (!this.file.exists()) {
                InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(this.file.getName());
                if (resourceStream != null) {
                    Files.copy(resourceStream, this.file.toPath());
                } else {
                    this.file.createNewFile();
                }
            }
            this.config = YamlConfiguration.loadConfiguration(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void save() {
        try {
            this.config.save(this.file);
        } catch (Exception ignored) {
        }
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }
}
