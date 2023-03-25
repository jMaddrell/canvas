package com.example.canvas.element;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Element {
    private List<Pixel> pixels;

    public Rectangle(Pixel start, Pixel end) {
        super(start, end);
    }

    List<Pixel> draw(boolean enableClipping) {
        if (this.pixels == null) {
            int minX = Math.min(this.start.getX(), this.end.getX());
            int maxX = Math.max(this.start.getX(), this.end.getX());
            int minY = Math.min(this.start.getY(), this.end.getY());
            int maxY = Math.max(this.start.getY(), this.end.getY());

            this.pixels = new ArrayList<>();

            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    if (x == minX || x == maxX || y == minY || y == maxY) {
                        this.pixels.add(new Pixel(x, y, 'x'));
                    } else if (!enableClipping) {
                        // Fill the centre when clipping is disabled
                        this.pixels.add(new Pixel(x, y, ' '));
                    }
                }
            }
        }
        return this.pixels;
    }
}
