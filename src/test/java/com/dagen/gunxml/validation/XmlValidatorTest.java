package com.dagen.gunxml.validation;

import com.dagen.gunxml.GunXmlSamples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlValidatorTest {

    private final XmlValidator validator = new XmlValidator(GunXmlSamples.XSD_FILE);

    @TempDir
    Path tempDir;

    private Path writeGlockWith(String target, String replacement) {
        String xml = GunXmlSamples.GLOCK_XML.replace(target, replacement);
        return GunXmlSamples.write(tempDir, GunXmlSamples.document(xml));
    }

    @Test
    void acceptsDataFile() {
        assertDoesNotThrow(() -> validator.validate(GunXmlSamples.XML_FILE));
        assertTrue(validator.isValid(GunXmlSamples.XML_FILE));
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 3000})
    void acceptsSightingRangeBoundaries(int range) {
        Path file = writeGlockWith("<SightingRange>50", "<SightingRange>" + range);

        assertTrue(validator.isValid(file));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "<SightingRange>50</SightingRange>|<SightingRange>9</SightingRange>",
            "<SightingRange>50</SightingRange>|<SightingRange>3001</SightingRange>",
            "<Handy>one-handed</Handy>|<Handy>three-handed</Handy>",
            "<FiringRange>close</FiringRange>|<FiringRange>very far</FiringRange>",
            "<Origin>Austria</Origin>|<Origin>South Korea</Origin>",
            "<Clip>true</Clip>|<Clip>yes</Clip>",
            "<Material>polymer</Material>|<Material>x</Material>",
            "<Model>Glock 17</Model>|<Model></Model>",
            "<Material>polymer</Material>|''",
            "' id=\"f1\"'|''",
            "id=\"f1\"|id=\"1\""
    })
    void rejectsInvalidDocument(String target, String replacement) {
        Path file = writeGlockWith(target, replacement);

        assertThrows(XmlValidationException.class, () -> validator.validate(file));
        assertFalse(validator.isValid(file));
    }

    @Test
    void rejectsDuplicateIds() {
        Path file = GunXmlSamples.write(tempDir,
                GunXmlSamples.document(GunXmlSamples.GLOCK_XML, GunXmlSamples.GLOCK_XML));

        assertFalse(validator.isValid(file));
    }

    @Test
    void rejectsEmptyGun() {
        Path file = GunXmlSamples.write(tempDir, GunXmlSamples.document());

        assertFalse(validator.isValid(file));
    }

    @Test
    void failsOnMissingFile() {
        Path missing = tempDir.resolve("missing.xml");

        assertThrows(XmlValidationException.class, () -> validator.validate(missing));
    }

    @Test
    void failsOnBrokenSchema() {
        Path schema = GunXmlSamples.write(tempDir, "<not-a-schema/>");

        assertThrows(XmlValidationException.class, () -> new XmlValidator(schema));
    }
}
