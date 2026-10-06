package com.dagen.gunxml.app;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.LogManager;

public final class LoggingConfig {

    static final String CONFIG_RESOURCE = "/logging.properties";
    static final Path LOG_DIR = Path.of("logs");

    private LoggingConfig() {
    }

    public static void init() {
        init(CONFIG_RESOURCE, LOG_DIR);
    }

    static void init(String resource, Path logDir) {
        try (InputStream config = LoggingConfig.class.getResourceAsStream(resource)) {
            if (config == null) {
                throw new IllegalStateException(resource + " not found on classpath");
            }
            Files.createDirectories(logDir);
            LogManager.getLogManager().readConfiguration(config);
        } catch (IOException e) {
            throw new IllegalStateException("cannot configure logging: " + e.getMessage(), e);
        }
    }
}
