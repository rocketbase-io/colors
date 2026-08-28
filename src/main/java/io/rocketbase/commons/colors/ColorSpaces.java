package io.rocketbase.commons.colors;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * conversion helpers between the supported color spaces
 * <p>
 * the oklab math follows Björn Ottosson's reference implementation
 * (<a href="https://bottosson.github.io/posts/oklab/">bottosson.github.io/posts/oklab</a>)
 * which is also the basis of the css color 4 specification.
 */
final class ColorSpaces {

    private ColorSpaces() {
    }

    /**
     * converts a 0-1 channel to the 0-255 range used by {@link RgbColor}
     */
    static int to8Bit(double value) {
        return (int) Math.round(Math.min(1, Math.max(0, value)) * 255);
    }

    /**
     * srgb gamma transfer function - linear light to gamma encoded
     */
    static double linearToSrgb(double c) {
        return c <= 0.0031308d ? 12.92d * c : 1.055d * Math.pow(c, 1 / 2.4d) - 0.055d;
    }

    /**
     * srgb gamma transfer function - gamma encoded to linear light
     */
    static double srgbToLinear(double c) {
        return c <= 0.04045d ? c / 12.92d : Math.pow((c + 0.055d) / 1.055d, 2.4d);
    }

    /**
     * oklab to linear srgb - lms cube followed by the inverse m1 matrix
     */
    static double[] oklabToLinearSrgb(double lightness, double a, double b) {
        double l_ = lightness + 0.3963377774d * a + 0.2158037573d * b;
        double m_ = lightness - 0.1055613458d * a - 0.0638541728d * b;
        double s_ = lightness - 0.0894841775d * a - 1.2914855480d * b;

        double l = l_ * l_ * l_;
        double m = m_ * m_ * m_;
        double s = s_ * s_ * s_;

        return new double[]{
                +4.0767416621d * l - 3.3077115913d * m + 0.2309699292d * s,
                -1.2684380046d * l + 2.6097574011d * m - 0.3413193965d * s,
                -0.0041960863d * l - 0.7034186147d * m + 1.7076147010d * s
        };
    }

    /**
     * linear srgb to oklab
     */
    static double[] linearSrgbToOklab(double r, double g, double b) {
        double l = 0.4122214708d * r + 0.5363325363d * g + 0.0514459929d * b;
        double m = 0.2119034982d * r + 0.6806995451d * g + 0.1073969566d * b;
        double s = 0.0883024619d * r + 0.2817188376d * g + 0.6299787005d * b;

        double l_ = Math.cbrt(l);
        double m_ = Math.cbrt(m);
        double s_ = Math.cbrt(s);

        return new double[]{
                0.2104542553d * l_ + 0.7936177850d * m_ - 0.0040720468d * s_,
                1.9779984951d * l_ - 2.4285922050d * m_ + 0.4505937099d * s_,
                0.0259040371d * l_ + 0.7827717662d * m_ - 0.8086757660d * s_
        };
    }

    /**
     * formats a double without trailing zeros so 50.0 becomes 50
     */
    static String trim(double value) {
        BigDecimal rounded = BigDecimal.valueOf(value).setScale(3, RoundingMode.HALF_UP).stripTrailingZeros();
        return rounded.toPlainString();
    }
}
