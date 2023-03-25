package com.example.canvas.element;

import com.example.exception.InvalidArgumentsException;
import io.vavr.control.Try;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Canvas extends Element {
    @Getter
    private final int width;

    @Getter
    private final int height;

    private final Stack<Element> elements;

    public Canvas(int width, int height) {
        super(new Pixel(1, 1), new Pixel(width, height));

        this.width = width;
        this.height = height;
        this.elements = new Stack<>();
    }

    public List<Pixel> draw(boolean enableClipping) {
        char[][] buffer = new char[this.height][this.width];

        // Create a blank canvas
        for (var y = 0; y < this.height; y++) {
            Arrays.fill(buffer[y], ' ');
        }

        // Draw the elements onto the canvas starting at the oldest
        this.elements.forEach(element -> {
            List<Pixel> pixels = element.draw(enableClipping);
            pixels.forEach(pixel -> buffer[pixel.getY() - 1][pixel.getX() - 1] = pixel.getData());
        });

        ArrayList<Pixel> pixels = new ArrayList<>();

        // Convert the buffer to a list of pixels
        // Could potentially remove this and return the buffer directly to speed up rendering
        // however it's required for now as Canvas extends Element
        for (var y = 0; y < this.height; y++) {
            for (var x = 0; x < this.width; x++) {
                pixels.add(new Pixel(x, y, buffer[y][x]));
            }
        }

        return pixels;
    }

    public Try<Boolean> addElement(Element element) {
        return Try.of(() -> {
            if (isValidCoordinates(element)) {
                return this.elements.add(element);
            }

            throw new InvalidArgumentsException("Element must be within the bounds of the canvas");
        });
    }

    private boolean isValidCoordinates(Element element) {
        var start = element.getStart();
        var end = element.getEnd();

        boolean onCanvas = isOnCanvas(start) && isOnCanvas(end);

        if (element instanceof Line) {
            if (start.getX() != end.getX() && start.getY() != end.getY()) {
                throw new InvalidArgumentsException("Not a valid line, diagonals are not supported");
            }
        }

        return onCanvas;
    }

    private boolean isOnCanvas(Pixel pixel) {
        return (pixel.getX() > 0 && pixel.getX() <= width && pixel.getY() > 0 && pixel.getY() <= height);
    }
}
