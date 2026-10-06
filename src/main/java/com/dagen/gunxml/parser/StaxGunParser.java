package com.dagen.gunxml.parser;

import com.dagen.gunxml.model.Firearm;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StaxGunParser extends AbstractGunParser {

    @Override
    protected List<Firearm> parseDocument(Path file) throws Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        try (InputStream in = Files.newInputStream(file)) {
            XMLStreamReader reader = factory.createXMLStreamReader(in);
            try {
                return readFirearms(reader);
            } finally {
                reader.close();
            }
        }
    }

    private List<Firearm> readFirearms(XMLStreamReader reader) throws Exception {
        List<Firearm> firearms = new ArrayList<>();
        FirearmBuilder current = null;
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                GunElement element = GunElement.fromXml(reader.getLocalName());
                if (element == GunElement.FIREARM) {
                    current = new FirearmBuilder(reader.getAttributeValue(null, GunElement.ID_ATTRIBUTE));
                } else if (element.isLeaf() && current != null) {
                    current.set(element, reader.getElementText().strip());
                }
            } else if (event == XMLStreamConstants.END_ELEMENT
                    && GunElement.fromXml(reader.getLocalName()) == GunElement.FIREARM) {
                firearms.add(current.build());
                current = null;
            }
        }
        return firearms;
    }
}
