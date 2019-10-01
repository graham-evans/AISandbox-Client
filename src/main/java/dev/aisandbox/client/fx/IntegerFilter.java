package dev.aisandbox.client.fx;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * <p>IntegerFilter class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
public class IntegerFilter implements UnaryOperator<TextFormatter.Change> {
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d*");

    /**
     * {@inheritDoc}
     */
    @Override
    public TextFormatter.Change apply(TextFormatter.Change aT) {
        return DIGIT_PATTERN.matcher(aT.getText()).matches() ? aT : null;
    }
}
