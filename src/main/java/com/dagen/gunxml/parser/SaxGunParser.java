package com.dagen.gunxml.parser;

import com.dagen.gunxml.model.Firearm;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import java.nio.file.Path;
import java.util.List;

public class SaxGunParser extends AbstractGunParser {

    @Override
    protected List<Firearm> parseDocument(Path file) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        GunSaxHandler handler = new GunSaxHandler();
        factory.newSAXParser().parse(file.toFile(), handler);
        return handler.getFirearms();
    }
}
