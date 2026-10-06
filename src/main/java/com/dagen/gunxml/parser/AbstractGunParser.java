package com.dagen.gunxml.parser;

import com.dagen.gunxml.model.Firearm;

import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractGunParser implements GunParser {

    private static final Logger LOGGER = Logger.getLogger(AbstractGunParser.class.getName());

    @Override
    public final List<Firearm> parse(Path file) {
        String parserName = getClass().getSimpleName();
        LOGGER.fine(() -> parserName + " started parsing " + file);
        List<Firearm> firearms;
        try {
            firearms = parseDocument(file);
        } catch (GunParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new GunParsingException(parserName + " failed to parse " + file + ": " + e.getMessage(), e);
        }
        LOGGER.info(() -> parserName + " read " + firearms.size() + " firearms from " + file);
        return firearms;
    }

    protected abstract List<Firearm> parseDocument(Path file) throws Exception;
}
