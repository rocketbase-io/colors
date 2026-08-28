package io.rocketbase.commons.colors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * the default color palette of tailwind css - 26 families with 11 shades each
 * <p>
 * the values track tailwind css 4.3.3. tailwind re-tuned its palette between v3 and
 * v4, so these are not the hex codes older references list - a future tailwind release
 * that changes them again would land here as a breaking change.
 * <p>
 * tailwind defines these colors in oklch, so that is what is stored here. the hex
 * code is derived on demand, clipping to the srgb gamut the same way browsers do.
 * <p>
 * <pre>
 * String hex = TailwindPalette.EMERALD.shade(600).getHexCode();
 * TailwindShade random = TailwindPalette.randomShade(Shade.mids());
 * </pre>
 * <p>
 * colors by tailwind labs, mit licensed - see https://tailwindcss.com/docs/colors
 */
public enum TailwindPalette {

    RED(
            0.971d, 0.013d, 17.38d,
            0.936d, 0.032d, 17.717d,
            0.885d, 0.062d, 18.334d,
            0.808d, 0.114d, 19.571d,
            0.704d, 0.191d, 22.216d,
            0.637d, 0.237d, 25.331d,
            0.577d, 0.245d, 27.325d,
            0.505d, 0.213d, 27.518d,
            0.444d, 0.177d, 26.899d,
            0.396d, 0.141d, 25.723d,
            0.258d, 0.092d, 26.042d),
    ORANGE(
            0.98d, 0.016d, 73.684d,
            0.954d, 0.038d, 75.164d,
            0.901d, 0.076d, 70.697d,
            0.837d, 0.128d, 66.29d,
            0.75d, 0.183d, 55.934d,
            0.705d, 0.213d, 47.604d,
            0.646d, 0.222d, 41.116d,
            0.553d, 0.195d, 38.402d,
            0.47d, 0.157d, 37.304d,
            0.408d, 0.123d, 38.172d,
            0.266d, 0.079d, 36.259d),
    AMBER(
            0.987d, 0.022d, 95.277d,
            0.962d, 0.059d, 95.617d,
            0.924d, 0.12d, 95.746d,
            0.879d, 0.169d, 91.605d,
            0.828d, 0.189d, 84.429d,
            0.769d, 0.188d, 70.08d,
            0.666d, 0.179d, 58.318d,
            0.555d, 0.163d, 48.998d,
            0.473d, 0.137d, 46.201d,
            0.414d, 0.112d, 45.904d,
            0.279d, 0.077d, 45.635d),
    YELLOW(
            0.987d, 0.026d, 102.212d,
            0.973d, 0.071d, 103.193d,
            0.945d, 0.129d, 101.54d,
            0.905d, 0.182d, 98.111d,
            0.852d, 0.199d, 91.936d,
            0.795d, 0.184d, 86.047d,
            0.681d, 0.162d, 75.834d,
            0.554d, 0.135d, 66.442d,
            0.476d, 0.114d, 61.907d,
            0.421d, 0.095d, 57.708d,
            0.286d, 0.066d, 53.813d),
    LIME(
            0.986d, 0.031d, 120.757d,
            0.967d, 0.067d, 122.328d,
            0.938d, 0.127d, 124.321d,
            0.897d, 0.196d, 126.665d,
            0.841d, 0.238d, 128.85d,
            0.768d, 0.233d, 130.85d,
            0.648d, 0.2d, 131.684d,
            0.532d, 0.157d, 131.589d,
            0.453d, 0.124d, 130.933d,
            0.405d, 0.101d, 131.063d,
            0.274d, 0.072d, 132.109d),
    GREEN(
            0.982d, 0.018d, 155.826d,
            0.962d, 0.044d, 156.743d,
            0.925d, 0.084d, 155.995d,
            0.871d, 0.15d, 154.449d,
            0.792d, 0.209d, 151.711d,
            0.723d, 0.219d, 149.579d,
            0.627d, 0.194d, 149.214d,
            0.527d, 0.154d, 150.069d,
            0.448d, 0.119d, 151.328d,
            0.393d, 0.095d, 152.535d,
            0.266d, 0.065d, 152.934d),
    EMERALD(
            0.979d, 0.021d, 166.113d,
            0.95d, 0.052d, 163.051d,
            0.905d, 0.093d, 164.15d,
            0.845d, 0.143d, 164.978d,
            0.765d, 0.177d, 163.223d,
            0.696d, 0.17d, 162.48d,
            0.596d, 0.145d, 163.225d,
            0.508d, 0.118d, 165.612d,
            0.432d, 0.095d, 166.913d,
            0.378d, 0.077d, 168.94d,
            0.262d, 0.051d, 172.552d),
    TEAL(
            0.984d, 0.014d, 180.72d,
            0.953d, 0.051d, 180.801d,
            0.91d, 0.096d, 180.426d,
            0.855d, 0.138d, 181.071d,
            0.777d, 0.152d, 181.912d,
            0.704d, 0.14d, 182.503d,
            0.6d, 0.118d, 184.704d,
            0.511d, 0.096d, 186.391d,
            0.437d, 0.078d, 188.216d,
            0.386d, 0.063d, 188.416d,
            0.277d, 0.046d, 192.524d),
    CYAN(
            0.984d, 0.019d, 200.873d,
            0.956d, 0.045d, 203.388d,
            0.917d, 0.08d, 205.041d,
            0.865d, 0.127d, 207.078d,
            0.789d, 0.154d, 211.53d,
            0.715d, 0.143d, 215.221d,
            0.609d, 0.126d, 221.723d,
            0.52d, 0.105d, 223.128d,
            0.45d, 0.085d, 224.283d,
            0.398d, 0.07d, 227.392d,
            0.302d, 0.056d, 229.695d),
    SKY(
            0.977d, 0.013d, 236.62d,
            0.951d, 0.026d, 236.824d,
            0.901d, 0.058d, 230.902d,
            0.828d, 0.111d, 230.318d,
            0.746d, 0.16d, 232.661d,
            0.685d, 0.169d, 237.323d,
            0.588d, 0.158d, 241.966d,
            0.5d, 0.134d, 242.749d,
            0.443d, 0.11d, 240.79d,
            0.391d, 0.09d, 240.876d,
            0.293d, 0.066d, 243.157d),
    BLUE(
            0.97d, 0.014d, 254.604d,
            0.932d, 0.032d, 255.585d,
            0.882d, 0.059d, 254.128d,
            0.809d, 0.105d, 251.813d,
            0.707d, 0.165d, 254.624d,
            0.623d, 0.214d, 259.815d,
            0.546d, 0.245d, 262.881d,
            0.488d, 0.243d, 264.376d,
            0.424d, 0.199d, 265.638d,
            0.379d, 0.146d, 265.522d,
            0.282d, 0.091d, 267.935d),
    INDIGO(
            0.962d, 0.018d, 272.314d,
            0.93d, 0.034d, 272.788d,
            0.87d, 0.065d, 274.039d,
            0.785d, 0.115d, 274.713d,
            0.673d, 0.182d, 276.935d,
            0.585d, 0.233d, 277.117d,
            0.511d, 0.262d, 276.966d,
            0.457d, 0.24d, 277.023d,
            0.398d, 0.195d, 277.366d,
            0.359d, 0.144d, 278.697d,
            0.257d, 0.09d, 281.288d),
    VIOLET(
            0.969d, 0.016d, 293.756d,
            0.943d, 0.029d, 294.588d,
            0.894d, 0.057d, 293.283d,
            0.811d, 0.111d, 293.571d,
            0.702d, 0.183d, 293.541d,
            0.606d, 0.25d, 292.717d,
            0.541d, 0.281d, 293.009d,
            0.491d, 0.27d, 292.581d,
            0.432d, 0.232d, 292.759d,
            0.38d, 0.189d, 293.745d,
            0.283d, 0.141d, 291.089d),
    PURPLE(
            0.977d, 0.014d, 308.299d,
            0.946d, 0.033d, 307.174d,
            0.902d, 0.063d, 306.703d,
            0.827d, 0.119d, 306.383d,
            0.714d, 0.203d, 305.504d,
            0.627d, 0.265d, 303.9d,
            0.558d, 0.288d, 302.321d,
            0.496d, 0.265d, 301.924d,
            0.438d, 0.218d, 303.724d,
            0.381d, 0.176d, 304.987d,
            0.291d, 0.149d, 302.717d),
    FUCHSIA(
            0.977d, 0.017d, 320.058d,
            0.952d, 0.037d, 318.852d,
            0.903d, 0.076d, 319.62d,
            0.833d, 0.145d, 321.434d,
            0.74d, 0.238d, 322.16d,
            0.667d, 0.295d, 322.15d,
            0.591d, 0.293d, 322.896d,
            0.518d, 0.253d, 323.949d,
            0.452d, 0.211d, 324.591d,
            0.401d, 0.17d, 325.612d,
            0.293d, 0.136d, 325.661d),
    PINK(
            0.971d, 0.014d, 343.198d,
            0.948d, 0.028d, 342.258d,
            0.899d, 0.061d, 343.231d,
            0.823d, 0.12d, 346.018d,
            0.718d, 0.202d, 349.761d,
            0.656d, 0.241d, 354.308d,
            0.592d, 0.249d, 0.584d,
            0.525d, 0.223d, 3.958d,
            0.459d, 0.187d, 3.815d,
            0.408d, 0.153d, 2.432d,
            0.284d, 0.109d, 3.907d),
    ROSE(
            0.969d, 0.015d, 12.422d,
            0.941d, 0.03d, 12.58d,
            0.892d, 0.058d, 10.001d,
            0.81d, 0.117d, 11.638d,
            0.712d, 0.194d, 13.428d,
            0.645d, 0.246d, 16.439d,
            0.586d, 0.253d, 17.585d,
            0.514d, 0.222d, 16.935d,
            0.455d, 0.188d, 13.697d,
            0.41d, 0.159d, 10.272d,
            0.271d, 0.105d, 12.094d),
    SLATE(
            0.984d, 0.003d, 247.858d,
            0.968d, 0.007d, 247.896d,
            0.929d, 0.013d, 255.508d,
            0.869d, 0.022d, 252.894d,
            0.704d, 0.04d, 256.788d,
            0.554d, 0.046d, 257.417d,
            0.446d, 0.043d, 257.281d,
            0.372d, 0.044d, 257.287d,
            0.279d, 0.041d, 260.031d,
            0.208d, 0.042d, 265.755d,
            0.129d, 0.042d, 264.695d),
    GRAY(
            0.985d, 0.002d, 247.839d,
            0.967d, 0.003d, 264.542d,
            0.928d, 0.006d, 264.531d,
            0.872d, 0.01d, 258.338d,
            0.707d, 0.022d, 261.325d,
            0.551d, 0.027d, 264.364d,
            0.446d, 0.03d, 256.802d,
            0.373d, 0.034d, 259.733d,
            0.278d, 0.033d, 256.848d,
            0.21d, 0.034d, 264.665d,
            0.13d, 0.028d, 261.692d),
    ZINC(
            0.985d, 0.0d, 0.0d,
            0.967d, 0.001d, 286.375d,
            0.92d, 0.004d, 286.32d,
            0.871d, 0.006d, 286.286d,
            0.705d, 0.015d, 286.067d,
            0.552d, 0.016d, 285.938d,
            0.442d, 0.017d, 285.786d,
            0.37d, 0.013d, 285.805d,
            0.274d, 0.006d, 286.033d,
            0.21d, 0.006d, 285.885d,
            0.141d, 0.005d, 285.823d),
    NEUTRAL(
            0.985d, 0.0d, 0.0d,
            0.97d, 0.0d, 0.0d,
            0.922d, 0.0d, 0.0d,
            0.87d, 0.0d, 0.0d,
            0.708d, 0.0d, 0.0d,
            0.556d, 0.0d, 0.0d,
            0.439d, 0.0d, 0.0d,
            0.371d, 0.0d, 0.0d,
            0.269d, 0.0d, 0.0d,
            0.205d, 0.0d, 0.0d,
            0.145d, 0.0d, 0.0d),
    STONE(
            0.985d, 0.001d, 106.423d,
            0.97d, 0.001d, 106.424d,
            0.923d, 0.003d, 48.717d,
            0.869d, 0.005d, 56.366d,
            0.709d, 0.01d, 56.259d,
            0.553d, 0.013d, 58.071d,
            0.444d, 0.011d, 73.639d,
            0.374d, 0.01d, 67.558d,
            0.268d, 0.007d, 34.298d,
            0.216d, 0.006d, 56.043d,
            0.147d, 0.004d, 49.25d),
    MAUVE(
            0.985d, 0.0d, 0.0d,
            0.96d, 0.003d, 325.6d,
            0.922d, 0.005d, 325.62d,
            0.865d, 0.012d, 325.68d,
            0.711d, 0.019d, 323.02d,
            0.542d, 0.034d, 322.5d,
            0.435d, 0.029d, 321.78d,
            0.364d, 0.029d, 323.89d,
            0.263d, 0.024d, 320.12d,
            0.212d, 0.019d, 322.12d,
            0.145d, 0.008d, 326.0d),
    OLIVE(
            0.988d, 0.003d, 106.5d,
            0.966d, 0.005d, 106.5d,
            0.93d, 0.007d, 106.5d,
            0.88d, 0.011d, 106.6d,
            0.737d, 0.021d, 106.9d,
            0.58d, 0.031d, 107.3d,
            0.466d, 0.025d, 107.3d,
            0.394d, 0.023d, 107.4d,
            0.286d, 0.016d, 107.4d,
            0.228d, 0.013d, 107.4d,
            0.153d, 0.006d, 107.1d),
    MIST(
            0.987d, 0.002d, 197.1d,
            0.963d, 0.002d, 197.1d,
            0.925d, 0.005d, 214.3d,
            0.872d, 0.007d, 219.6d,
            0.723d, 0.014d, 214.4d,
            0.56d, 0.021d, 213.5d,
            0.45d, 0.017d, 213.2d,
            0.378d, 0.015d, 216.0d,
            0.275d, 0.011d, 216.9d,
            0.218d, 0.008d, 223.9d,
            0.148d, 0.004d, 228.8d),
    TAUPE(
            0.986d, 0.002d, 67.8d,
            0.96d, 0.002d, 17.2d,
            0.922d, 0.005d, 34.3d,
            0.868d, 0.007d, 39.5d,
            0.714d, 0.014d, 41.2d,
            0.547d, 0.021d, 43.1d,
            0.438d, 0.017d, 39.3d,
            0.367d, 0.016d, 35.7d,
            0.268d, 0.011d, 36.5d,
            0.214d, 0.009d, 43.1d,
            0.147d, 0.004d, 49.3d);
    private final double[] oklchValues;
    private volatile Map<Shade, TailwindShade> shades;

    TailwindPalette(double... oklchValues) {
        this.oklchValues = oklchValues;
    }

    /**
     * @return all shades of this family keyed by their step
     */
    public Map<Shade, TailwindShade> getShades() {
        Map<Shade, TailwindShade> result = shades;
        if (result == null) {
            synchronized (this) {
                result = shades;
                if (result == null) {
                    Map<Shade, TailwindShade> map = new EnumMap<>(Shade.class);
                    Shade[] all = Shade.values();
                    for (int i = 0; i < all.length; i++) {
                        int offset = i * 3;
                        map.put(all[i], new TailwindShade(this, all[i],
                                new OklchColor(oklchValues[offset], oklchValues[offset + 1], oklchValues[offset + 2])));
                    }
                    result = Collections.unmodifiableMap(map);
                    shades = result;
                }
            }
        }
        return result;
    }

    /**
     * @param shade step of the scale
     * @return the color of this family at the given shade
     */
    public TailwindShade shade(Shade shade) {
        return getShades().get(shade);
    }

    /**
     * @param step tailwind notation - 50, 100, 200 ... 950
     * @return the color of this family at the given step or null when the step does not exist
     */
    public TailwindShade shade(int step) {
        return shade(Shade.ofTailwindStep(step));
    }

    /**
     * @return the 500 shade, the one that carries the family name in tailwind
     */
    public TailwindShade base() {
        return shade(Shade.S500);
    }

    /**
     * @return all shades of this family, from light to dark
     */
    public List<TailwindShade> allShades() {
        List<TailwindShade> result = new ArrayList<>();
        for (Shade shade : Shade.values()) {
            result.add(shade(shade));
        }
        return result;
    }

    /**
     * @param candidates shades to choose from - for example {@link Shade#mids()}
     * @return a random shade of this family or null when nothing was given
     */
    public TailwindShade random(List<Shade> candidates) {
        Shade shade = Shade.random(candidates);
        return shade == null ? null : shade(shade);
    }

    /**
     * @return every color of the palette - 26 families with 11 shades
     */
    public static List<TailwindShade> all() {
        List<TailwindShade> result = new ArrayList<>();
        for (TailwindPalette family : values()) {
            result.addAll(family.allShades());
        }
        return result;
    }

    /**
     * @return a random family
     */
    public static TailwindPalette randomFamily() {
        return values()[new Random().nextInt(values().length)];
    }

    /**
     * @param candidates shades to pick from - for example {@link Shade#mids()}
     * @return a random color of a random family within the given shades
     */
    public static TailwindShade randomShade(List<Shade> candidates) {
        return randomFamily().random(candidates);
    }

    /**
     * @return a random color of the whole palette
     */
    public static TailwindShade randomShade() {
        return randomShade(Arrays.asList(Shade.values()));
    }

    /**
     * finds the perceptually closest color of the palette
     *
     * @param colorCode color in any supported notation - rgb, hsl, oklch or hex
     * @return nearest color or null when the given code is not valid
     */
    public static TailwindShade getNearest(String colorCode) {
        return ColorCode.getNearest(all(), colorCode);
    }

    /**
     * @param candidates shades to restrict the search to
     * @param colorCode  color in any supported notation
     * @return nearest color within the given shades or null when the code is not valid
     */
    public static TailwindShade getNearest(Collection<Shade> candidates, String colorCode) {
        List<TailwindShade> pool = new ArrayList<>();
        for (TailwindPalette family : values()) {
            for (Shade shade : candidates) {
                pool.add(family.shade(shade));
            }
        }
        return ColorCode.getNearest(pool, colorCode);
    }

    /**
     * @return name in css notation for example blue-gray becomes bluegray
     */
    public String getValue() {
        return name().toLowerCase().replace("_", "-");
    }
}
