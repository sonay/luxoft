package luxoft.codingchallenge.solution.filter;

import luxoft.codingchallenge.filesystem.File;
import luxoft.codingchallenge.filesystem.Permissions;

import java.util.Objects;

/**
 * A FileFilter that matches if given Permission is equal to file's permission.
 * */
public class FilePermissionFilter implements FileFilter {

    private final Permissions permission;

    public FilePermissionFilter(Permissions permission) {
        this.permission = Objects.requireNonNull(permission);
    }

    @Override
    public boolean matches(File file) {
        Objects.requireNonNull(file, "file must not be null.");
        // enum instances are guaranteed to be singletons by JVM and JLS.
        return permission == file.getPermission();
    }
}

