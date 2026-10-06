package com.dagen.gunxml;

import com.dagen.gunxml.model.Firearm;
import com.dagen.gunxml.model.FiringRange;
import com.dagen.gunxml.model.Handy;
import com.dagen.gunxml.model.Ttc;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class GunXmlSamples {

    public static final Path XML_FILE = Path.of("data", "gun.xml");
    public static final Path XSD_FILE = Path.of("data", "gun.xsd");
    public static final Path XSL_FILE = Path.of("data", "gun-by-origin.xsl");

    public static final Firearm GLOCK = new Firearm("f1", "Glock 17", Handy.ONE_HANDED, "Austria",
            new Ttc(FiringRange.CLOSE, 50, true, false), "polymer");

    public static final String GLOCK_XML = """
            <Firearm id="f1">
                <Model>Glock 17</Model>
                <Handy>one-handed</Handy>
                <Origin>Austria</Origin>
                <TTC>
                    <FiringRange>close</FiringRange>
                    <SightingRange>50</SightingRange>
                    <Clip>true</Clip>
                    <Optics>false</Optics>
                </TTC>
                <Material>polymer</Material>
            </Firearm>
            """;

    private GunXmlSamples() {
    }

    public static String document(String... firearms) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Gun>\n" + String.join("", firearms) + "</Gun>\n";
    }

    public static Path write(Path dir, String content) {
        try {
            return Files.writeString(dir.resolve("gun.xml"), content);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
