package io.rocketbase.commons.colors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

/**
 * color in the oklab color space - the cartesian form of {@link OklchColor}
 * <p>
 * distances in this space are perceptually uniform, so it is the right place
 * to answer "which of these colors looks closest to mine".
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(of = {"l", "a", "b"})
public class OklabColor {

    /**
     * lightness 0-1
     */
    private final double l;
    /**
     * green to red axis
     */
    private final double a;
    /**
     * blue to yellow axis
     */
    private final double b;

    /**
     * converts to rgb - values outside the srgb gamut are clipped per channel
     *
     * @return the same color converted to rgb
     */
    public RgbColor toRgb() {
        double[] linear = ColorSpaces.oklabToLinearSrgb(l, a, b);
        return new RgbColor(
                ColorSpaces.to8Bit(ColorSpaces.linearToSrgb(linear[0])),
                ColorSpaces.to8Bit(ColorSpaces.linearToSrgb(linear[1])),
                ColorSpaces.to8Bit(ColorSpaces.linearToSrgb(linear[2])));
    }

    /**
     * @return the same color converted to the polar oklch form
     */
    public OklchColor toOklch() {
        double chroma = Math.sqrt(a * a + b * b);
        double hue = chroma < 1e-9d ? 0d : HslColor.normalizeHue(Math.toDegrees(Math.atan2(b, a)));
        return new OklchColor(l, chroma, hue);
    }

    /**
     * perceptual distance to another color - lower means more similar
     *
     * @param other color to compare with
     * @return euclidean distance within the oklab space
     */
    public double distance(OklabColor other) {
        double dl = l - other.l;
        double da = a - other.a;
        double db = b - other.b;
        return Math.sqrt(dl * dl + da * da + db * db);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "oklab(%s%% %s %s)",
                ColorSpaces.trim(l * 100), ColorSpaces.trim(a), ColorSpaces.trim(b));
    }
}
