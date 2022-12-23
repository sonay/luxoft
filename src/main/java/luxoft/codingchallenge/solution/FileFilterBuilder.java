package luxoft.codingchallenge.solution;

import luxoft.codingchallenge.filesystem.Permissions;
import luxoft.codingchallenge.solution.filter.*;
import luxoft.codingchallenge.solution.runner.CommandLineOptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class FileFilterBuilder {

    private final CommandLineOptions options;

    public FileFilterBuilder(CommandLineOptions options) {
        this.options = Objects.requireNonNull(options, "Options must not be null.");
    }

    public FileFilter build() {
        var filters = new ArrayList<FileFilter>(4);

        Long size = options.size();
        if (size != null) {
            filters.add(new FileSizeFilter(size));
        }

        Permissions permission = options.permission();
        if (permission != null) {
            filters.add(new FilePermissionFilter(permission));
        }

        LocalDateTime time = options.createdTime();
        if (time != null) {
            filters.add(new FileCreatedTimeFilter(time));
        }

        String name = options.name();
        if (name != null) {
            filters.add(new FileNameFilter(name));
        }

        return new CompositeFileFilter(filters);
    }

}

