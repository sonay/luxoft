package luxoft.codingchallenge.solution.runner;

import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;
import luxoft.codingchallenge.filesystem.Permissions;

import java.util.Locale;

public class PermissionsValueConverter implements ValueConverter<Permissions> {

    @Override
    public Permissions convert(String value) {
        try {
            return Permissions.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new ValueConversionException("Illegal permission value.");
        }
    }

    @Override
    public Class<? extends Permissions> valueType() {
        return Permissions.class;
    }

    @Override
    public String valuePattern() {
        return "permission";
    }

}
