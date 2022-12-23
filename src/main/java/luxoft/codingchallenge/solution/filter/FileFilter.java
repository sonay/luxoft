package luxoft.codingchallenge.solution.filter;

import luxoft.codingchallenge.filesystem.File;

public interface FileFilter {

    boolean matches(File f);

}
