package com.dagen.gunxml.parser;

import com.dagen.gunxml.model.Firearm;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DomGunParser extends AbstractGunParser {

    @Override
    protected List<Firearm> parseDocument(Path file) throws Exception {
        NodeList nodes = newDocumentBuilder().parse(file.toFile())
                .getElementsByTagName(GunElement.FIREARM.xmlValue());
        List<Firearm> firearms = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            FirearmBuilder builder = new FirearmBuilder(element.getAttribute(GunElement.ID_ATTRIBUTE));
            fillLeaves(element, builder);
            firearms.add(builder.build());
        }
        return firearms;
    }

    private void fillLeaves(Element parent, FirearmBuilder builder) {
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element child = (Element) children.item(i);
            GunElement element = GunElement.fromXml(child.getTagName());
            if (element.isLeaf()) {
                builder.set(element, child.getTextContent().strip());
            } else {
                fillLeaves(child, builder);
            }
        }
    }

    private static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        return factory.newDocumentBuilder();
    }
}
