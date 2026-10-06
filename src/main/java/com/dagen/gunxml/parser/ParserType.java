package com.dagen.gunxml.parser;

import java.util.function.Supplier;

public enum ParserType {
    DOM(DomGunParser::new),
    SAX(SaxGunParser::new),
    STAX(StaxGunParser::new);

    private final Supplier<GunParser> factory;

    ParserType(Supplier<GunParser> factory) {
        this.factory = factory;
    }

    public GunParser create() {
        return factory.get();
    }
}
