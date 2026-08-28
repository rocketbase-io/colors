package io.rocketbase.commons.colors;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

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
    public void hex2rgbSixDigitsReadsChannelPairs() {
        // given
        String input = "#123456";
        // when
        RgbColor color = RgbColor.hex2rgb(input);
        // then
        assertThat(color, equalTo(new RgbColor(18, 52, 86)));
    }

    @Test
    public void hex2rgbSixDigitsPaletteColor() {
        // given
        String input = ColorPalette.BRICK.getHexCode();
        // when
        RgbColor color = RgbColor.hex2rgb(input);
        // then
        assertThat(color, equalTo(new RgbColor(165, 78, 60)));
    }

    @Test
    public void hex2rgbIsCaseInsensitive() {
        // given
        String upper = "#AABBCC";
        String lower = "#aabbcc";
        // when
        RgbColor upperColor = RgbColor.hex2rgb(upper);
        RgbColor lowerColor = RgbColor.hex2rgb(lower);
        // then
        assertThat(upperColor, equalTo(new RgbColor(170, 187, 204)));
        assertThat(upperColor, equalTo(lowerColor));
    }

    @Test
    public void hex2rgbInvalidInputReturnsNull() {
        // given
        String notHex = "zzzzzz";
        String tooShort = "#12345";
        String empty = "";
        // when
        RgbColor notHexColor = RgbColor.hex2rgb(notHex);
        RgbColor tooShortColor = RgbColor.hex2rgb(tooShort);
        RgbColor emptyColor = RgbColor.hex2rgb(empty);
        RgbColor nullColor = RgbColor.hex2rgb(null);
        // then
        assertThat(notHexColor, nullValue());
        assertThat(tooShortColor, nullValue());
        assertThat(emptyColor, nullValue());
        assertThat(nullColor, nullValue());
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
    public void readRgbOrHex() {
        // given
        String rgb = "rgb(18, 52, 86)";
        String hex = "#123456";
        String garbage = "not-a-color";
        // when
        RgbColor fromRgb = RgbColor.readRgbOrHex(rgb);
        RgbColor fromHex = RgbColor.readRgbOrHex(hex);
        RgbColor fromGarbage = RgbColor.readRgbOrHex(garbage);
        // then
        RgbColor color = new RgbColor(18, 52, 86);

        assertThat(fromRgb, equalTo(color));
        assertThat(fromHex, equalTo(color));
        assertThat(fromGarbage, nullValue());
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

    @Test
    public void getHexCodePadsSingleDigitChannels() {
        // given
        RgbColor color = new RgbColor(5, 10, 15);
        // when
        String hexCode = color.getHexCode();
        // then
        assertThat(hexCode, equalTo("050a0f"));
        assertThat(color.getHexCodeWithLeadingHash(), equalTo("#050a0f"));
    }

    @Test
    public void hexCodeRoundTrip() {
        // given
        String hexCode = ColorPalette.BRICK.getHexCode();
        // when
        RgbColor color = RgbColor.hex2rgb(hexCode);
        // then
        assertThat(color.getHexCode(), equalTo(hexCode));
    }
}
