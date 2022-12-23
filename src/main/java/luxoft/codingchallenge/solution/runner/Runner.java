package luxoft.codingchallenge.solution.runner;

import joptsimple.OptionParser;
import luxoft.codingchallenge.filesystem.File;
import luxoft.codingchallenge.filesystem.FileSystem;
import luxoft.codingchallenge.solution.FileFilterBuilder;
import luxoft.codingchallenge.solution.IterableFileSystem;
import luxoft.codingchallenge.solution.filter.FileFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Runner {

    private final OptionParser parser;

    private FileFilter current;

    private FileFilter previous;

    public Runner(OptionParser parser) {
        this.parser = Objects.requireNonNull(parser, "parser must not be null.");
    }

    /**
     * Returns false to signal user requested quit.
     * */
    public boolean run(String[] args) throws IOException {
        try {
            CommandLineOptions options = CommandLineOptions.parseOptions(parser, args);
            if (options.quit()) {
                return true;
            }
            if (options.shouldHelp()) {
                parser.printHelpOn(System.err);
            } else if (options.printPrevious()) {
                if (previous != null) {
                    printFiles(searchFiles(previous));
                } else {
                    System.err.println("No previous result yet.");
                }
            } else {
                var filter = new FileFilterBuilder(options).build();
                printFiles(searchFiles(filter));
                previous = (current != null) ? current : filter;
                current = filter;
            }
        } catch (CommandLineOptionException e) {
            System.err.println("Error parsing command line:");
            System.err.println(" " + e.getMessage());
            System.err.println("To get help, please use -h option.");
        }
        return false;
    }

    /**
     * Can be overridden to print to different outputs such as a file, network etc.
     * */
    protected void printFiles(List<File> files) {
        files.forEach(System.out::println);
    }

    /**
     * Can be overridden to for a different search strategy
     * */
    protected List<File> searchFiles(FileFilter filter) {
        return new IterableFileSystem(FileSystem.FILE_SYSTEM).search(filter);
    }
}

