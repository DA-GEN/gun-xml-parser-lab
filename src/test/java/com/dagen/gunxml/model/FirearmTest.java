package com.dagen.gunxml.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FirearmTest {

    private static final Ttc TTC = new Ttc(FiringRange.FAR, 1800, true, true);

    @Test
    void keepsAllComponents() {
        Firearm firearm = new Firearm("f5", "Barrett M82", Handy.TWO_HANDED, "USA", TTC, "steel");

        assertEquals("Barrett M82", firearm.model());
        assertEquals(1800, firearm.ttc().sightingRange());
    }

    @Test
    void rejectsMissingComponents() {
        assertThrows(NullPointerException.class,
                () -> new Firearm(null, "M82", Handy.TWO_HANDED, "USA", TTC, "steel"));
        assertThrows(NullPointerException.class,
                () -> new Firearm("f5", null, Handy.TWO_HANDED, "USA", TTC, "steel"));
        assertThrows(NullPointerException.class,
                () -> new Firearm("f5", "M82", null, "USA", TTC, "steel"));
        assertThrows(NullPointerException.class,
                () -> new Firearm("f5", "M82", Handy.TWO_HANDED, null, TTC, "steel"));
        assertThrows(NullPointerException.class,
                () -> new Firearm("f5", "M82", Handy.TWO_HANDED, "USA", null, "steel"));
        assertThrows(NullPointerException.class,
                () -> new Firearm("f5", "M82", Handy.TWO_HANDED, "USA", TTC, null));
    }

    @Test
    void ttcRejectsInvalidValues() {
        assertThrows(NullPointerException.class, () -> new Ttc(null, 100, true, true));
        assertThrows(IllegalArgumentException.class, () -> new Ttc(FiringRange.CLOSE, 0, true, true));
    }
}
