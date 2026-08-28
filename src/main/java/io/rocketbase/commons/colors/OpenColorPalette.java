package io.rocketbase.commons.colors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * the open color palette 1.9.1 - 13 families with 10 shades each, designed for user interfaces
 * <p>
 * open color counts its steps from 0 (lightest) to 9 (darkest), which maps onto
 * {@link Shade#S50} through {@link Shade#S900} here.
 * <p>
 * <pre>
 * String hex = OpenColorPalette.BLUE.shade(5).getHexCode();
 * OpenColorShade random = OpenColorPalette.randomShade(Shade.darks());
 * </pre>
 * <p>
 * colors by heeyeun, mit licensed - see https://yeun.github.io/open-color/
 */
public enum OpenColorPalette {

    GRAY("f8f9fa", "f1f3f5", "e9ecef", "dee2e6", "ced4da", "adb5bd", "868e96", "495057", "343a40", "212529"),
    RED("fff5f5", "ffe3e3", "ffc9c9", "ffa8a8", "ff8787", "ff6b6b", "fa5252", "f03e3e", "e03131", "c92a2a"),
    PINK("fff0f6", "ffdeeb", "fcc2d7", "faa2c1", "f783ac", "f06595", "e64980", "d6336c", "c2255c", "a61e4d"),
    GRAPE("f8f0fc", "f3d9fa", "eebefa", "e599f7", "da77f2", "cc5de8", "be4bdb", "ae3ec9", "9c36b5", "862e9c"),
    VIOLET("f3f0ff", "e5dbff", "d0bfff", "b197fc", "9775fa", "845ef7", "7950f2", "7048e8", "6741d9", "5f3dc4"),
    INDIGO("edf2ff", "dbe4ff", "bac8ff", "91a7ff", "748ffc", "5c7cfa", "4c6ef5", "4263eb", "3b5bdb", "364fc7"),
    BLUE("e7f5ff", "d0ebff", "a5d8ff", "74c0fc", "4dabf7", "339af0", "228be6", "1c7ed6", "1971c2", "1864ab"),
    CYAN("e3fafc", "c5f6fa", "99e9f2", "66d9e8", "3bc9db", "22b8cf", "15aabf", "1098ad", "0c8599", "0b7285"),
    TEAL("e6fcf5", "c3fae8", "96f2d7", "63e6be", "38d9a9", "20c997", "12b886", "0ca678", "099268", "087f5b"),
    GREEN("ebfbee", "d3f9d8", "b2f2bb", "8ce99a", "69db7c", "51cf66", "40c057", "37b24d", "2f9e44", "2b8a3e"),
    LIME("f4fce3", "e9fac8", "d8f5a2", "c0eb75", "a9e34b", "94d82d", "82c91e", "74b816", "66a80f", "5c940d"),
    YELLOW("fff9db", "fff3bf", "ffec99", "ffe066", "ffd43b", "fcc419", "fab005", "f59f00", "f08c00", "e67700"),
    ORANGE("fff4e6", "ffe8cc", "ffd8a8", "ffc078", "ffa94d", "ff922b", "fd7e14", "f76707", "e8590c", "d9480f");
    private final String[] hexCodes;
    private volatile Map<Shade, OpenColorShade> shades;

    OpenColorPalette(String... hexCodes) {
        this.hexCodes = hexCodes;
    }

    /**
     * @return all shades of this family keyed by their step
     */
    public Map<Shade, OpenColorShade> getShades() {
        Map<Shade, OpenColorShade> result = shades;
        if (result == null) {
            synchronized (this) {
                result = shades;
                if (result == null) {
                    Map<Shade, OpenColorShade> map = new EnumMap<>(Shade.class);
                    List<Shade> scale = Shade.range(Shade.S50, Shade.S900);
                    for (int i = 0; i < scale.size(); i++) {
                        map.put(scale.get(i), new OpenColorShade(this, scale.get(i), hexCodes[i]));
                    }
                    result = Collections.unmodifiableMap(map);
                    shades = result;
                }
            }
        }
        return result;
    }

    /**
     * @param shade step of the scale
     * @return the color of this family at the given shade or null when open color has no such step
     */
    public OpenColorShade shade(Shade shade) {
        return shade == null ? null : getShades().get(shade);
    }

    /**
     * @param step open color notation - 0 to 9
     * @return the color of this family at the given step or null when the step does not exist
     */
    public OpenColorShade shade(int step) {
        return shade(Shade.ofOpenColorStep(step));
    }

    /**
     * @return the step 5 shade, the middle of the scale
     */
    public OpenColorShade base() {
        return shade(Shade.S500);
    }

    /**
     * @return all shades of this family, from light to dark
     */
    public List<OpenColorShade> allShades() {
        List<OpenColorShade> result = new ArrayList<>();
        for (Shade shade : Shade.range(Shade.S50, Shade.S900)) {
            result.add(shade(shade));
        }
        return result;
    }

    /**
     * @param candidates shades to choose from - for example {@link Shade#mids()}
     * @return a random shade of this family or null when none of them exists here
     */
    public OpenColorShade random(List<Shade> candidates) {
        List<OpenColorShade> pool = new ArrayList<>();
        if (candidates != null) {
            for (Shade shade : candidates) {
                OpenColorShade value = shade(shade);
                if (value != null) {
                    pool.add(value);
                }
            }
        }
        return pool.isEmpty() ? null : pool.get(new Random().nextInt(pool.size()));
    }

    /**
     * @return every color of the palette - 13 families with 10 shades
     */
    public static List<OpenColorShade> all() {
        List<OpenColorShade> result = new ArrayList<>();
        for (OpenColorPalette family : values()) {
            result.addAll(family.allShades());
        }
        return result;
    }

    /**
     * @return a random family
     */
    public static OpenColorPalette randomFamily() {
        return values()[new Random().nextInt(values().length)];
    }

    /**
     * @param candidates shades to pick from - for example {@link Shade#mids()}
     * @return a random color of a random family within the given shades
     */
    public static OpenColorShade randomShade(List<Shade> candidates) {
        return randomFamily().random(candidates);
    }

    /**
     * @return a random color of the whole palette
     */
    public static OpenColorShade randomShade() {
        return randomShade(Shade.range(Shade.S50, Shade.S900));
    }

    /**
     * finds the perceptually closest color of the palette
     *
     * @param colorCode color in any supported notation - rgb, hsl, oklch or hex
     * @return nearest color or null when the given code is not valid
     */
    public static OpenColorShade getNearest(String colorCode) {
        return ColorCode.getNearest(all(), colorCode);
    }

    /**
     * @param candidates shades to restrict the search to
     * @param colorCode  color in any supported notation
     * @return nearest color within the given shades or null when the code is not valid
     */
    public static OpenColorShade getNearest(Collection<Shade> candidates, String colorCode) {
        List<OpenColorShade> pool = new ArrayList<>();
        for (OpenColorPalette family : values()) {
            for (Shade shade : candidates) {
                OpenColorShade value = family.shade(shade);
                if (value != null) {
                    pool.add(value);
                }
            }
        }
        return ColorCode.getNearest(pool, colorCode);
    }

    /**
     * @return name in css notation for example gray
     */
    public String getValue() {
        return name().toLowerCase().replace("_", "-");
    }
}
