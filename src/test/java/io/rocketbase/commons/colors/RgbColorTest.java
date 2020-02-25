package io.rocketbase.commons.colors;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class RgbColorTest {

    @Test
    public void hex2rgb() {
        // given
        String inputOne = "#fff";
        String inputTwo = "#ffffff";
        String inputThree = "fff";
        String inputFour = "a";
        // when
        RgbColor colorOne = RgbColor.hex2rgb(inputOne);
        RgbColor colorTwo = RgbColor.hex2rgb(inputTwo);
        RgbColor colorThree = RgbColor.hex2rgb(inputThree);
        RgbColor colorFour = RgbColor.hex2rgb(inputFour);
        // then
        RgbColor color = new RgbColor(255, 255, 255);

        assertThat(colorOne, equalTo(color));
        assertThat(colorTwo, equalTo(color));
        assertThat(colorThree, equalTo(color));
        assertThat(colorFour, nullValue());
    }

    @Test
    public void readRgb() {
        // given
        String inputOne = "rgb(255,255, 255)";
        String inputTwo = "RGB(255,255,255)";
        String inputThree = "fff";
        // when
        RgbColor colorOne = RgbColor.readRgb(inputOne);
        RgbColor colorTwo = RgbColor.readRgb(inputTwo);
        RgbColor colorThree = RgbColor.readRgb(inputThree);
        // then
        RgbColor color = new RgbColor(255, 255, 255);

        assertThat(colorOne, equalTo(color));
        assertThat(colorTwo, equalTo(color));
        assertThat(colorThree, nullValue());
    }

    @Test
    public void isTextContrastBlack() {
        // given
        ColorPalette black = ColorPalette.BLACK;
        ColorPalette white = ColorPalette.WHITE;
        // when
        boolean blackContrastBlack = black.isBlackContrastingColor();
        boolean whiteContrastBlack = white.isBlackContrastingColor();
        // then
        assertThat(blackContrastBlack, equalTo(false));
        assertThat(whiteContrastBlack, equalTo(true));

    }

    @Test
    public void getHexCode() {
        // given
        RgbColor white = new RgbColor(255, 255, 255);
        RgbColor black = new RgbColor(0, 0, 0);
        // when
        String whiteHex = white.getHexCode();
        String blackHex = black.getHexCode();
        // then
        assertThat(whiteHex, equalTo("ffffff"));
        assertThat(blackHex, equalTo("000000"));
    }
}