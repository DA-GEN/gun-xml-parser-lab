package com.dagen.gunxml.parser;

import com.dagen.gunxml.model.Firearm;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class GunSaxHandler extends DefaultHandler {

    private final List<Firearm> firearms = new ArrayList<>();
    private final StringBuilder text = new StringBuilder();
    private FirearmBuilder current;

    public List<Firearm> getFirearms() {
        return firearms;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        text.setLength(0);
        if (GunElement.fromXml(qName) == GunElement.FIREARM) {
            current = new FirearmBuilder(attributes.getValue(GunElement.ID_ATTRIBUTE));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        text.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        GunElement element = GunElement.fromXml(qName);
        if (element == GunElement.FIREARM) {
            firearms.add(current.build());
            current = null;
        } else if (element.isLeaf() && current != null) {
            current.set(element, text.toString().strip());
        }
    }
}
