# colors

[![Build Status](https://travis-ci.com/rocketbase-io/colors.svg?branch=master)](https://travis-ci.com/rocketbase-io/colors)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.rocketbase.commons/colors/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.rocketbase.commons/colors)


![colors](assets/colors.png)


## usage

```java
RgbColor whiteRgb = RgbColor.readRgb("rgb( 255, 255, 255)");
RgbColor whiteHex = RgbColor.hex2rgb("#fff");
boolean textOnTopBlack = whiteHex.isBlackContrastingColor();
```
