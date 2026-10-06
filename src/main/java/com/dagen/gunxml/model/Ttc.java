package com.dagen.gunxml.model;

import java.util.Objects;

public record Ttc(FiringRange firingRange, int sightingRange, boolean clip, boolean optics) {

    public Ttc {
        Objects.requireNonNull(firingRange, "firingRange");
        if (sightingRange <= 0) {
            throw new IllegalArgumentException("sighting range must be positive: " + sightingRange);
        }
    }
}
