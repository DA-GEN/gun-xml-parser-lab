package com.dagen.gunxml.parser;

import com.dagen.gunxml.GunXmlSamples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FirearmBuilderTest {

    private static FirearmBuilder glockBuilder() {
        return new FirearmBuilder("f1")
                .set(GunElement.MODEL, "Glock 17")
                .set(GunElement.HANDY, "one-handed")
                .set(GunElement.ORIGIN, "Austria")
                .set(GunElement.FIRING_RANGE, "close")
                .set(GunElement.SIGHTING_RANGE, "50")
                .set(GunElement.CLIP, "true")
                .set(GunElement.OPTICS, "false")
                .set(GunElement.MATERIAL, "polymer");
    }

    @Test
    void buildsFirearmFromElementValues() {
        assertEquals(GunXmlSamples.GLOCK, glockBuilder().build());
    }

    @Test
    void acceptsNumericXmlBooleans() {
        var ttc = glockBuilder().set(GunElement.CLIP, "0").set(GunElement.OPTICS, "1").build().ttc();

        assertFalse(ttc.clip());
        assertTrue(ttc.optics());
    }

    @Test
    void rejectsInvalidValues() {
        FirearmBuilder builder = glockBuilder();

        assertThrows(IllegalArgumentException.class, () -> builder.set(GunElement.CLIP, "yes"));
        assertThrows(IllegalArgumentException.class, () -> builder.set(GunElement.HANDY, "three-handed"));
        assertThrows(NumberFormatException.class, () -> builder.set(GunElement.SIGHTING_RANGE, "far"));
        assertThrows(IllegalArgumentException.class, () -> builder.set(GunElement.TTC, "x"));
    }

    @Test
    void rejectsMissingId() {
        assertThrows(GunParsingException.class, () -> new FirearmBuilder(null));
        assertThrows(GunParsingException.class, () -> new FirearmBuilder(" "));
    }

    @Test
    void reportsMissingElement() {
        FirearmBuilder builder = new FirearmBuilder("f1")
                .set(GunElement.FIRING_RANGE, "close")
                .set(GunElement.SIGHTING_RANGE, "50")
                .set(GunElement.CLIP, "true")
                .set(GunElement.OPTICS, "false");

        GunParsingException e = assertThrows(GunParsingException.class, builder::build);
        assertEquals("firearm 'f1' has no <Model> element", e.getMessage());
    }
}
