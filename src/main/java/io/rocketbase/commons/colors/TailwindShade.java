package io.rocketbase.commons.colors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * one color of the tailwind palette - a family combined with a shade
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(of = {"family", "shade"})
public class TailwindShade implements ColorCode {

    private final TailwindPalette family;
    private final Shade shade;
    private final OklchColor oklchColor;

    /**
     * @return hexCode of color without leading # for example fb2c36
     */
    @Override
    public String getHexCode() {
        return oklchColor.toRgb().getHexCode();
    }

    /**
     * @return the oklch source value tailwind defines this color with
     */
    @Override
    public OklchColor getOklchColor() {
        return oklchColor;
    }

    /**
     * @return name as used in tailwind classes for example red-500
     */
    public String getValue() {
        return family.getValue() + "-" + shade.getTailwindStep();
    }

    @Override
    public String toString() {
        return getValue() + " " + getHexCodeWithLeadingHash();
    }
}
