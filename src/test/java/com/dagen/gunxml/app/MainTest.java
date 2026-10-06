package com.dagen.gunxml.app;

import com.dagen.gunxml.GunXmlSamples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @TempDir
    Path tempDir;

    @Test
    void processesDataFile() {
        Path result = tempDir.resolve("result.xml");

        assertTrue(Main.run(GunXmlSamples.XML_FILE, result));
        assertTrue(Files.exists(result));
    }

    @Test
    void reportsInvalidDocument() {
        Path file = GunXmlSamples.write(tempDir, GunXmlSamples.document());
        Path result = tempDir.resolve("result.xml");

        assertFalse(Main.run(file, result));
        assertFalse(Files.exists(result));
    }

    @Test
    void mainWritesResultToGivenPath() {
        Path result = tempDir.resolve("result.xml");

        Main.main(new String[]{GunXmlSamples.XML_FILE.toString(), result.toString()});

        assertTrue(Files.exists(result));
    }

    @Test
    void mainUsesDefaultPathsWithoutArguments() {
        Main.main(new String[0]);

        assertTrue(Files.exists(Main.RESULT_FILE));
    }

    @Test
    void mainSurvivesInvalidDocument() {
        Path file = GunXmlSamples.write(tempDir, GunXmlSamples.document());
        Path result = tempDir.resolve("result.xml");

        Main.main(new String[]{file.toString(), result.toString()});

        assertFalse(Files.exists(result));
    }
}
