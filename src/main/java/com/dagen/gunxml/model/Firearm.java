package com.dagen.gunxml.model;

import java.util.Objects;

public record Firearm(String id, String model, Handy handy, String origin, Ttc ttc, String material) {

    public Firearm {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(model, "model");
        Objects.requireNonNull(handy, "handy");
        Objects.requireNonNull(origin, "origin");
        Objects.requireNonNull(ttc, "ttc");
        Objects.requireNonNull(material, "material");
    }
}
