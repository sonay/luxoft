package luxoft.codingchallenge.solution.filter;

import luxoft.codingchallenge.filesystem.File;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A FileFilter that matches when given file's createdTime is equal
 * or before the given time.
 * */
public class FileCreatedTimeFilter implements FileFilter {

    private final LocalDateTime creationTime;

    public FileCreatedTimeFilter(LocalDateTime creationTime) {
        this.creationTime = Objects.requireNonNull(creationTime);
    }

    @Override
    public boolean matches(File file) {
        Objects.requireNonNull(file, "file must not be null");
        return creationTime.equals(file.getTimeCreated())
                || creationTime.isBefore(file.getTimeCreated());
    }
}
