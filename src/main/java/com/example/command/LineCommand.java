package com.example.command;

import com.example.canvas.element.Element;
import com.example.canvas.element.Line;
import com.example.canvas.element.Pixel;
import com.example.exception.InvalidArgumentsException;
import io.vavr.control.Try;

import java.util.StringTokenizer;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

public class LineCommand implements Command {
    public static final String INVALID_COMMAND_MESSAGE = "Invalid command Line needs two coordinates, eg: L x1 y1 x2 y2";
    public static final String INVALID_NUMBER_MESSAGE = "Line coordinates must be a positive number.";

    @SuppressWarnings("unchecked") // .mapFailure is missing @SafeVarargs
    public Try<Element> invoke(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() != 4) {
            return Try.failure(new InvalidArgumentsException(INVALID_COMMAND_MESSAGE));
        }

        return Try.of(() -> {
            var start = new Pixel(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()));
            var end = new Pixel(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()));
            return (Element) new Line(start, end);
        }).mapFailure(Case($(instanceOf(NumberFormatException.class)), e -> new InvalidArgumentsException(INVALID_NUMBER_MESSAGE, e)));
    }
}
