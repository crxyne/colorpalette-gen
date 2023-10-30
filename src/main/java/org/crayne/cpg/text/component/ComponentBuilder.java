package org.crayne.cpg.text.component;

import org.crayne.cpg.text.color.Color;
import org.crayne.cpg.text.color.ansi.AnsiColor;
import org.crayne.cpg.text.color.ansi.AnsiColorBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponentBuilder {

    @NotNull
    private AnsiColorBuilder ansiColorBuilder;

    @Nullable
    private String text;

    @NotNull
    private final List<ComponentPart> parts;

    public ComponentBuilder() {
        ansiColorBuilder = new AnsiColorBuilder();
        ansiColorBuilder.reset(true);
        parts = new ArrayList<>();
    }

    protected ComponentBuilder(@NotNull final List<ComponentPart> parts) {
        this();
        this.parts.addAll(parts);
    }

    public void clearAll() {
        clearCurrent();
        parts.clear();
    }

    public void clearCurrent() {
        ansiColorBuilder = new AnsiColorBuilder();
        ansiColorBuilder.reset(true);
        text = null;
    }

    @NotNull
    public ComponentBuilder foreground(@Nullable final Color fg) {
        ansiColorBuilder.foreground(fg);
        ansiColorBuilder.reset(false);
        return this;
    }

    @NotNull
    public ComponentBuilder background(@Nullable final Color bg) {
        ansiColorBuilder.background(bg);
        ansiColorBuilder.reset(false);
        return this;
    }

    @NotNull
    public ComponentBuilder reset(final boolean b) {
        ansiColorBuilder.reset(b);
        return this;
    }

    @NotNull
    public ComponentBuilder bold(final boolean b) {
        ansiColorBuilder.bold(b);
        return this;
    }

    @NotNull
    public ComponentBuilder dim(final boolean b) {
        ansiColorBuilder.dim(b);
        return this;
    }

    @NotNull
    public ComponentBuilder italic(final boolean b) {
        ansiColorBuilder.italic(b);
        return this;
    }

    @NotNull
    public ComponentBuilder underline(final boolean b) {
        ansiColorBuilder.underline(b);
        return this;
    }

    @NotNull
    public ComponentBuilder blinking(final boolean b) {
        ansiColorBuilder.blinking(b);
        return this;
    }

    @NotNull
    public ComponentBuilder inverted(final boolean b) {
        ansiColorBuilder.inverted(b);
        return this;
    }

    @NotNull
    public ComponentBuilder hidden(final boolean b) {
        ansiColorBuilder.hidden(b);
        return this;
    }

    @NotNull
    public ComponentBuilder strikethrough(final boolean b) {
        ansiColorBuilder.strikethrough(b);
        return this;
    }

    @NotNull
    public Optional<Color> foreground() {
        return ansiColorBuilder.foreground();
    }

    @NotNull
    public Optional<Color> background() {
        return ansiColorBuilder.background();
    }

    @NotNull
    public AnsiColor color() {
        return ansiColorBuilder.build();
    }

    public boolean reset() {
        return ansiColorBuilder.reset();
    }

    public boolean bold() {
        return ansiColorBuilder.bold();
    }

    public boolean dim() {
        return ansiColorBuilder.dim();
    }

    public boolean italic() {
        return ansiColorBuilder.italic();
    }

    public boolean underline() {
        return ansiColorBuilder.underline();
    }

    public boolean blinking() {
        return ansiColorBuilder.blinking();
    }

    public boolean inverted() {
        return ansiColorBuilder.inverted();
    }

    public boolean hidden() {
        return ansiColorBuilder.hidden();
    }

    public boolean strikethrough() {
        return ansiColorBuilder.strikethrough();
    }

    @NotNull
    public static ComponentBuilder builder(@NotNull final String text) {
        return builder().text(text);
    }

    @NotNull
    public static ComponentBuilder builder() {
        return new ComponentBuilder();
    }

    @NotNull
    public ComponentBuilder text(@Nullable final String text) {
        this.text = text;
        return this;
    }

    @NotNull
    public Optional<String> text() {
        return Optional.ofNullable(text);
    }

    @NotNull
    public ComponentPart buildPart() {
        return new ComponentPart(color(), text);
    }

    @NotNull
    public ComponentBuilder next() {
        final ComponentPart part = buildPart();
        if (!part.invisible()) parts.add(part);
        clearCurrent();
        return this;
    }

    @NotNull
    public Component build() {
        next();
        final Component result = Component.text(parts);
        clearAll();
        return result;
    }

}
