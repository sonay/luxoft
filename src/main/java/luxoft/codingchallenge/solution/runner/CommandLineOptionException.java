package luxoft.codingchallenge.solution.runner;

public class CommandLineOptionException extends Exception {

    private static final long serialVersionUID = 4023975483757781721L;

    public CommandLineOptionException(String message) {
        super(message);
    }

    public CommandLineOptionException(String message, Throwable cause) {
        super(message, cause);
    }

}

