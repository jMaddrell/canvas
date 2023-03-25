package com.example.canvas.element;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pixel {
    private int x;

    private int y;

    private char data;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
