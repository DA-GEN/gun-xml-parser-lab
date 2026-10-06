package com.dagen.gunxml.transform;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class XslTransformer {

    private static final Logger LOGGER = Logger.getLogger(XslTransformer.class.getName());
    private static final String JDK_STANDALONE_PROPERTY = "http://www.oracle.com/xml/is-standalone";

    private final Templates templates;

    public XslTransformer(Path xslFile) {
        try {
            templates = TransformerFactory.newInstance().newTemplates(new StreamSource(xslFile.toFile()));
        } catch (TransformerException e) {
            throw new XmlTransformationException("cannot load stylesheet " + xslFile + ": " + e.getMessage(), e);
        }
    }

    public void transform(Path sourceFile, Path resultFile) {
        try {
            Path parent = resultFile.toAbsolutePath().getParent();
            Files.createDirectories(parent);
            Transformer transformer = templates.newTransformer();
            transformer.setOutputProperty(JDK_STANDALONE_PROPERTY, "yes");
            transformer.transform(new StreamSource(sourceFile.toFile()), new StreamResult(resultFile.toFile()));
        } catch (TransformerException | IOException e) {
            throw new XmlTransformationException("cannot transform " + sourceFile + ": " + e.getMessage(), e);
        }
        LOGGER.info(() -> sourceFile + " transformed into " + resultFile);
    }
}
