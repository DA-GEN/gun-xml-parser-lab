package com.dagen.gunxml.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggingConfigTest {

    @TempDir
    Path tempDir;

    @Test
    void createsLogDirectory() {
        LoggingConfig.init();

        assertTrue(Files.isDirectory(LoggingConfig.LOG_DIR));
    }

    @Test
    void failsWhenConfigIsMissing() {
        assertThrows(IllegalStateException.class, () -> LoggingConfig.init("/missing.properties", tempDir));
    }

    @Test
    void failsWhenLogDirectoryCannotBeCreated() throws IOException {
        Path file = Files.createFile(tempDir.resolve("logs"));

        assertThrows(IllegalStateException.class, () -> LoggingConfig.init(LoggingConfig.CONFIG_RESOURCE, file));
    }
}
