package com.dagen.gunxml.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class ParserTypeTest {

    @Test
    void createsMatchingParser() {
        assertInstanceOf(DomGunParser.class, ParserType.DOM.create());
        assertInstanceOf(SaxGunParser.class, ParserType.SAX.create());
        assertInstanceOf(StaxGunParser.class, ParserType.STAX.create());
    }

    @Test
    void createsNewInstanceEachTime() {
        assertNotSame(ParserType.SAX.create(), ParserType.SAX.create());
    }
}
