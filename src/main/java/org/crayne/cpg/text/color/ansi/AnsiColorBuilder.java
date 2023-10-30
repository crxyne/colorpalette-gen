package org.crayne.cpg.text.color.ansi;

import org.crayne.cpg.text.color.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AnsiColorBuilder {

    @Nullable
    private Color fg;

    @Nullable
    private Color bg;

    private final boolean @NotNull [] flags;

    public AnsiColorBuilder() {
        this.flags = new boolean[9];
    }

    @NotNull
    public AnsiColorBuilder foreground(@Nullable final Color fg) {
        this.fg = fg;
        return this;
    }

    @NotNull
    public AnsiColorBuilder background(@Nullable final Color bg) {
        this.bg = bg;
        return this;
    }

    @NotNull
    public AnsiColorBuilder reset(final boolean b) {
        flags[0] = b;
        return this;
    }

    @NotNull
    public AnsiColorBuilder bold(final boolean b) {
        flags[1] = b;
        return this;
    }

    @NotNull
    public AnsiColorBuilder dim(final boolean b) {
        flags[2] = b;
        return this;
    }

    @NotNull
    public AnsiColorBuilder italic(final boolean b) {
        flags[3] = b;
        return this;
    }

    @NotNull
    public AnsiColorBuilder underline(final boolean b) {
        flags[4] = b;
        return this;
    }

    @NotNull
    public AnsiColorBuilder blinking(final boolean b) {
        flags[5] = b;
        return this;
    }

    @NotNull
    public AnsiColorBuilder inverted(final boolean b) {
        flags[6] = b;
        return this;
    }

    @NotNull
    public AnsiColorBuilder hidden(final boolean b) {
        flags[7] = b;
        return this;
    }

    @NotNull
    public AnsiColorBuilder strikethrough(final boolean b) {
        flags[8] = b;
        return this;
    }

    @NotNull
    public Optional<Color> foreground() {
        return Optional.ofNullable(fg);
    }

    @NotNull
    public Optional<Color> background() {
        return Optional.ofNullable(bg);
    }

    public boolean reset() {
        return flags[0];
    }

    public boolean bold() {
        return flags[1];
    }

    public boolean dim() {
        return flags[2];
    }

    public boolean italic() {
        return flags[3];
    }

    public boolean underline() {
        return flags[4];
    }

    public boolean blinking() {
        return flags[5];
    }

    public boolean inverted() {
        return flags[6];
    }

    public boolean hidden() {
        return flags[7];
    }

    public boolean strikethrough() {
        return flags[8];
    }

    @NotNull
    public AnsiColor build() {
        return new AnsiColor(fg, bg, flags);
    }

}
