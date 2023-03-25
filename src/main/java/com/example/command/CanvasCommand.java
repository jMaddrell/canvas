package com.example.command;

import com.example.canvas.element.Canvas;
import com.example.canvas.element.Element;
import com.example.exception.InvalidArgumentsException;
import io.vavr.control.Try;

import java.util.StringTokenizer;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

public class CanvasCommand implements Command {

    public static final String INVALID_COMMAND_MESSAGE = "Invalid command Canvas needs a width and a height, eg: C w h";
    public static final String INVALID_NUMBER_MESSAGE = "Canvas width and height must be a positive number.";

    @SuppressWarnings("unchecked") // .mapFailure is missing @SafeVarargs
    public Try<Element> invoke(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() != 2) {
            return Try.failure(new InvalidArgumentsException(INVALID_COMMAND_MESSAGE));
        }

        return Try.of(() -> {
            var width = Integer.parseInt(tokenizer.nextToken());
            var height = Integer.parseInt(tokenizer.nextToken());
            return (Element) new Canvas(width, height);
        }).mapFailure(Case($(instanceOf(NumberFormatException.class)), e -> new InvalidArgumentsException(INVALID_NUMBER_MESSAGE, e)));
    }
}
