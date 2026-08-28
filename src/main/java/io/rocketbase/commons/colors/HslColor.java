package io.rocketbase.commons.colors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * color in the hsl color space - hue in degrees, saturation and lightness in percent
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(of = {"h", "s", "l"})
public class HslColor {

    protected static final Pattern HSL_PATTERN = Pattern.compile(
            "hsl\\(\\s*(-?[0-9]*\\.?[0-9]+)(?:deg)?\\s*[,\\s]\\s*([0-9]*\\.?[0-9]+)%?\\s*[,\\s]\\s*([0-9]*\\.?[0-9]+)%?\\s*\\)",
            CASE_INSENSITIVE);

    /**
     * hue in degrees 0-360
     */
    private final double h;
    /**
     * saturation 0-1
     */
    private final double s;
    /**
     * lightness 0-1
     */
    private final double l;

    /**
     * reads a css hsl string like hsl(210, 50%, 40%) or hsl(210 50% 40%)
     *
     * @param hslCode string of color should match hsl(...
     * @return HslColor or null when not valid format
     */
    public static HslColor readHsl(String hslCode) {
        if (hslCode == null) {
            return null;
        }
        Matcher matcher = HSL_PATTERN.matcher(hslCode.trim());
        if (!matcher.matches()) {
            return null;
        }
        try {
            return new HslColor(normalizeHue(Double.parseDouble(matcher.group(1))),
                    Double.parseDouble(matcher.group(2)) / 100d,
                    Double.parseDouble(matcher.group(3)) / 100d);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected static double normalizeHue(double hue) {
        double result = hue % 360d;
        return result < 0 ? result + 360d : result;
    }

    /**
     * @return the same color converted to rgb
     */
    public RgbColor toRgb() {
        double c = (1 - Math.abs(2 * l - 1)) * s;
        double hPrime = h / 60d;
        double x = c * (1 - Math.abs((hPrime % 2) - 1));
        double r1 = 0, g1 = 0, b1 = 0;
        if (hPrime < 1) {
            r1 = c;
            g1 = x;
        } else if (hPrime < 2) {
            r1 = x;
            g1 = c;
        } else if (hPrime < 3) {
            g1 = c;
            b1 = x;
        } else if (hPrime < 4) {
            g1 = x;
            b1 = c;
        } else if (hPrime < 5) {
            r1 = x;
            b1 = c;
        } else {
            r1 = c;
            b1 = x;
        }
        double m = l - c / 2d;
        return new RgbColor(ColorSpaces.to8Bit(r1 + m), ColorSpaces.to8Bit(g1 + m), ColorSpaces.to8Bit(b1 + m));
    }

    /**
     * @return css representation for example hsl(210, 50%, 40%)
     */
    public String getHslCode() {
        return String.format(Locale.ROOT, "hsl(%s, %s%%, %s%%)",
                ColorSpaces.trim(h), ColorSpaces.trim(s * 100), ColorSpaces.trim(l * 100));
    }

    /**
     * @param amount value between 0 and 1 to add to the lightness
     * @return brighter variant of the color
     */
    public HslColor lighten(double amount) {
        return new HslColor(h, s, Math.min(1, Math.max(0, l + amount)));
    }

    /**
     * @param amount value between 0 and 1 to remove from the lightness
     * @return darker variant of the color
     */
    public HslColor darken(double amount) {
        return lighten(-amount);
    }

    /**
     * @param amount value between 0 and 1 to add to the saturation
     * @return more saturated variant of the color
     */
    public HslColor saturate(double amount) {
        return new HslColor(h, Math.min(1, Math.max(0, s + amount)), l);
    }

    /**
     * @param amount value between 0 and 1 to remove from the saturation
     * @return less saturated variant of the color
     */
    public HslColor desaturate(double amount) {
        return saturate(-amount);
    }

    @Override
    public String toString() {
        return getHslCode();
    }
}
