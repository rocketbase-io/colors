package io.rocketbase.commons.colors;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class ColorPaletteTest {

    @Test
    public void getRgbColorMatchesHexCode() {
        // given
        ColorPalette brick = ColorPalette.BRICK;
        // when
        RgbColor rgbColor = brick.getRgbColor();
        // then
        assertThat(rgbColor, equalTo(new RgbColor(165, 78, 60)));
        assertThat(rgbColor.getHexCode(), equalTo("a54e3c"));
    }

    @Test
    public void getNearestFindsExactMatch() {
        // given
        String hexCode = ColorPalette.EMERALD.getHexCode();
        // when
        ColorPalette nearest = ColorPalette.getNearest(hexCode);
        // then
        assertThat(nearest, equalTo(ColorPalette.EMERALD));
    }

    @Test
    public void getNearestInvalidInputReturnsNull() {
        // when
        ColorPalette nearest = ColorPalette.getNearest("no-color");
        // then
        assertThat(nearest, nullValue());
    }

    @Test
    public void getRandomValueSkipsExistingColors() {
        // given
        Set<ColorPalette> existing = new HashSet<>(Arrays.asList(ColorPalette.values()));
        existing.remove(ColorPalette.MINT);
        // when
        ColorPalette newValue = assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> ColorPalette.getRandomValue(existing));
        // then - MINT is the only one left, so it has to be returned
        assertThat(newValue, equalTo(ColorPalette.MINT));
    }

    @Test
    public void getRandomValueOnEmptyCollectionTerminates() {
        // when
        ColorPalette newValue = assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> ColorPalette.getRandomValue(Collections.emptySet()));
        // then
        assertThat(newValue, notNullValue());
    }

    @Test
    public void getRandomValueReturnsNullWhenNothingLeft() {
        // given
        Set<ColorPalette> all = new HashSet<>(Arrays.asList(ColorPalette.values()));
        // when
        ColorPalette newValue = ColorPalette.getRandomValue(all);
        // then
        assertThat(newValue, nullValue());
        assertThat(ColorPalette.getRandomValue(null), nullValue());
    }

    @Test
    public void getValueUsesDashedLowercase() {
        assertThat(ColorPalette.FRENCH_BLUE.getValue(), equalTo("french-blue"));
    }

    @Test
    public void everyPaletteEntryHasValidHexCode() {
        for (ColorPalette color : ColorPalette.values()) {
            RgbColor rgbColor = color.getRgbColor();
            assertThat("palette entry " + color.name() + " must parse", rgbColor, notNullValue());
            assertThat("palette entry " + color.name() + " must round-trip",
                    rgbColor.getHexCode(), equalTo(color.getHexCode()));
        }
    }
}
