package com.dagen.gunxml.transform;

import com.dagen.gunxml.GunXmlSamples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XslTransformerTest {

    private final XslTransformer transformer = new XslTransformer(GunXmlSamples.XSL_FILE);

    @TempDir
    Path tempDir;

    private Path result;

    @BeforeEach
    void setUp() {
        result = tempDir.resolve("nested").resolve("result.xml");
    }

    private static List<Element> childElements(Element parent) {
        List<Element> elements = new ArrayList<>();
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                elements.add((Element) children.item(i));
            }
        }
        return elements;
    }

    @Test
    void groupsFirearmsByOrigin() throws Exception {
        transformer.transform(GunXmlSamples.XML_FILE, result);

        Element root = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(result.toFile()).getDocumentElement();
        List<Element> groups = childElements(root);

        assertEquals("Gun", root.getTagName());
        assertEquals(List.of("Austria", "Belgium", "Germany", "Ukraine", "USA"),
                groups.stream().map(Element::getTagName).toList());
        int total = 0;
        for (Element group : groups) {
            for (Element firearm : childElements(group)) {
                assertEquals("Firearm", firearm.getTagName());
                assertEquals(group.getTagName(),
                        firearm.getElementsByTagName("Origin").item(0).getTextContent());
                total++;
            }
        }
        assertEquals(10, total);
    }

    @Test
    void dropsSchemaNamespaceFromCopiedElements() throws Exception {
        transformer.transform(GunXmlSamples.XML_FILE, result);

        assertFalse(Files.readString(result).contains("xmlns"));
    }

    @Test
    void failsOnMissingSource() {
        Path missing = tempDir.resolve("missing.xml");

        assertThrows(XmlTransformationException.class, () -> transformer.transform(missing, result));
    }

    @Test
    void failsOnBrokenStylesheet() {
        Path stylesheet = GunXmlSamples.write(tempDir, "<xsl:stylesheet");

        assertThrows(XmlTransformationException.class, () -> new XslTransformer(stylesheet));
    }
}
