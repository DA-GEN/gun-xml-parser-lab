package com.dagen.gunxml.validation;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

public class XmlValidator {

    private static final Logger LOGGER = Logger.getLogger(XmlValidator.class.getName());

    private final Schema schema;

    public XmlValidator(Path xsdFile) {
        try {
            schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(xsdFile.toFile());
        } catch (SAXException e) {
            throw new XmlValidationException("cannot load schema " + xsdFile + ": " + e.getMessage(), e);
        }
    }

    public void validate(Path xmlFile) {
        try {
            schema.newValidator().validate(new StreamSource(xmlFile.toFile()));
        } catch (SAXParseException e) {
            throw new XmlValidationException(xmlFile + " is invalid at line " + e.getLineNumber()
                    + ": " + e.getMessage(), e);
        } catch (SAXException | IOException e) {
            throw new XmlValidationException("cannot validate " + xmlFile + ": " + e.getMessage(), e);
        }
        LOGGER.info(() -> xmlFile + " is valid against the schema");
    }

    public boolean isValid(Path xmlFile) {
        try {
            validate(xmlFile);
            return true;
        } catch (XmlValidationException e) {
            LOGGER.warning(e.getMessage());
            return false;
        }
    }
}
