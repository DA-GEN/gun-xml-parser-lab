package com.dagen.gunxml.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GunElementTest {

    @Test
    void resolvesElementByTagName() {
        assertEquals(GunElement.TTC, GunElement.fromXml("TTC"));
        assertEquals(GunElement.SIGHTING_RANGE, GunElement.fromXml("SightingRange"));
    }

    @Test
    void distinguishesContainerAndValueElements() {
        assertFalse(GunElement.GUN.isLeaf());
        assertFalse(GunElement.FIREARM.isLeaf());
        assertFalse(GunElement.TTC.isLeaf());
        assertTrue(GunElement.MODEL.isLeaf());
        assertTrue(GunElement.OPTICS.isLeaf());
    }

    @Test
    void rejectsUnknownTag() {
        assertThrows(IllegalArgumentException.class, () -> GunElement.fromXml("Caliber"));
    }
}
