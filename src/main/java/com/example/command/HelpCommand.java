package com.example.command;

import com.example.canvas.element.Element;
import com.example.exception.InvalidArgumentsException;
import io.vavr.control.Try;

import java.util.StringTokenizer;

public class HelpCommand implements Command {

    public static final String HELP_MESSAGE = "Commands:" +
            System.lineSeparator() +
            "\tC w h\t\t\t\tCreate a canvas of width w and height h." +
            System.lineSeparator() +
            "\tL x1 y1 x2 y2\t\tCreate a new line from (x1,y1) to (x2,y2). Currently only horizontal or vertical lines are - supported" +
            System.lineSeparator() +
            "\tR x1 y1 x2 y2\t\tCreate a new rectangle from (x1,y1) to (x2,y2). Whose upper left corner is (x1,y1) and lower right corner is (x2,y2)." +
            System.lineSeparator() +
            "\tQ\t\t\t\t\tQuit the application" +
            System.lineSeparator();

    public Try<Element> invoke(StringTokenizer tokenizer) {
        return Try.failure(new InvalidArgumentsException(HELP_MESSAGE));
    }
}
