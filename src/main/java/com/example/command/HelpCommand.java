package com.example.command;

import com.example.canvas.element.Element;
import com.example.exception.InvalidArgumentsException;
import io.vavr.NotImplementedError;
import io.vavr.control.Try;

import java.util.StringTokenizer;

public class HelpCommand implements Command {
    public Try<Element> invoke(StringTokenizer tokenizer) {
        var builder = new StringBuilder();
        builder.append("Commands:");
        builder.append(System.lineSeparator());
        builder.append("\tC w h\t\t\t\tCreate a canvas of width w and height h.");
        builder.append(System.lineSeparator());
        builder.append("\tL x1 y1 x2 y2\t\tCreate a new line from (x1,y1) to (x2,y2). Currently only horizontal or vertical lines are - supported");
        builder.append(System.lineSeparator());
        builder.append("\tR x1 y1 x2 y2\t\tCreate a new rectangle from (x1,y1) to (x2,y2). Whose upper left corner is (x1,y1) and lower right corner is (x2,y2).");
        builder.append(System.lineSeparator());
        builder.append("\tQ\t\t\t\t\tQuit the application");
        builder.append(System.lineSeparator());

        return Try.failure(new InvalidArgumentsException(builder.toString()));
    }
}
