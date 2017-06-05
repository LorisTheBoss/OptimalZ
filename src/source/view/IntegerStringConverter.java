package source.view;

import javafx.util.StringConverter;

/**
 * Created by Jonas on 05.06.2017.
 */

public class IntegerStringConverter extends StringConverter<Number> {
    public IntegerStringConverter() {
    }

    @Override
    public String toString(Number object) {
        if (object.intValue() != object.doubleValue()) return "";
        return "" + (object.intValue());
    }

    @Override
    public Number fromString(String string) {
        Number val = Double.parseDouble(string);
        return val.intValue();
    }
}