package com.dagen.gunxml.app;

import com.dagen.gunxml.model.Firearm;
import com.dagen.gunxml.parser.GunParsingException;
import com.dagen.gunxml.parser.ParserType;
import com.dagen.gunxml.sort.FirearmComparator;
import com.dagen.gunxml.transform.XmlTransformationException;
import com.dagen.gunxml.transform.XslTransformer;
import com.dagen.gunxml.validation.XmlValidationException;
import com.dagen.gunxml.validation.XmlValidator;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    static final Path XML_FILE = Path.of("data", "gun.xml");
    static final Path XSD_FILE = Path.of("data", "gun.xsd");
    static final Path XSL_FILE = Path.of("data", "gun-by-origin.xsl");
    static final Path RESULT_FILE = Path.of("output", "gun-by-origin.xml");

    private Main() {
    }

    public static void main(String[] args) {
        LoggingConfig.init();
        Path xmlFile = args.length > 0 ? Path.of(args[0]) : XML_FILE;
        Path resultFile = args.length > 1 ? Path.of(args[1]) : RESULT_FILE;
        if (!run(xmlFile, resultFile)) {
            LOGGER.severe("processing finished with errors");
        }
    }

    static boolean run(Path xmlFile, Path resultFile) {
        try {
            new XmlValidator(XSD_FILE).validate(xmlFile);

            List<List<Firearm>> results = Arrays.stream(ParserType.values())
                    .map(type -> type.create().parse(xmlFile))
                    .toList();
            List<Firearm> firearms = results.get(0);
            LOGGER.info(() -> "DOM, SAX and StAX results identical: " + results.stream().allMatch(firearms::equals));

            logSorted(firearms, "origin, then model",
                    FirearmComparator.BY_ORIGIN.thenComparing(FirearmComparator.BY_MODEL));
            logSorted(firearms, "sighting range", FirearmComparator.BY_SIGHTING_RANGE);

            new XslTransformer(XSL_FILE).transform(xmlFile, resultFile);
            return true;
        } catch (XmlValidationException | GunParsingException | XmlTransformationException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.log(Level.FINE, "error details", e);
            return false;
        }
    }

    private static void logSorted(List<Firearm> firearms, String criterion,
                                  Comparator<Firearm> comparator) {
        LOGGER.info("firearms sorted by " + criterion + ":");
        firearms.stream()
                .sorted(comparator)
                .forEach(firearm -> LOGGER.info(() -> "  " + firearm));
    }
}
