package com.example.command;

import com.example.canvas.element.Element;
import com.example.canvas.element.Pixel;
import com.example.canvas.element.Rectangle;
import com.example.exception.InvalidArgumentsException;
import io.vavr.control.Try;

import java.util.StringTokenizer;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

public class RectangleCommand implements Command {
    @SuppressWarnings("unchecked") // .mapFailure is missing @SafeVarargs
    public Try<Element> invoke(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() == 4) {
            return Try.of(() -> {
                Pixel start = new Pixel(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()));
                Pixel end = new Pixel(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()));
                return (Element) new Rectangle(start, end);
            }).mapFailure(Case($(instanceOf(NumberFormatException.class)), e -> new InvalidArgumentsException("Rectangle coordinates must be a positive number", e)));
        }

        return Try.failure(new InvalidArgumentsException("Invalid command Rectangle needs two coordinates, eg: R x1 y1 x2 y2"));
    }
}
