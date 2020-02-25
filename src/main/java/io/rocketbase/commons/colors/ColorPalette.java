package io.rocketbase.commons.colors;

import lombok.Getter;

import java.util.*;

import static io.rocketbase.commons.colors.RgbColor.hex2rgb;

/**
 * holds a list of colors<br>
 * provides a function to transform from hex-color to rgb-color<br>
 * furthermore it allows to find a nearest fitting color to given hex-code
 */
public enum ColorPalette {

    BRICK("a54e3c"),
    POMEGRANTE("881f30"),
    CABERNET("721432"),
    AUBERGINE("5b1946"),
    INDIGO("461c5d"),
    PLUM("5c145e"),
    ROYAL("97348a"),
    FRENCH_BLUE("3266ad"),
    TEAL("337b8d"),
    AQUAMARINE("22586f"),
    MARINE("18456b"),
    NAVY("0e2e57"),
    LILAC("ae9cc7"),
    ORCHID("985a9c"),
    COBALT("4099d4"),
    POOL("9cd6f5"),
    PERIWINKLE("a7b4d0"),
    DOVE("aec4d0"),
    DUCK_EGG("97babf"),
    TURQUOISE("6fbabf"),
    JADE("73aba0"),
    TIFFANY("a5d2c1"),
    MINT("c8e0c4"),
    SPRING("d3e2a3"),
    CHIVE("b8d26e"),
    TREE_TOP("759150"),
    BOTTLE("378159"),
    EMERALD("29634e"),
    LEMON("fef6a6"),
    SUNSHINE("fcf151"),
    TUMERIC("f9d549"),
    CLEMENTINE("f3b143"),
    PEACH("f7d19d"),
    SALMON("f0b79f"),
    CORAL("e89487"),
    WATERMELON("e3708e"),
    RASBERRY("ac2c69"),
    MAGENTA("db318a"),
    RED_BALLOON("db3656"),
    FIRE_ENGINE("db3732"),
    BLOOD_ORANGE("e06151"),
    PAPAYA("e88b5b"),
    ROSE("e6aab3"),
    CHERRY_BLOSSOM("f6d6dc"),
    BLUSH("f6d6d1"),
    FRENCH_VANILLA("f8f1de"),
    IVORY("fbf8da"),
    CLAY("e8d4b8"),
    SANDSTONE("c4b199"),
    SAGE("a5b4a3"),
    MOSS("a2a495"),
    OLIVE("7b7d6a"),
    SMOKE("63605c"),
    ESPRESSO("473937"),
    CHOCOLATE("684940"),
    CARAMEL("957d62"),
    BLACK("221f20"),
    CHARCOAL("58585a"),
    SLATE("949599"),
    SILVER_LINING("d1d2d4"),
    OYSTER("e3ddd4"),
    STONE("d2cfc9"),
    SMOG("928b88"),
    WHITE("ffffff");

    @Getter
    private String hexCode;

    ColorPalette(String hexCode) {
        this.hexCode = hexCode;
    }

    /**
     * find a nearest fitting @{@link ColorPalette} by given hexcode
     *
     * @param hexCode sting of color could start with # or not...
     * @return when no problem @{@link ColorPalette}
     */
    public static ColorPalette getNearest(String hexCode) {
        RgbColor given = hex2rgb(hexCode);
        if (given == null) {
            return null;
        }
        List<Double> differenceArray = new ArrayList<>();
        Arrays.asList(values())
                .forEach(color -> {
                    RgbColor c = color.getRgbColor();
                    differenceArray.add(Math.sqrt((given.getR() - c.getR()) * (given.getR() - c.getR())
                            + (given.getG() - c.getG()) * (given.getG() - c.getG())
                            + (given.getB() - c.getB()) * (given.getB() - c.getB())));
                });
        Double min = Collections.min(differenceArray);
        int index = differenceArray.indexOf(min);

        return values()[index];
    }

    public static ColorPalette getRandomValue() {
        return values()[new Random().nextInt(values().length)];
    }

    public static ColorPalette getRandomValue(Collection<ColorPalette> existingCollection) {
        ColorPalette newValue = null;
        if (existingCollection == null || existingCollection.size() >= values().length) {
            return null;
        }
        do {
            newValue = getRandomValue();
        } while (!existingCollection.contains(newValue));
        return newValue;
    }

    public RgbColor getRgbColor() {
        return hex2rgb(this.getHexCode());
    }

    public boolean isBlackContrastingColor() {
        return this.getRgbColor().isBlackContrastingColor();
    }

}
