package org.crayne.cpg.text.component;

import org.crayne.cpg.text.color.Color;
import org.crayne.cpg.text.color.ansi.AnsiColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TextComponent {

    private final List<ComponentPart> parts;

    public TextComponent(@NotNull final ComponentPart... parts) {
        this.parts = new ArrayList<>(Arrays.stream(parts).toList());
    }

    public TextComponent(@NotNull final Collection<ComponentPart> parts) {
        this.parts = new ArrayList<>(parts);
    }

    @NotNull
    public static TextComponent text(@NotNull final AnsiColor color, @NotNull final String text) {
        return new TextComponent(ComponentPart.of(color, text));
    }

    @NotNull
    public static TextComponent text(@NotNull final Color color, @NotNull final String text) {
        return new TextComponent(ComponentPart.of(color, text));
    }

    @NotNull
    public static TextComponent text(@NotNull final ComponentPart... parts) {
        return new TextComponent(parts);
    }

    @NotNull
    public static TextComponent text(@NotNull final Collection<ComponentPart> parts) {
        return new TextComponent(parts);
    }

    @NotNull
    public static TextComponent empty() {
        return new TextComponent();
    }

    @NotNull
    public static TextComponent plain(@NotNull final String text) {
        return new TextComponent(ComponentPart.plain(text));
    }

    @NotNull
    public TextComponent prepend(@NotNull final ComponentPart part) {
        parts.add(0, part);
        return this;
    }

    @NotNull
    public TextComponent prepend(@NotNull final AnsiColor part) {
        parts.add(0, new ComponentPart(part, null));
        return this;
    }

    @NotNull
    public TextComponent prepend(@NotNull final String plain) {
        parts.add(0, ComponentPart.plain(plain));
        return this;
    }

    @NotNull
    public TextComponent prepend(@NotNull final ComponentBuilder part) {
        parts.add(0, part.build());
        return this;
    }

    @NotNull
    public TextComponent prepend(@NotNull final TextComponent comp) {
        parts.addAll(0, comp.parts);
        return this;
    }

    @NotNull
    public TextComponent append(@NotNull final ComponentPart part) {
        parts.add(part);
        return this;
    }

    @NotNull
    public TextComponent append(@NotNull final AnsiColor part) {
        parts.add(new ComponentPart(part, null));
        return this;
    }

    @NotNull
    public TextComponent append(@NotNull final String plain) {
        parts.add(ComponentPart.plain(plain));
        return this;
    }

    @NotNull
    public TextComponent append(@NotNull final ComponentBuilder part) {
        parts.add(part.build());
        return this;
    }

    @NotNull
    public TextComponent append(@NotNull final TextComponent comp) {
        parts.addAll(comp.parts);
        return this;
    }

    @NotNull
    public TextComponent replace(@NotNull final String find, @NotNull final String replace) {
        final List<ComponentPart> replaced = parts.stream().map(c -> new ComponentPart(c.color(), c.text().replace(find, replace))).toList();
        parts.clear();
        parts.addAll(replaced);
        return this;
    }

    @NotNull
    public TextComponent replaceAll(@NotNull final String regex, @NotNull final String replace) {
        final List<ComponentPart> replaced = parts.stream().map(c -> new ComponentPart(c.color(), c.text().replaceAll(regex, replace))).toList();
        parts.clear();
        parts.addAll(replaced);
        return this;
    }

    @NotNull
    public List<ComponentPart> parts() {
        return parts;
    }

    public boolean isEmpty() {
        return parts.isEmpty();
    }

    public boolean visible() {
        return !text().isEmpty() || !parts.stream().filter(c -> c.color() != null && c.color() != AnsiColor.RESET_ANSI_COLOR).toList().isEmpty();
    }

    @NotNull
    public String text() {
        return parts.stream().map(ComponentPart::text).collect(Collectors.joining());
    }

    @NotNull
    public String toString(final boolean autoResetColor) {
        return parts.stream().map(ComponentPart::toString).collect(Collectors.joining()) + (autoResetColor ? AnsiColor.RESET : "");
    }

    @NotNull
    public TextComponent createCopy() {
        return new TextComponent(parts.stream().map(ComponentPart::createCopy).toList());
    }

    @NotNull
    public String toString() {
        return toString(true);
    }

}
