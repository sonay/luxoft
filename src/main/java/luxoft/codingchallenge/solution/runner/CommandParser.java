package luxoft.codingchallenge.solution.runner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * An input line parser that tries to mimic terminal to argv conversion.
 * It is not perfect at all.
 * Normally I would use a CharacterReader, Lexer
 * */
public class CommandParser {

    private static final Pattern PREV_COMMAND = Pattern.compile("\\s*-l\\s*");

    private static final char DOUBLE_QUOTE = '"';

    private static final char OPTION_START = '-';


    private State state = new InitialState();

    private final String input;

    private int cursor = 0;

    public CommandParser(String input) {
        this.input = input;
    }

    public String[] parse() {
        // simple case if only -l is defined, return it
        if (PREV_COMMAND.matcher(input).matches()) {
            return new String[]{"-l"};
        }

        List<String> args = new ArrayList<>();
        while (state != null) {
            state = state.read(args);
        }
        return args.toArray(new String[0]);
    }

    private interface State {
        State read(List<String> args);
    }

    private class InitialState implements State {
        @Override
        public State read(List<String> args) {
            skipWhitespace();
            if (hitEnd()) {
                return null;
            }
            char c = peek();
            return (c == OPTION_START) ? new OptionState()
                    : new ValueState();
        }
    }

    private class OptionState implements State {
        @Override
        public State read(List<String> args) {
            int start = cursor;
            while (!hitEnd()) {
                char c = peek();
                if (Character.isWhitespace(c)) { // end of option
                    args.add(input.substring(start, cursor));
                    return new ValueState();
                }
                advance();
            }
            args.add(input.substring(start));
            return null;
        }
    }

    private class ValueState implements State {
        @Override
        public State read(List<String> args) {
            skipWhitespace();
            if (hitEnd()) { // hit end
                return null;
            }
            char c = peek();
            if (c == OPTION_START) {
                // no value, just empty space
                return new OptionState();
            }

            int start = cursor;
            if (c == DOUBLE_QUOTE) { // possibly quoted value
                advance();
                while (!hitEnd()) {
                    c = peek();
                    if (c == DOUBLE_QUOTE) {
                        advance();
                        args.add(removeQuotes(start));
                        return new InitialState();
                    }
                    advance();
                }
            } else { // Unquoted value
                advance();
                while (!hitEnd()) {
                    c = peek();
                    if (Character.isWhitespace(c)) {
                        args.add(input.substring(start, cursor));
                        return new InitialState();
                    }
                    advance();
                }
            }
            // Unclosed double quote or value at the end
            args.add(input.substring(start).stripTrailing());
            return null;
        }
    }

    private String removeQuotes(int start) {
        if (cursor == start + 1) {
            // just double quotes, return empty
            return "";
        }
        return input.substring(start + 1, cursor - 1);
    }

    private void skipWhitespace() {
        while (!hitEnd()) {
            char c = peek();
            if (!Character.isWhitespace(c)) {
                break;
            }
            advance();
        }
    }

    private boolean hitEnd() {
        return cursor == input.length();
    }

    private char peek() {
        return input.charAt(cursor);
    }

    private void advance() {
        cursor++;
    }

}

