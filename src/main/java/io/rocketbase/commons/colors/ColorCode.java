package io.rocketbase.commons.colors;

import java.util.Collection;
import java.util.Comparator;

/**
 * a named color that knows its hex code
 * <p>
 * implemented by all palette enums so that helpers like {@link #getNearest(Collection, String)}
 * work across palettes. only {@link #getHexCode()} has to be provided.
 */
public interface ColorCode {

    /**
     * @return hexCode of color without leading # for example ffffff
     */
    String getHexCode();

    /**
     * @return hexCode with leading # for example #ffffff
     */
    default String getHexCodeWithLeadingHash() {
        return "#" + getHexCode();
    }

    /**
     * @return color as rgb representation
     */
    default RgbColor getRgbColor() {
        return RgbColor.hex2rgb(getHexCode());
    }

    /**
     * @return color as hsl representation
     */
    default HslColor getHslColor() {
        return getRgbColor().toHsl();
    }

    /**
     * @return color as oklch representation
     */
    default OklchColor getOklchColor() {
        return getRgbColor().toOklch();
    }

    /**
     * primar algorithm to calculate text color for background-color
     *
     * @return true text should be black - otherwise white
     */
    default boolean isBlackContrastingColor() {
        return getRgbColor().isBlackContrastingColor();
    }

    /**
     * different algorithm to calculate text color for background-color
     *
     * @return true text should be black - otherwise white
     */
    default boolean isBlackContrastingColor2() {
        return getRgbColor().isBlackContrastingColor2();
    }

    /**
     * finds the perceptually closest color of the given candidates
     *
     * @param candidates colors to choose from
     * @param colorCode  color in any supported notation - rgb, hsl, oklch or hex
     * @param <T>        type of the palette
     * @return nearest color or null when the given code or the candidates are not valid
     */
    static <T extends ColorCode> T getNearest(Collection<T> candidates, String colorCode) {
        RgbColor given = RgbColor.readColor(colorCode);
        if (given == null || candidates == null || candidates.isEmpty()) {
            return null;
        }
        OklabColor reference = given.toOklab();
        return candidates.stream()
                .min(Comparator.comparingDouble(c -> reference.distance(c.getRgbColor().toOklab())))
                .orElse(null);
    }
}
