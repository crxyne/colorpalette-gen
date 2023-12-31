package org.crayne.cpg.text.component;

import org.crayne.cpg.text.color.Color;
import org.crayne.cpg.text.color.ansi.AnsiColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ComponentPart {

    @Nullable
    private AnsiColor color;

    @NotNull
    private final String text;

    public ComponentPart(@Nullable final AnsiColor color, @Nullable final String text) {
        this.color = color;
        this.text = text == null ? "" : text;
    }

    @NotNull
    public static ComponentPart empty() {
        return new ComponentPart(null, null);
    }

    @NotNull
    public static ComponentPart of(@NotNull final String text) {
        return new ComponentPart(null, text);
    }

    @NotNull
    public static ComponentPart of(@Nullable final AnsiColor color, @NotNull final String text) {
        return new ComponentPart(color, text);
    }

    @NotNull
    public static ComponentPart of(@NotNull final Color color, @NotNull final String text) {
        return of(color, text, true);
    }

    @NotNull
    public static ComponentPart of(@NotNull final Color color, @NotNull final String text, final boolean foreground) {
        return new ComponentPart(foreground ? AnsiColor.foreground(color) : AnsiColor.background(color), text);
    }

    public boolean invisible() {
        return color().map(c -> !c.reset()).orElse(true) && text.isEmpty();
    }

    @NotNull
    public String text() {
        return text;
    }

    @NotNull
    public Optional<AnsiColor> color() {
        return Optional.ofNullable(color);
    }

    @NotNull
    public ComponentPart color(@Nullable final AnsiColor color) {
        this.color = color;
        return this;
    }

    @NotNull
    public ComponentPart createCopy() {
        return new ComponentPart(color, text);
    }

    @NotNull
    public String toString() {
        return color == null ? text : (color + text);
    }
}
