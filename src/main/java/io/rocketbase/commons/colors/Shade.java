package io.rocketbase.commons.colors;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * a step within a color family - from the lightest tint up to the darkest shade
 * <p>
 * both supported scales are mapped onto the same ordinal range so that a range
 * like {@link #lights()} means the same thing in every palette:
 * tailwind uses 50, 100, 200 ... 950 while open color counts 0 to 9.
 */
@Getter
public enum Shade {

    S50(50, 0),
    S100(100, 1),
    S200(200, 2),
    S300(300, 3),
    S400(400, 4),
    S500(500, 5),
    S600(600, 6),
    S700(700, 7),
    S800(800, 8),
    S900(900, 9),
    S950(950, null);

    /**
     * step as used by tailwind - 50, 100 ... 950
     */
    private final int tailwindStep;
    /**
     * step as used by open color - 0 to 9, null when the scale has no such step
     */
    private final Integer openColorStep;

    Shade(int tailwindStep, Integer openColorStep) {
        this.tailwindStep = tailwindStep;
        this.openColorStep = openColorStep;
    }

    /**
     * @param step tailwind notation - 50, 100, 200 ... 950
     * @return matching shade or null when the step does not exist
     */
    public static Shade ofTailwindStep(int step) {
        for (Shade shade : values()) {
            if (shade.tailwindStep == step) {
                return shade;
            }
        }
        return null;
    }

    /**
     * @param step open color notation - 0 to 9
     * @return matching shade or null when the step does not exist
     */
    public static Shade ofOpenColorStep(int step) {
        for (Shade shade : values()) {
            if (shade.openColorStep != null && shade.openColorStep == step) {
                return shade;
            }
        }
        return null;
    }

    /**
     * @return the lighter half of the scale - 50 to 400
     */
    public static List<Shade> lights() {
        return range(S50, S400);
    }

    /**
     * @return the middle of the scale - 400 to 600, the shades that work as an accent
     */
    public static List<Shade> mids() {
        return range(S400, S600);
    }

    /**
     * @return the darker half of the scale - 600 to 950
     */
    public static List<Shade> darks() {
        return range(S600, S950);
    }

    /**
     * @param from first shade - inclusive
     * @param to   last shade - inclusive
     * @return all shades between the two, order is kept from light to dark
     */
    public static List<Shade> range(Shade from, Shade to) {
        if (from == null || to == null) {
            return Collections.emptyList();
        }
        int start = Math.min(from.ordinal(), to.ordinal());
        int end = Math.max(from.ordinal(), to.ordinal());
        return new ArrayList<>(Arrays.asList(values()).subList(start, end + 1));
    }

    /**
     * @return a random shade of the full scale
     */
    public static Shade random() {
        return random(Arrays.asList(values()));
    }

    /**
     * @param candidates shades to choose from - for example {@link #mids()}
     * @return a random shade of the given ones or null when nothing was given
     */
    public static Shade random(List<Shade> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }
        return candidates.get(new Random().nextInt(candidates.size()));
    }

    /**
     * @return true when this shade is part of the lighter half
     */
    public boolean isLight() {
        return ordinal() <= S400.ordinal();
    }

    /**
     * @return true when this shade is part of the darker half
     */
    public boolean isDark() {
        return ordinal() >= S600.ordinal();
    }
}
