package com.dagen.gunxml.parser;

import com.dagen.gunxml.model.FiringRange;
import com.dagen.gunxml.model.Firearm;
import com.dagen.gunxml.model.Handy;
import com.dagen.gunxml.model.Ttc;
import com.dagen.gunxml.model.XmlEnum;

public class FirearmBuilder {

    private final String id;
    private String model;
    private Handy handy;
    private String origin;
    private FiringRange firingRange;
    private Integer sightingRange;
    private Boolean clip;
    private Boolean optics;
    private String material;

    public FirearmBuilder(String id) {
        if (id == null || id.isBlank()) {
            throw new GunParsingException("firearm has no " + GunElement.ID_ATTRIBUTE + " attribute");
        }
        this.id = id;
    }

    public FirearmBuilder set(GunElement element, String value) {
        switch (element) {
            case MODEL -> model = value;
            case HANDY -> handy = XmlEnum.fromXml(Handy.class, value);
            case ORIGIN -> origin = value;
            case FIRING_RANGE -> firingRange = XmlEnum.fromXml(FiringRange.class, value);
            case SIGHTING_RANGE -> sightingRange = Integer.valueOf(value);
            case CLIP -> clip = parseXmlBoolean(value);
            case OPTICS -> optics = parseXmlBoolean(value);
            case MATERIAL -> material = value;
            default -> throw new IllegalArgumentException(element.xmlValue() + " is not a value element");
        }
        return this;
    }

    public Firearm build() {
        Ttc ttc = new Ttc(require(firingRange, GunElement.FIRING_RANGE),
                require(sightingRange, GunElement.SIGHTING_RANGE),
                require(clip, GunElement.CLIP),
                require(optics, GunElement.OPTICS));
        return new Firearm(id, require(model, GunElement.MODEL), require(handy, GunElement.HANDY),
                require(origin, GunElement.ORIGIN), ttc, require(material, GunElement.MATERIAL));
    }

    private <T> T require(T value, GunElement element) {
        if (value == null) {
            throw new GunParsingException("firearm '" + id + "' has no <" + element.xmlValue() + "> element");
        }
        return value;
    }

    private static boolean parseXmlBoolean(String value) {
        return switch (value) {
            case "true", "1" -> true;
            case "false", "0" -> false;
            default -> throw new IllegalArgumentException("not an xs:boolean value: " + value);
        };
    }
}
