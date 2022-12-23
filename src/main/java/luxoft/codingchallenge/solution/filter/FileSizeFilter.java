package luxoft.codingchallenge.solution.filter;

import luxoft.codingchallenge.filesystem.File;

import java.util.Objects;

public class FileSizeFilter implements FileFilter {

    private final long size;

    public FileSizeFilter(long size) {
        if (size < 0) {
            throw new IllegalArgumentException("size can not be negative.");
        }
        this.size = size;
    }

    @Override
    public boolean matches(File file) {
        Objects.requireNonNull(file, "file must not be null");
        return size == file.getSize();
    }
}

