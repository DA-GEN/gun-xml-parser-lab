package com.dagen.gunxml.sort;

import com.dagen.gunxml.model.Firearm;
import com.dagen.gunxml.model.FiringRange;
import com.dagen.gunxml.model.Handy;
import com.dagen.gunxml.model.Ttc;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FirearmComparatorTest {

    private static final Firearm GLOCK = new Firearm("f1", "Glock 17", Handy.ONE_HANDED, "Austria",
            new Ttc(FiringRange.CLOSE, 50, true, false), "polymer");
    private static final Firearm BARRETT = new Firearm("f5", "Barrett M82", Handy.TWO_HANDED, "USA",
            new Ttc(FiringRange.FAR, 1800, true, true), "steel");
    private static final Firearm G36 = new Firearm("f4", "HK G36", Handy.TWO_HANDED, "Germany",
            new Ttc(FiringRange.MEDIUM, 800, true, true), "polymer");

    private static final List<Firearm> FIREARMS = List.of(G36, BARRETT, GLOCK);

    private static List<Firearm> sorted(FirearmComparator comparator) {
        return FIREARMS.stream().sorted(comparator).toList();
    }

    @Test
    void sortsByModel() {
        assertEquals(List.of(BARRETT, GLOCK, G36), sorted(FirearmComparator.BY_MODEL));
    }

    @Test
    void sortsByOrigin() {
        assertEquals(List.of(GLOCK, G36, BARRETT), sorted(FirearmComparator.BY_ORIGIN));
    }

    @Test
    void sortsByHandy() {
        assertEquals(List.of(GLOCK, G36, BARRETT), sorted(FirearmComparator.BY_HANDY));
    }

    @Test
    void sortsByFiringRangeFromCloseToFar() {
        assertEquals(List.of(GLOCK, G36, BARRETT), sorted(FirearmComparator.BY_FIRING_RANGE));
    }

    @Test
    void sortsBySightingRange() {
        assertEquals(List.of(GLOCK, G36, BARRETT), sorted(FirearmComparator.BY_SIGHTING_RANGE));
    }

    @Test
    void combinesComparators() {
        List<Firearm> result = FIREARMS.stream()
                .sorted(FirearmComparator.BY_HANDY.thenComparing(FirearmComparator.BY_MODEL.reversed()))
                .toList();

        assertEquals(List.of(GLOCK, G36, BARRETT), result);
    }
}
