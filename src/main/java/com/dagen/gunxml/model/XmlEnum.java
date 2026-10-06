package com.dagen.gunxml.model;

public interface XmlEnum {

    String xmlValue();

    static <E extends Enum<E> & XmlEnum> E fromXml(Class<E> type, String value) {
        for (E constant : type.getEnumConstants()) {
            if (constant.xmlValue().equals(value)) {
                return constant;
            }
        }
        throw new IllegalArgumentException("unknown " + type.getSimpleName() + " value: " + value);
    }
}
