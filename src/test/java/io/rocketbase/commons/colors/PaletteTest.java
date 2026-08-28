package io.rocketbase.commons.colors;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class PaletteTest {

    @Test
    public void tailwindHasEveryFamilyAndShade() {
        assertThat(TailwindPalette.values().length, equalTo(26));
        assertThat(TailwindPalette.all(), hasSize(26 * 11));
        for (TailwindPalette family : TailwindPalette.values()) {
            assertThat(family.name(), family.allShades(), hasSize(11));
        }
    }

    @Test
    public void tailwindShadesMatchPublishedHexValues() {
        assertThat(TailwindPalette.RED.shade(500).getHexCode(), equalTo("fb2c36"));
        assertThat(TailwindPalette.GREEN.shade(400).getHexCode(), equalTo("05df72"));
        assertThat(TailwindPalette.NEUTRAL.shade(500).getHexCode(), equalTo("737373"));
        assertThat(TailwindPalette.YELLOW.shade(400).getHexCode(), equalTo("fdc700"));
        assertThat(TailwindPalette.SLATE.shade(950).getHexCode(), equalTo("020618"));
    }

    @Test
    public void tailwindKeepsOklchAsSourceValue() {
        // given - tailwind defines its colors in oklch, so that value has to survive
        OklchColor oklch = TailwindPalette.RED.shade(500).getOklchColor();
        // then
        assertThat(oklch.getL(), equalTo(0.637));
        assertThat(oklch.getC(), equalTo(0.237));
        assertThat(oklch.getH(), equalTo(25.331));
    }

    @Test
    public void tailwindNaming() {
        assertThat(TailwindPalette.EMERALD.shade(600).getValue(), equalTo("emerald-600"));
        assertThat(TailwindPalette.BLUE.base().getValue(), equalTo("blue-500"));
        assertThat(TailwindPalette.RED.shade(42), nullValue());
    }

    @Test
    public void openColorHasEveryFamilyAndShade() {
        assertThat(OpenColorPalette.values().length, equalTo(13));
        assertThat(OpenColorPalette.all(), hasSize(13 * 10));
        for (OpenColorPalette family : OpenColorPalette.values()) {
            assertThat(family.name(), family.allShades(), hasSize(10));
        }
    }

    @Test
    public void openColorShadesMatchPublishedHexValues() {
        assertThat(OpenColorPalette.BLUE.shade(5).getHexCode(), equalTo("339af0"));
        assertThat(OpenColorPalette.GRAY.shade(0).getHexCode(), equalTo("f8f9fa"));
        assertThat(OpenColorPalette.ORANGE.shade(9).getHexCode(), equalTo("d9480f"));
        assertThat(OpenColorPalette.BLUE.shade(5).getValue(), equalTo("blue-5"));
    }

    @Test
    public void openColorHasNoStep950() {
        // given - open color counts 0-9, so the tailwind-only 950 step stays empty
        assertThat(OpenColorPalette.BLUE.shade(Shade.S950), nullValue());
        assertThat(OpenColorPalette.BLUE.shade(10), nullValue());
    }

    @Test
    public void nearestIsFoundInEveryPalette() {
        // given - a color taken straight out of each palette has to find itself
        assertThat(TailwindPalette.getNearest("#fb2c36"), equalTo(TailwindPalette.RED.shade(500)));
        assertThat(OpenColorPalette.getNearest("#339af0"), equalTo(OpenColorPalette.BLUE.shade(5)));
        assertThat(ColorPalette.getNearest("#a54e3c"), equalTo(ColorPalette.BRICK));
    }

    @Test
    public void nearestAcceptsEveryNotation() {
        assertThat(TailwindPalette.getNearest("rgb(251, 44, 54)"), equalTo(TailwindPalette.RED.shade(500)));
        assertThat(TailwindPalette.getNearest("oklch(63.7% 0.237 25.331)"), equalTo(TailwindPalette.RED.shade(500)));
        assertThat(TailwindPalette.getNearest("garbage"), nullValue());
    }

    @Test
    public void nearestCanBeRestrictedToShades() {
        // given - only dark shades are allowed
        List<Shade> darks = Shade.darks();
        // when
        TailwindShade nearest = TailwindPalette.getNearest(darks, "#ffffff");
        // then - even for white the answer has to come from the dark range
        assertThat(darks.contains(nearest.getShade()), is(true));
    }

    @Test
    public void randomShadeStaysWithinTheGivenRange() {
        for (int i = 0; i < 50; i++) {
            TailwindShade shade = TailwindPalette.randomShade(Shade.mids());
            assertThat(Shade.mids().contains(shade.getShade()), is(true));

            OpenColorShade openColor = OpenColorPalette.randomShade(Shade.darks());
            assertThat(openColor, notNullValue());
            assertThat(Shade.darks().contains(openColor.getShade()), is(true));
        }
    }

    @Test
    public void randomShadeWithoutCandidatesIsNull() {
        assertThat(TailwindPalette.RED.random(null), nullValue());
        assertThat(OpenColorPalette.BLUE.random(Arrays.asList(Shade.S950)), nullValue());
    }

    @Test
    public void shadeRangesAreOrderedAndInclusive() {
        assertThat(Shade.range(Shade.S50, Shade.S200), equalTo(Arrays.asList(Shade.S50, Shade.S100, Shade.S200)));
        assertThat(Shade.range(Shade.S200, Shade.S50), equalTo(Arrays.asList(Shade.S50, Shade.S100, Shade.S200)));
        assertThat(Shade.lights(), hasSize(5));
        assertThat(Shade.mids(), hasSize(3));
        assertThat(Shade.darks(), hasSize(5));
        assertThat(Shade.range(null, Shade.S50), hasSize(0));
    }

    @Test
    public void shadeStepLookup() {
        assertThat(Shade.ofTailwindStep(500), equalTo(Shade.S500));
        assertThat(Shade.ofTailwindStep(999), nullValue());
        assertThat(Shade.ofOpenColorStep(0), equalTo(Shade.S50));
        assertThat(Shade.ofOpenColorStep(9), equalTo(Shade.S900));
        assertThat(Shade.ofOpenColorStep(10), nullValue());
        assertThat(Shade.S50.isLight(), is(true));
        assertThat(Shade.S950.isDark(), is(true));
    }

    @Test
    public void everyColorOfEveryPaletteIsParseable() {
        for (ColorCode color : TailwindPalette.all()) {
            assertThat(color.toString(), color.getRgbColor(), notNullValue());
            assertThat(color.getHexCode().length(), equalTo(6));
        }
        for (ColorCode color : OpenColorPalette.all()) {
            assertThat(color.toString(), color.getRgbColor(), notNullValue());
            assertThat(color.getHexCode().length(), equalTo(6));
        }
    }

    @Test
    public void interfaceDefaultsWorkAcrossPalettes() {
        // given - light colors want black text, dark ones want white
        ColorCode light = TailwindPalette.RED.shade(50);
        ColorCode dark = TailwindPalette.RED.shade(950);
        // then
        assertThat(light.isBlackContrastingColor2(), is(true));
        assertThat(dark.isBlackContrastingColor2(), is(false));
        assertThat(light.getHexCodeWithLeadingHash().startsWith("#"), is(true));
        assertThat(light.getHslColor(), notNullValue());
        assertThat(light.getOklchColor(), notNullValue());
    }

    @Test
    public void nearestAcrossPalettesUsesTheSharedHelper() {
        // given - candidates from two different palettes in one list
        List<ColorCode> mixed = Arrays.asList(ColorPalette.WHITE, TailwindPalette.RED.shade(500), OpenColorPalette.BLUE.shade(5));
        // when
        ColorCode nearest = ColorCode.getNearest(mixed, "#fb2c36");
        // then
        assertThat(nearest, equalTo(TailwindPalette.RED.shade(500)));
        assertThat(ColorCode.getNearest(mixed, "not-a-color"), nullValue());
    }
}
