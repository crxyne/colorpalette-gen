package org.crayne.cpg.text.util;

import org.jetbrains.annotations.NotNull;

public class Vec3<T> {

    @NotNull
    private final T x, y, z;

    public Vec3(@NotNull final T x, @NotNull final T y, @NotNull final T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @NotNull
    public T x() {
        return x;
    }

    @NotNull
    public T y() {
        return y;
    }

    @NotNull
    public T z() {
        return z;
    }

}
