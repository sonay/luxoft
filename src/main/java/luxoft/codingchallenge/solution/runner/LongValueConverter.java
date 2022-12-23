package luxoft.codingchallenge.solution.runner;

import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;
import joptsimple.internal.Reflection;

/**
 * Converts option value from String to Long and makes sure the value exceeds given minimal threshold.
 */
public class LongValueConverter implements ValueConverter<Long> {
    private final static ValueConverter<Long> TO_LONG_CONVERTER = Reflection.findConverter(long.class);

    public final static LongValueConverter POSITIVE = new LongValueConverter(1L);
    public final static LongValueConverter NON_NEGATIVE = new LongValueConverter(0L);

    private final long minValue;

    public LongValueConverter(long minValue) {
        this.minValue = minValue;
    }

    @Override
    public Long convert(String value) {
        Long newValue = TO_LONG_CONVERTER.convert(value);
        if (newValue == null) {
            // should not get here
            throw new ValueConversionException("value should not be null");
        }

        if (newValue < minValue) {
            String message = "The given value " + value + " should be ";
            if (minValue == 0) {
                message += "non-negative";
            } else if (minValue == 1) {
                message += "positive";
            } else {
                message += "greater or equal than " + minValue;
            }
            throw new ValueConversionException(message);
        }
        return newValue;
    }

    @Override
    public Class<? extends Long> valueType() {
        return TO_LONG_CONVERTER.valueType();
    }

    @Override
    public String valuePattern() {
        return "int";
    }
}
