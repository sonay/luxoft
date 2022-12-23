package luxoft.codingchallenge.solution.filter;

import luxoft.codingchallenge.filesystem.File;

import java.util.Objects;

/**
 * A FileFilter matches when given pattern is contained in file's name.
 * */
public class FileNameFilter implements FileFilter {

    private final String pattern;

    public FileNameFilter(String pattern) {
        this.pattern = Objects.requireNonNull(pattern);
    }

    @Override
    public boolean matches(File file) {
        Objects.requireNonNull(file, "file must not be null.");
        return file.getName() != null && file.getName().contains(pattern);
    }

}
