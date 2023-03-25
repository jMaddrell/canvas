package com.example.canvas.element;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Line extends Element {
    private List<Pixel> pixels;

    public Line(Pixel start, Pixel end) {
        super(start, end);
    }

    List<Pixel> draw(boolean enableClipping) {
        if (this.pixels == null) {
            this.pixels = new ArrayList<>();

            int minX = Math.min(this.start.getX(), this.end.getX());
            int maxX = Math.max(this.start.getX(), this.end.getX());
            int minY = Math.min(this.start.getY(), this.end.getY());
            int maxY = Math.max(this.start.getY(), this.end.getY());

            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    this.pixels.add(new Pixel(x, y, 'x'));
                }
            }
        }
        return this.pixels;
    }
}
