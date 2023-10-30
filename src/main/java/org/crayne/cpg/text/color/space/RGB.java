package org.crayne.cpg.text.color;

import org.crayne.cpg.text.util.BoundedVec3;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class RGB extends BoundedVec3<Integer> {

    public RGB(final int r, final int g, final int b) {
        super(r, g, b);
    }

    public RGB(final float r, final float g, final float b) {
        this((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    @NotNull
    public static RGB ofHSV(@NotNull final HSV hsv) {
        return hsv.toRGB();
    }

    @NotNull
    public static RGB ofHSV(final float h, final float s, final float v) {
        return ofHSV(HSV.of(h, s, v));
    }

    @NotNull
    public static RGB of(final int r, final int g, final int b) {
        return new RGB(r, g, b);
    }

    @NotNull
    public static RGB of(final float r, final float g, final float b) {
        return new RGB(r, g, b);
    }

    @NotNull
    public static RGB clampInt(final int r, final int g, final int b) {
        return new RGB(
                clampIntegerComponent(r, 0, 255),
                clampIntegerComponent(g, 0, 255),
                clampIntegerComponent(b, 0, 255)
        );
    }

    @NotNull
    public static RGB clampFloat(final float r, final float g, final float b) {
        return new RGB(
                clampFloatComponent(r, 0.0f, 1.0f),
                clampFloatComponent(g, 0.0f, 1.0f),
                clampFloatComponent(b, 0.0f, 1.0f)
        );
    }

    public void checkBounds(@NotNull final Integer x, @NotNull final Integer y, @NotNull final Integer z) {
        checkIntegerBounds(x, y, z, 0, 255);
    }

    private static void checkIntegerBounds(final int r, final int g, final int b, final int min, final int max) {
        checkIntegerComponentBounds(r, "Red",   min, max);
        checkIntegerComponentBounds(g, "Green", min, max);
        checkIntegerComponentBounds(b, "Blue",  min, max);
    }

    public int r() {
        return x();
    }

    public int g() {
        return y();
    }

    public int b() {
        return z();
    }

    @NotNull
    public HSV toHSV() {
        final float[] hsv = Color.RGBtoHSB(r(), g(), b(), null);
        return new HSV(hsv[0] * 360f, hsv[1], hsv[2]);
    }

    @NotNull
    public String toString() {
        return "rgb(" + r() +
                 ", " + g() +
                 ", " + b() +
                 ')';
    }

}
