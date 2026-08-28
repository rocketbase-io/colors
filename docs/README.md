# docs

Astro + Starlight documentation for `io.rocketbase.commons:colors`, deployed to
<https://colors.rocketbase.io> via `.github/workflows/docs.yml`.

```
pnpm install
pnpm dev      # localhost:4321
pnpm build    # → dist/
```

## Palette data

The swatch pages read from `src/data/`:

| File | Source |
| ----------------- | ------------------------------------------------------------------------ |
| `tailwind.js` | `--color-<family>-<shade>` lines of the official `tailwindcss@4.3.3` `theme.css`, plus the four extra neutrals (mauve, olive, mist, taupe) taken from `TailwindColor.java` |
| `open-color.js` | the [open-color](https://github.com/yeun/open-color) `open-color.json` |
| `rocketbase.js` | the enum constants of `src/main/java/io/rocketbase/commons/colors/ColorPalette.java` |

Each entry carries the hex code, the `black` / `black2` verdicts of
`isBlackContrastingColor()` and `isBlackContrastingColor2()`, and for Tailwind the oklch source
value. The oklch→hex conversion and both contrast algorithms are reimplementations of
`ColorSpaces.java` and `RgbColor.java` — the 242 colors that exist upstream were diffed against
Tailwind's published hex values and match exactly.

Regenerating the data means porting those three files again; there is no checked-in generator, since
the palettes only change when the library does.
