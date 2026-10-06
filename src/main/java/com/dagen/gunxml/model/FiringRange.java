package com.dagen.gunxml.model;

public enum FiringRange implements XmlEnum {
    CLOSE("close"),
    MEDIUM("medium"),
    FAR("far");

    private final String xmlValue;

    FiringRange(String xmlValue) {
        this.xmlValue = xmlValue;
    }

    @Override
    public String xmlValue() {
        return xmlValue;
    }
}
