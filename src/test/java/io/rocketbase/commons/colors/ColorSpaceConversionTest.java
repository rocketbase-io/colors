package io.rocketbase.commons.colors;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class ColorSpaceConversionTest {

    @Test
    public void oklchToRgbMatchesTailwindPublishedValues() {
        // given - the oklch source values tailwind v4 ships in theme.css
        // when / then - hex has to match what tailwind publishes
        assertThat(new OklchColor(0.637, 0.237, 25.331).toRgb().getHexCode(), equalTo("fb2c36"));
        assertThat(new OklchColor(0.792, 0.209, 151.711).toRgb().getHexCode(), equalTo("05df72"));
        assertThat(new OklchColor(0.623, 0.214, 259.815).toRgb().getHexCode(), equalTo("2b7fff"));
        assertThat(new OklchColor(0.556, 0, 0).toRgb().getHexCode(), equalTo("737373"));
        assertThat(new OklchColor(0.208, 0.042, 265.755).toRgb().getHexCode(), equalTo("0f172b"));
    }

    @Test
    public void oklchOutOfGamutIsClipped() {
        // given - a chroma far beyond what srgb can show
        OklchColor tooVivid = new OklchColor(0.7, 0.4, 150);
        // when / then
        assertThat(tooVivid.isInSrgbGamut(), is(false));
        assertThat(new OklchColor(0.556, 0, 0).isInSrgbGamut(), is(true));
    }

    @Test
    public void rgbToOklchAndBackIsStable() {
        // given
        RgbColor original = RgbColor.hex2rgb("#a54e3c");
        // when
        RgbColor roundTrip = original.toOklch().toRgb();
        // then
        assertThat(roundTrip, equalTo(original));
    }

    @Test
    public void rgbToHslAndBackIsStable() {
        // given
        RgbColor original = RgbColor.hex2rgb("#3266ad");
        // when
        HslColor hsl = original.toHsl();
        // then
        assertThat(hsl.toRgb(), equalTo(original));
    }

    @Test
    public void hslKnownValues() {
        // given - pure red
        RgbColor red = new RgbColor(255, 0, 0);
        // when
        HslColor hsl = red.toHsl();
        // then
        assertThat(hsl.getH(), closeTo(0, 0.001));
        assertThat(hsl.getS(), closeTo(1, 0.001));
        assertThat(hsl.getL(), closeTo(0.5, 0.001));
    }

    @Test
    public void readHslParsesBothCssNotations() {
        // given
        RgbColor expected = new RgbColor(255, 0, 0);
        // when / then
        assertThat(HslColor.readHsl("hsl(0, 100%, 50%)").toRgb(), equalTo(expected));
        assertThat(HslColor.readHsl("hsl(0 100% 50%)").toRgb(), equalTo(expected));
        assertThat(HslColor.readHsl("HSL(360deg, 100%, 50%)").toRgb(), equalTo(expected));
        assertThat(HslColor.readHsl("not-a-color"), nullValue());
        assertThat(HslColor.readHsl(null), nullValue());
    }

    @Test
    public void readOklchParsesPercentAndNoneHue() {
        // given / when
        OklchColor withPercent = OklchColor.readOklch("oklch(63.7% 0.237 25.331)");
        OklchColor noneHue = OklchColor.readOklch("oklch(55.6% 0 none)");
        // then
        assertThat(withPercent.toRgb().getHexCode(), equalTo("fb2c36"));
        assertThat(noneHue.toRgb().getHexCode(), equalTo("737373"));
        assertThat(OklchColor.readOklch("oklch(nonsense)"), nullValue());
        assertThat(OklchColor.readOklch(null), nullValue());
    }

    @Test
    public void readColorUnderstandsEveryNotation() {
        // given
        RgbColor expected = new RgbColor(251, 44, 54);
        // when / then
        assertThat(RgbColor.readColor("#fb2c36"), equalTo(expected));
        assertThat(RgbColor.readColor("rgb(251, 44, 54)"), equalTo(expected));
        assertThat(RgbColor.readColor("oklch(63.7% 0.237 25.331)"), equalTo(expected));
        assertThat(RgbColor.readColor("  #fb2c36  "), equalTo(expected));
        assertThat(RgbColor.readColor("nope"), nullValue());
    }

    @Test
    public void distanceIsZeroForSameColorAndGrowsWithDifference() {
        // given
        RgbColor red = RgbColor.hex2rgb("#ff0000");
        RgbColor alsoRed = RgbColor.hex2rgb("#fe0000");
        RgbColor blue = RgbColor.hex2rgb("#0000ff");
        // when / then
        assertThat(red.distance(red), closeTo(0, 1e-9));
        assertThat(red.distance(alsoRed) < red.distance(blue), is(true));
    }

    @Test
    public void hslLightenAndDarken() {
        // given
        HslColor mid = new HslColor(210, 0.5, 0.5);
        // when / then
        assertThat(mid.lighten(0.2).getL(), closeTo(0.7, 1e-9));
        assertThat(mid.darken(0.2).getL(), closeTo(0.3, 1e-9));
        assertThat(mid.lighten(5).getL(), closeTo(1, 1e-9));
        assertThat(mid.darken(5).getL(), closeTo(0, 1e-9));
        assertThat(mid.desaturate(5).getS(), closeTo(0, 1e-9));
    }
}
