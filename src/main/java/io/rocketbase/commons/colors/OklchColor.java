package io.rocketbase.commons.colors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * color in the oklch color space - a perceptually uniform, polar form of oklab
 * <p>
 * unlike rgb or hsl the distance between two oklch colors matches how different
 * they look to the human eye, which makes it the better basis for nearest-color
 * lookups and for generating lighter or darker variants of a color.
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(of = {"l", "c", "h"})
public class OklchColor {

    protected static final Pattern OKLCH_PATTERN = Pattern.compile(
            "oklch\\(\\s*([0-9]*\\.?[0-9]+)(%?)\\s*[,\\s]\\s*([0-9]*\\.?[0-9]+)\\s*[,\\s]\\s*(-?[0-9]*\\.?[0-9]+|none)(?:deg)?\\s*\\)",
            CASE_INSENSITIVE);

    /**
     * lightness 0-1
     */
    private final double l;
    /**
     * chroma - 0 is achromatic, values above ~0.37 leave the srgb gamut
     */
    private final double c;
    /**
     * hue in degrees 0-360
     */
    private final double h;

    /**
     * reads a css oklch string like oklch(63.7% 0.237 25.331)
     * <p>
     * an achromatic hue may be written as the keyword none
     *
     * @param oklchCode string of color should match oklch(...
     * @return OklchColor or null when not valid format
     */
    public static OklchColor readOklch(String oklchCode) {
        if (oklchCode == null) {
            return null;
        }
        Matcher matcher = OKLCH_PATTERN.matcher(oklchCode.trim());
        if (!matcher.matches()) {
            return null;
        }
        try {
            double lightness = Double.parseDouble(matcher.group(1));
            if ("%".equals(matcher.group(2))) {
                lightness = lightness / 100d;
            }
            String hue = matcher.group(4);
            return new OklchColor(lightness, Double.parseDouble(matcher.group(3)),
                    "none".equalsIgnoreCase(hue) ? 0d : HslColor.normalizeHue(Double.parseDouble(hue)));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * @return the same color converted to oklab
     */
    public OklabColor toOklab() {
        double radians = Math.toRadians(h);
        return new OklabColor(l, c * Math.cos(radians), c * Math.sin(radians));
    }

    /**
     * converts to rgb - colors outside the srgb gamut are clipped per channel,
     * which is what browsers and the tailwind hex values do as well
     *
     * @return the same color converted to rgb
     */
    public RgbColor toRgb() {
        return toOklab().toRgb();
    }

    /**
     * @return true when the color can be shown in srgb without clipping
     */
    public boolean isInSrgbGamut() {
        OklabColor oklab = toOklab();
        double[] linear = ColorSpaces.oklabToLinearSrgb(oklab.getL(), oklab.getA(), oklab.getB());
        for (double channel : linear) {
            if (channel < -1e-6d || channel > 1 + 1e-6d) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return css representation for example oklch(63.7% 0.237 25.331)
     */
    public String getOklchCode() {
        return String.format(Locale.ROOT, "oklch(%s%% %s %s)",
                ColorSpaces.trim(l * 100), ColorSpaces.trim(c), ColorSpaces.trim(h));
    }

    /**
     * @param amount value between 0 and 1 to add to the lightness
     * @return brighter variant of the color
     */
    public OklchColor lighten(double amount) {
        return new OklchColor(Math.min(1, Math.max(0, l + amount)), c, h);
    }

    /**
     * @param amount value between 0 and 1 to remove from the lightness
     * @return darker variant of the color
     */
    public OklchColor darken(double amount) {
        return lighten(-amount);
    }

    @Override
    public String toString() {
        return getOklchCode();
    }
}
