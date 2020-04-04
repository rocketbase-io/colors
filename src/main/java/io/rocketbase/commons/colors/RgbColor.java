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
        String colour = new String(hexCode);
        if (colour.startsWith("#")) {
            colour = colour.substring(1);
        }

        int r = 0, g = 0, b = 0;

        if (colour.length() == 6) {
            r = Integer.valueOf(colour.substring(0, 1), 16) * 17;
            g = Integer.valueOf(colour.substring(2, 3), 16) * 17;
            b = Integer.valueOf(colour.substring(4, 5), 16) * 17;
        } else if (colour.length() == 3) {
            r = Integer.valueOf(colour.substring(0, 1) + colour.substring(0, 1), 16);
            g = Integer.valueOf(colour.substring(1, 2) + colour.substring(1, 2), 16);
            b = Integer.valueOf(colour.substring(2, 3) + colour.substring(2, 3), 16);
        } else {
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
     * @return hexCode of color without leading # for example ffffff
     */
    public String getHexCode() {
        String rHex = Integer.toHexString(r), gHex = Integer.toHexString(g), bHex = Integer.toHexString(b);
        if (rHex.length() < 2) {
            rHex += rHex;
        }
        if (gHex.length() < 2) {
            gHex += gHex;
        }
        if (bHex.length() < 2) {
            bHex += bHex;
        }
        return rHex + gHex + bHex;
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
        // double a = 1 - ((0.00299 * (double) r) + (0.00587 * (double) g) + (0.00114 * (double) b));
        // return a < 0.5 ? true : false;
    }

}
