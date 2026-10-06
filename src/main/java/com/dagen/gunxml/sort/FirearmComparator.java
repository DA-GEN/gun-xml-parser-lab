package com.dagen.gunxml.sort;

import com.dagen.gunxml.model.Firearm;

import java.util.Comparator;

public enum FirearmComparator implements Comparator<Firearm> {
    BY_MODEL(Comparator.comparing(Firearm::model)),
    BY_ORIGIN(Comparator.comparing(Firearm::origin)),
    BY_HANDY(Comparator.comparing(Firearm::handy)),
    BY_FIRING_RANGE(Comparator.comparing(firearm -> firearm.ttc().firingRange())),
    BY_SIGHTING_RANGE(Comparator.comparingInt(firearm -> firearm.ttc().sightingRange()));

    private final Comparator<Firearm> delegate;

    FirearmComparator(Comparator<Firearm> delegate) {
        this.delegate = delegate;
    }

    @Override
    public int compare(Firearm first, Firearm second) {
        return delegate.compare(first, second);
    }
}
