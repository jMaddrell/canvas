package com.example.canvas.element;

import com.example.exception.InvalidArgumentsException;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pixel {
    public static final String INVALID_COORDINATE_MESSAGE = "Only coordinates greater than x: 1, y: 1 are supported.";
    private int x;

    private int y;

    private char data;

    public Pixel(int x, int y) {
        if (x <= 0 || y <= 0) {
            throw new InvalidArgumentsException(INVALID_COORDINATE_MESSAGE);
        }
        this.x = x;
        this.y = y;
    }
}
