package com.dagen.gunxml.parser;

import com.dagen.gunxml.model.XmlEnum;

public enum GunElement implements XmlEnum {
    GUN("Gun", false),
    FIREARM("Firearm", false),
    MODEL("Model", true),
    HANDY("Handy", true),
    ORIGIN("Origin", true),
    TTC("TTC", false),
    FIRING_RANGE("FiringRange", true),
    SIGHTING_RANGE("SightingRange", true),
    CLIP("Clip", true),
    OPTICS("Optics", true),
    MATERIAL("Material", true);

    public static final String ID_ATTRIBUTE = "id";

    private final String xmlValue;
    private final boolean leaf;

    GunElement(String xmlValue, boolean leaf) {
        this.xmlValue = xmlValue;
        this.leaf = leaf;
    }

    @Override
    public String xmlValue() {
        return xmlValue;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public static GunElement fromXml(String name) {
        return XmlEnum.fromXml(GunElement.class, name);
    }
}
