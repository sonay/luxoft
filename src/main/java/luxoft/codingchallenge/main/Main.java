package luxoft.codingchallenge.main;


import joptsimple.OptionParser;
import luxoft.codingchallenge.solution.runner.OptionFormatter;
import luxoft.codingchallenge.solution.runner.Runner;
import luxoft.codingchallenge.solution.runner.CommandParser;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        OptionParser parser = new OptionParser();
        parser.formatHelpWith(new OptionFormatter());

        Runner runner = new Runner(parser);
        runner.run(args);
        boolean quit = false;
        try (Scanner in = new Scanner(System.in)) {
            while (!quit && in.hasNextLine()) {
                args = new CommandParser(in.nextLine()).parse();
                quit = runner.run(args);
            }
        }
    }
}
