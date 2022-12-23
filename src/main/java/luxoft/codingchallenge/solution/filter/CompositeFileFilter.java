package luxoft.codingchallenge.solution.filter;

import luxoft.codingchallenge.filesystem.File;

import java.util.List;

/**
 * A FileFilter that matches if all contained filters match a given file.
 * */
public class CompositeFileFilter implements FileFilter {

    private final List<FileFilter> filters;

    public CompositeFileFilter(List<FileFilter> filters) {
        this.filters = List.copyOf(filters); // implicit null check on elements.
    }

    @Override
    public boolean matches(File f) {
        for (var filter : filters) {
            if (!filter.matches(f)) {
                return false;
            }
        }
        return true;
    }
}
