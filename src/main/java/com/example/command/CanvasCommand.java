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
    @SuppressWarnings("unchecked") // .mapFailure is missing @SafeVarargs
    public Try<Element> invoke(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() == 2) {
            return Try.of(() -> {
                int width = Integer.parseInt(tokenizer.nextToken());
                int height = Integer.parseInt(tokenizer.nextToken());
                return (Element) new Canvas(width, height);
            }).mapFailure(Case($(instanceOf(NumberFormatException.class)), e -> new InvalidArgumentsException("Canvas width and height must be a positive number.", e)));
        }
        return Try.failure(new InvalidArgumentsException("Invalid command Canvas needs a width and a height, eg: C w h"));
    }
}
