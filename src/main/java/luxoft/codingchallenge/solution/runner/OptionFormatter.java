package luxoft.codingchallenge.solution.runner;

import joptsimple.HelpFormatter;
import joptsimple.OptionDescriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class OptionFormatter implements HelpFormatter {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Override
    public String format(Map<String, ? extends OptionDescriptor> options) {
        StringBuilder sb = new StringBuilder();
        sb.append("Usage: java -jar ... [options]");
        sb.append("\n");
        sb.append(" [opt] means optional argument.\n");
        sb.append(" <opt> means required argument.\n");
        sb.append(" \"double quotes\" to enter arguments containing spaces");
        sb.append("\n");
        sb.append("\n");
        for (OptionDescriptor each : options.values()) {
            sb.append(lineFor(each));
        }

        return sb.toString();
    }

    private String lineFor(OptionDescriptor d) {
        StringBuilder line = new StringBuilder();

        StringBuilder o = new StringBuilder();
        o.append("  ");
        for (String str : d.options()) {
            if (!d.representsNonOptions()) {
                o.append("-");
            }
            o.append(str);
            if (d.acceptsArguments()) {
                o.append(" ");
                if (d.requiresArgument()) {
                    o.append("<");
                } else {
                    o.append("[");
                }
                o.append(d.argumentDescription());
                if (d.requiresArgument()) {
                    o.append(">");
                } else {
                    o.append("]");
                }
            }
        }

        final int optWidth = 30;

        line.append(String.format("%-" + optWidth + "s", o));
        boolean first = true;
        String desc = d.description();
        List<?> defaults = d.defaultValues();
        if (defaults != null && !defaults.isEmpty()) {
            desc += " (default: " + defaults + ")";
        }
        for (String l : rewrap(desc)) {
            if (first) {
                first = false;
            } else {
                line.append(LINE_SEPARATOR);
                line.append(String.format("%-" + optWidth + "s", ""));
            }
            line.append(l);
        }

        line.append(LINE_SEPARATOR);
        line.append(LINE_SEPARATOR);
        return line.toString();
    }

    public static Collection<String> rewrap(String lines) {
        Collection<String> result = new ArrayList<>();
        String[] words = lines.split("[ \n]");
        StringBuilder line = new StringBuilder();
        int cols = 0;
        for (String w : words) {
            cols += w.length();
            line.append(w).append(" ");
            if (cols > 40) {
                result.add(line.toString());
                line.setLength(0);
                cols = 0;
            }
        }
        String l = line.toString();
        if (!l.trim().isEmpty()) {
            result.add(l);
        }
        return result;
    }
}

