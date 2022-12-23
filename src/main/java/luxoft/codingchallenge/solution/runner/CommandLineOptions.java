package luxoft.codingchallenge.solution.runner;

import joptsimple.*;
import luxoft.codingchallenge.filesystem.Permissions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandLineOptions {

    private final boolean help;

    private final Long size;

    private final String name;

    private final LocalDateTime createdTime;

    private final Permissions permission;

    private final boolean printPrevious;

    private final boolean quit;

    public CommandLineOptions(boolean help, Long size, String name,
                              LocalDateTime createdTime, Permissions permission,
                              boolean printPrevious, boolean quit) {
        this.help = help;
        this.size = size;
        this.name = name;
        this.createdTime = createdTime;
        this.permission = permission;
        this.printPrevious = printPrevious;
        this.quit = quit;
    }

    public static CommandLineOptions parseOptions(
            OptionParser parser, String... args) throws CommandLineOptionException {

        OptionSpec<Long> optFileSize = parser
                .accepts("s", "File size to match (in bytes).")
                .withRequiredArg()
                .withValuesConvertedBy(LongValueConverter.NON_NEGATIVE)
                .describedAs("long");

        OptionSpec<String> optName = parser
                .accepts("n", "File name to match.")
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("name");

        var dateConverter = new LocalDateTimeValueConverter();
        OptionSpec<LocalDateTime> optCreatedTime = parser.accepts( "t", "File created time to match." )
                .withRequiredArg()
                .withValuesConvertedBy(dateConverter)
                .describedAs(dateConverter.valuePattern());

        String permissionValues = Arrays.stream(Permissions.values())
                .map(Enum::toString)
                .collect(Collectors.joining(", "));
        OptionSpec<Permissions> optPermissions = parser
                .accepts( "p", "File permission to match. Accepted values: "
                        + permissionValues + " (case insensitive).")
                .withRequiredArg()
                .withValuesConvertedBy(new PermissionsValueConverter())
                .describedAs("permission");

        parser.accepts("l", "Print last returned query result." +
                " It is an error to use this option with other options or arguments." +
                " They will silently will be ignored.");

        parser.accepts("h", "Display help, and exit.").forHelp();

        parser.accepts("q", "To quit");

        try {
            OptionSet set = parser.parse(args);
            boolean help = set.has("h") || !set.hasOptions();
            Long fileSize = set.valueOf(optFileSize);
            String name = set.valueOf(optName);
            LocalDateTime time = set.valueOf(optCreatedTime);
            Permissions permission = set.valueOf(optPermissions);
            boolean printPrevious = set.has("l");
            boolean quit = set.has("q");
            return new CommandLineOptions(help, fileSize, name, time, permission, printPrevious, quit);
        } catch (OptionException e) {
            String message = e.getMessage();
            Throwable cause = e.getCause();
            if (cause instanceof ValueConversionException) {
                message += ". " + cause.getMessage();
            }
            throw new CommandLineOptionException(message, e);
        }
    }

    public boolean shouldHelp() {
        return help;
    }

    public Long size() {
        return size;
    }

    public Permissions permission() {
        return permission;
    }

    public String name() {
        return name;
    }

    public LocalDateTime createdTime() {
        return createdTime;
    }

    public boolean printPrevious() {
        return printPrevious;
    }

    public boolean quit() {
        return quit;
    }

}

