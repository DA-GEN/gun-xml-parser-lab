package com.dagen.gunxml.model;

public enum Handy implements XmlEnum {
    ONE_HANDED("one-handed"),
    TWO_HANDED("two-handed");

    private final String xmlValue;

    Handy(String xmlValue) {
        this.xmlValue = xmlValue;
    }

    @Override
    public String xmlValue() {
        return xmlValue;
    }
}
