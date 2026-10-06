package com.dagen.gunxml.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XmlEnumTest {

    @Test
    void resolvesConstantsByXmlValue() {
        assertEquals(Handy.ONE_HANDED, XmlEnum.fromXml(Handy.class, "one-handed"));
        assertEquals(Handy.TWO_HANDED, XmlEnum.fromXml(Handy.class, "two-handed"));
        assertEquals(FiringRange.CLOSE, XmlEnum.fromXml(FiringRange.class, "close"));
        assertEquals(FiringRange.MEDIUM, XmlEnum.fromXml(FiringRange.class, "medium"));
        assertEquals(FiringRange.FAR, XmlEnum.fromXml(FiringRange.class, "far"));
    }

    @Test
    void rejectsUnknownValue() {
        assertThrows(IllegalArgumentException.class, () -> XmlEnum.fromXml(Handy.class, "ONE_HANDED"));
    }
}
