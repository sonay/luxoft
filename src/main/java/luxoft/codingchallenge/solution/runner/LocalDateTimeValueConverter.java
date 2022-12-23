package luxoft.codingchallenge.solution.runner;

import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalDateTimeValueConverter implements ValueConverter<LocalDateTime> {

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm";

    private final DateTimeFormatter formatter;

    private final String pattern;

    public LocalDateTimeValueConverter() {
        this(DEFAULT_DATE_PATTERN);
    }

    public LocalDateTimeValueConverter(String pattern) {
        Objects.requireNonNull(pattern, "pattern must not be null");
        formatter = DateTimeFormatter.ofPattern(pattern);
        this.pattern = pattern;
    }

    @Override
    public LocalDateTime convert(String value) {
        try {
            return LocalDateTime.parse(value, formatter);
        } catch (DateTimeParseException ex) {
            throw new ValueConversionException("Must be in format:" + DEFAULT_DATE_PATTERN);
        }
    }

    @Override
    public Class<? extends LocalDateTime> valueType() {
        return LocalDateTime.class;
    }

    @Override
    public String valuePattern() {
        return pattern;
    }

}
