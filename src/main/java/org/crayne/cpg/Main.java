package org.crayne.cpg;

import org.apache.commons.lang3.StringUtils;
import org.crayne.cpg.text.color.Color;
import org.crayne.cpg.text.component.Component;
import org.crayne.cpg.text.component.ComponentBuilder;
import org.crayne.cpg.text.component.TextUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    private Main() {}

    public static void main(@NotNull final String... args) {
        if (args.length == 0) {
            System.out.println("processing random base color");
            final Random random = new Random();
            displayColorProfile(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            return;
        }
        if (args.length != 1) {
            System.err.println("expected only one argument - color");
            displayUsage();
            System.exit(1);
            return;
        }
        final String argument = args[0];
        if (!argument.contains("(")) {
            System.err.println("invalid usage - could not parse color");
            displayUsage();
            System.exit(1);
            return;
        }
        final String mode = StringUtils.substringBefore(argument, "(");
        final String parameter = StringUtils.substringBetween(argument, "(", ")");
        processParameters(mode, parameter);
    }

    private static void processParameters(@NotNull final String mode, @NotNull final String parameter) {
        switch (mode) {
            case "hex" -> processHex(parameter);
            case "rgb" -> processRGB(parameter);
            case "hsv" -> processHSV(parameter);
            default -> {
                System.err.println("invalid usage - could not parse color");
                displayUsage();
                System.exit(1);
            }
        }
    }

    private static void processHex(@NotNull final String parameter) {
        System.out.println("processing base color from hex code");
        try {
            displayColorProfile(Color.hex(parameter));
        } catch (final Exception e) {
            System.err.println("could not parse hex color: " + e.getMessage());
            displayUsage();
            System.exit(1);
        }
    }

    private static void processRGB(@NotNull final String parameter) {
        System.out.println("processing base color from rgb");
        final String[] values = parameter.split(",");
        try {
            final List<Integer> valuesInts = Arrays.stream(values).map(Integer::parseInt).toList();
            if (valuesInts.size() != 3) throw new RuntimeException("3 color channels were expected, only " + valuesInts.size() + " were provided");

            displayColorProfile(Color.rgb(valuesInts.get(0), valuesInts.get(1), valuesInts.get(2)));
        } catch (final Exception e) {
            System.err.println("could not parse rgb color: " + e);
            displayUsage();
            System.exit(1);
        }
    }

    private static void processHSV(@NotNull final String parameter) {
        System.out.println("processing base color from hsv");
        final String[] values = parameter.split(",");
        try {
            final List<Float> valuesFloats = Arrays.stream(values).map(Float::parseFloat).toList();
            if (valuesFloats.size() != 3) throw new RuntimeException("3 color channels were expected, only " + valuesFloats.size() + " were provided");

            displayColorProfile(Color.hsv(valuesFloats.get(0), valuesFloats.get(1), valuesFloats.get(2)));
        } catch (final Exception e) {
            System.err.println("could not parse rgb color: " + e);
            displayUsage();
            System.exit(1);
        }
    }

    private static void displayUsage() {
        System.out.println("usage (rgb): cpg rgb(255,255,255)");
        System.out.println("usage (hsv): cpg hsv(360,1,1)");
        System.out.println("usage (hex): cpg hex(#FFFFFF)");
    }

    public static void displayColorProfile(@NotNull final Color baseColor) {
        System.out.println(Component.text(baseColor, baseColor.hex()).prepend("color palettes of base color "));
        displayColorPalette("complementary", baseColor.complementaryPalette());
        displayColorPalette("triadic", baseColor.triadicPalette());
        displayColorPalette("tetradic", baseColor.tetradicPalette());
        displayColorPalette("complementary2", baseColor.complementaryPalette2());
        displayColorPalette("triadic2", baseColor.triadicPalette2());
        displayColorPalette("tetradic2", baseColor.tetradicPalette2());
        displayColorPalette("complementary3", baseColor.complementaryPalette3(-0.75f, 0.75f, 0.0f, 0.125f));
        displayColorPalette("triadic3", baseColor.triadicPalette3(-0.75f, 0.75f, 0.0f, 0.125f));
        displayColorPalette("tetradic3", baseColor.tetradicPalette3(-0.75f, 0.75f, 0.0f, 0.125f));
        displayColorPalette("complementary4", baseColor.complementaryPalette3(0.0f, 0.75f, 0.0f, 0.125f));
        displayColorPalette("triadic4", baseColor.triadicPalette3(0.0f, 0.75f, 0.0f, 0.125f));
        displayColorPalette("tetradic4", baseColor.tetradicPalette3(0.0f, 0.75f, 0.0f, 0.125f));
        displayColorPalette("analogous1", baseColor.analogousPalette1(30.0f, 2));
        displayColorPalette("analogous2", baseColor.analogousPalette2(30.0f, 2, 2.5f, 1.0f));
        displayColorPalette("analogous3", baseColor.analogousPalette3(30.0f, 2));
        displayColorPalette("analogous4", baseColor.analogousPalette4(30.0f, 3, -4.0f, -1.0f, 0.0f, 0.25f));
        displayColorPalette("analogous5", baseColor.analogousPalette4(40.0f, 2, -4.0f, 1.0f, 0.5f, 0.25f));
        displayColorPalette("monochromatic1", baseColor.monochromaticPalette(0.1f, 3));
        displayColorPalette("monochromatic2", baseColor.monochromaticPalette(-0.2f, 3));
    }

    private static void displayColorPalette(@NotNull final String mode, @NotNull final Color[] colors) {
        final List<Color> palette = Arrays.stream(colors).toList();

        final ComponentBuilder builder = TextUtil.colorizeGradient(mode,
                        palette, true)
                .builder()
                .next()
                .text(": " + " ".repeat(14 - mode.length()));

        int totalRepeat = 0;
        for (final Color otherColor : palette) {
            final int r = (int) (14.0f / colors.length * 2.0f);
            builder.next()
                    .text(" ".repeat(r))
                    .background(otherColor);
            totalRepeat += r;
        }

        builder.next().text(" ".repeat(28 - totalRepeat)).reset(false).next().text(" ").next();

        for (int i = 0; i < palette.size(); i++) {
            final Color otherColor = palette.get(i);
            builder.next()
                    .text(otherColor.hex())
                    .foreground(otherColor)
                    .next();

            if (i < palette.size() - 1) builder.text(", ").next();
        }
        System.out.println(builder.build());
    }

}
