package io.rocketbase.commons.colors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(of = {"r", "g", "b"})
public class RgbColor {

    protected static final Pattern RGB_PATTERN = Pattern.compile("rgb\\(\\ *([0-9]{1,3})\\ *,\\ *([0-9]{1,3})\\ *,\\ *([0-9]{1,3})\\ *\\)", CASE_INSENSITIVE);

    /**
     * red
     */
    private final int r;
    /**
     * green
     */
    private final int g;
    /**
     * blue
     */
    private final int b;

    /**
     * reads given hex string and returns a it's color representation by red, green, blue
     *
     * @param hexCode sting of color could start with # or not...
     * @return RgbColor or null when not valid format
     */
    public static RgbColor hex2rgb(String hexCode) {
        if (hexCode == null) {
            return null;
        }
        String colour = hexCode;
        if (colour.startsWith("#")) {
            colour = colour.substring(1);
        }

        int r, g, b;

        try {
            if (colour.length() == 6) {
                r = Integer.parseInt(colour.substring(0, 2), 16);
                g = Integer.parseInt(colour.substring(2, 4), 16);
                b = Integer.parseInt(colour.substring(4, 6), 16);
            } else if (colour.length() == 3) {
                r = Integer.parseInt(colour.substring(0, 1) + colour.substring(0, 1), 16);
                g = Integer.parseInt(colour.substring(1, 2) + colour.substring(1, 2), 16);
                b = Integer.parseInt(colour.substring(2, 3) + colour.substring(2, 3), 16);
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }

        return new RgbColor(r, g, b);
    }

    /**
     * reads given string in rgb(2,2,6) layout and returns a it's color representation by red, green, blue
     *
     * @param rgbCode sting of color should match rgb(...
     * @return RgbColor or null when not valid format
     */
    public static RgbColor readRgb(String rgbCode) {
        if (rgbCode == null) {
            return null;
        }
        Matcher matcher = RGB_PATTERN.matcher(rgbCode);
        if (!matcher.matches()) {
            return null;
        }
        return new RgbColor(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
    }

    /**
     * reads given string - tries at first rgb and afterwards hex
     *
     * @param rgbOrHex rgb or hex
     * @return RgbColor or null when not valid format
     */
    public static RgbColor readRgbOrHex(String rgbOrHex) {
        RgbColor result = readRgb(rgbOrHex);
        if (result != null) {
            return result;
        }
        return hex2rgb(rgbOrHex);
    }

    /**
     * reads any of the supported notations - rgb(...), hsl(...), oklch(...) or a hex code
     *
     * @param colorCode color in one of the supported notations
     * @return RgbColor or null when not valid format
     */
    public static RgbColor readColor(String colorCode) {
        if (colorCode == null) {
            return null;
        }
        String trimmed = colorCode.trim();
        HslColor hsl = HslColor.readHsl(trimmed);
        if (hsl != null) {
            return hsl.toRgb();
        }
        OklchColor oklch = OklchColor.readOklch(trimmed);
        if (oklch != null) {
            return oklch.toRgb();
        }
        return readRgbOrHex(trimmed);
    }

    /**
     * @return the same color converted to hsl
     */
    public HslColor toHsl() {
        double rd = r / 255d, gd = g / 255d, bd = b / 255d;
        double max = Math.max(rd, Math.max(gd, bd));
        double min = Math.min(rd, Math.min(gd, bd));
        double delta = max - min;
        double lightness = (max + min) / 2d;

        if (delta == 0) {
            return new HslColor(0, 0, lightness);
        }
        double saturation = delta / (1 - Math.abs(2 * lightness - 1));
        double hue;
        if (max == rd) {
            hue = 60 * (((gd - bd) / delta) % 6);
        } else if (max == gd) {
            hue = 60 * (((bd - rd) / delta) + 2);
        } else {
            hue = 60 * (((rd - gd) / delta) + 4);
        }
        return new HslColor(HslColor.normalizeHue(hue), saturation, lightness);
    }

    /**
     * @return the same color converted to oklab
     */
    public OklabColor toOklab() {
        double[] oklab = ColorSpaces.linearSrgbToOklab(
                ColorSpaces.srgbToLinear(r / 255d),
                ColorSpaces.srgbToLinear(g / 255d),
                ColorSpaces.srgbToLinear(b / 255d));
        return new OklabColor(oklab[0], oklab[1], oklab[2]);
    }

    /**
     * @return the same color converted to oklch
     */
    public OklchColor toOklch() {
        return toOklab().toOklch();
    }

    /**
     * perceptual distance to another color - lower means more similar
     *
     * @param other color to compare with
     * @return euclidean distance within the oklab space
     */
    public double distance(RgbColor other) {
        return toOklab().distance(other.toOklab());
    }

    /**
     * @return hexCode of color without leading # for example ffffff
     */
    public String getHexCode() {
        return String.format("%02x%02x%02x", r, g, b);
    }

    /**
     * @return hexCode of with leading # for example #ffffff
     */
    public String getHexCodeWithLeadingHash() {
        return "#" + getHexCode();
    }

    /**
     * could be used to decide color of text with given background color...
     *
     * @return true when text should be black or false when should be white
     */
    public boolean isBlackContrastingColor() {
        return (Integer.valueOf(getHexCode(), 16) > 0xffffff / 1.5) ? true : false;
    }

    /**
     * different algorithm to calculate text color for background-color
     *
     * @return true text should be black - otherweise white
     */
    public boolean isBlackContrastingColor2() {
        double rd = gammaCorrect(r);
        double gd = gammaCorrect(g);
        double bd = gammaCorrect(b);
        double luminance = 0.2126 * rd + 0.7152 * gd + 0.0722 * bd;
        if (luminance > 0.179) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return getHexCodeWithLeadingHash();
    }

    private double gammaCorrect(int value) {
        double c = value / 255.0;
        if (c <= 0.03928) {
            c = c / 12.92;
        } else {
            c = Math.pow(((c + 0.055) / 1.055), 2.4);
        }
        return c;
    }

}
