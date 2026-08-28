package io.rocketbase.commons.colors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * one color of the open color palette - a family combined with a shade
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(of = {"family", "shade"})
public class OpenColorShade implements ColorCode {

    private final OpenColorPalette family;
    private final Shade shade;
    private final String hexCode;

    /**
     * @return name as used by open color for example blue-5
     */
    public String getValue() {
        return family.getValue() + "-" + shade.getOpenColorStep();
    }

    @Override
    public String toString() {
        return getValue() + " " + getHexCodeWithLeadingHash();
    }
}
