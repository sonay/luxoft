package luxoft.codingchallenge.solution;

import luxoft.codingchallenge.filesystem.File;
import luxoft.codingchallenge.filesystem.FileSystem;
import luxoft.codingchallenge.solution.filter.FileFilter;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IterableFileSystem implements Iterable<File> {

    private final FileSystem fileSystem;

    public IterableFileSystem(FileSystem fileSystem) {
        this.fileSystem = Objects.requireNonNull(fileSystem, "Filesystem must not be null.");
    }

    @Override
    public Iterator<File> iterator() {
        return new ReverseDiagonalIterator<>(this.fileSystem.files);
    }

    public List<File> search(FileFilter matcher) {
        return StreamSupport.stream(this.spliterator(), false)
                .filter(Objects::nonNull)
                .filter(matcher::matches)
                .collect(Collectors.toList());
    }


}
