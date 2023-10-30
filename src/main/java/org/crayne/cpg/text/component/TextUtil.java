package org.crayne.cpg.text.component;

import org.crayne.cpg.text.color.Color;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TextUtil {

    private TextUtil() {}

    @NotNull
    public static Component colorizeGradient(@NotNull final String string, @NotNull final List<Color> colors, final boolean foreground) {
        if (colors.isEmpty() || string.isBlank()) return Component.text(string);
        if (colors.size() == 1) return colors.get(0).stylize(string, foreground);
        final int length = string.length();
        final int colorsAmount = colors.size();

        Component result = Component.text("");
        final int flagColors = colorsAmount - 1;
        final int individualStringLength = (length + (length % flagColors)) / flagColors;

        for (int i = 0; i < flagColors; i++) {
            final Color color = colors.get(i);
            final Color nextColor = colors.get(i + 1);

            final int end = i + 1 == flagColors ? length : Math.min(length, (i + 1) * individualStringLength);
            for (int j = i * individualStringLength; j < end; j++) {
                final double step = (double) (j - i * individualStringLength + (i == 0 ? 0 : 1)) / (double) (end  - i * individualStringLength);
                result = result.append(color.interpolate(step, nextColor).stylize(string.charAt(j), foreground));
            }
        }
        return result;
    }

}
