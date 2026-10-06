package com.dagen.gunxml.parser;

import com.dagen.gunxml.GunXmlSamples;
import com.dagen.gunxml.model.Firearm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GunParserTest {

    @TempDir
    Path tempDir;

    @ParameterizedTest
    @EnumSource(ParserType.class)
    void parsesDataFile(ParserType type) {
        List<Firearm> firearms = type.create().parse(GunXmlSamples.XML_FILE);

        assertEquals(10, firearms.size());
        assertEquals(GunXmlSamples.GLOCK, firearms.get(0));
        assertEquals("FN SCAR-H", firearms.get(9).model());
    }

    @Test
    void allParsersProduceSameResult() {
        List<Firearm> expected = ParserType.DOM.create().parse(GunXmlSamples.XML_FILE);

        assertEquals(expected, ParserType.SAX.create().parse(GunXmlSamples.XML_FILE));
        assertEquals(expected, ParserType.STAX.create().parse(GunXmlSamples.XML_FILE));
    }

    @ParameterizedTest
    @EnumSource(ParserType.class)
    void parsesNumericBooleans(ParserType type) {
        String xml = GunXmlSamples.GLOCK_XML.replace("<Clip>true</Clip>", "<Clip>1</Clip>");
        Path file = GunXmlSamples.write(tempDir, GunXmlSamples.document(xml));

        assertEquals(List.of(GunXmlSamples.GLOCK), type.create().parse(file));
    }

    @ParameterizedTest
    @EnumSource(ParserType.class)
    void ignoresValuesOutsideFirearm(ParserType type) {
        Path file = GunXmlSamples.write(tempDir, GunXmlSamples.document("<Model>stray</Model>\n"));

        assertTrue(type.create().parse(file).isEmpty());
    }

    @ParameterizedTest
    @EnumSource(ParserType.class)
    void failsOnMissingFile(ParserType type) {
        GunParser parser = type.create();
        Path missing = tempDir.resolve("missing.xml");

        assertThrows(GunParsingException.class, () -> parser.parse(missing));
    }

    @ParameterizedTest
    @EnumSource(ParserType.class)
    void failsOnMalformedXml(ParserType type) {
        Path file = GunXmlSamples.write(tempDir, "<Gun><Firearm id=\"f1\"></Gun>");

        assertThrows(GunParsingException.class, () -> type.create().parse(file));
    }

    @ParameterizedTest
    @EnumSource(ParserType.class)
    void failsOnMissingElement(ParserType type) {
        String xml = GunXmlSamples.GLOCK_XML.replace("<Material>polymer</Material>", "");
        Path file = GunXmlSamples.write(tempDir, GunXmlSamples.document(xml));

        GunParsingException e = assertThrows(GunParsingException.class, () -> type.create().parse(file));
        assertEquals("firearm 'f1' has no <Material> element", e.getMessage());
    }

    @ParameterizedTest
    @EnumSource(ParserType.class)
    void failsOnUnknownElement(ParserType type) {
        String xml = GunXmlSamples.GLOCK_XML.replace("<TTC>", "<TTC><Caliber>9</Caliber>");
        Path file = GunXmlSamples.write(tempDir, GunXmlSamples.document(xml));

        assertThrows(GunParsingException.class, () -> type.create().parse(file));
    }

    @ParameterizedTest
    @EnumSource(ParserType.class)
    void failsOnMissingId(ParserType type) {
        String xml = GunXmlSamples.GLOCK_XML.replace(" id=\"f1\"", "");
        Path file = GunXmlSamples.write(tempDir, GunXmlSamples.document(xml));

        assertThrows(GunParsingException.class, () -> type.create().parse(file));
    }
}
