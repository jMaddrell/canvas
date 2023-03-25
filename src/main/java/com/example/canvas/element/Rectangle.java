package com.example.canvas.element;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
public class Rectangle extends Element {
    private List<Pixel> pixels;

    public Rectangle(Pixel start, Pixel end) {
        super(start, end);
    }

    @Override
    List<Pixel> draw(boolean enableClipping) {
        if (this.pixels == null) {
            var minX = Math.min(this.start.getX(), this.end.getX());
            var maxX = Math.max(this.start.getX(), this.end.getX());
            var minY = Math.min(this.start.getY(), this.end.getY());
            var maxY = Math.max(this.start.getY(), this.end.getY());

            this.pixels = new ArrayList<>();

            for (var y = minY; y <= maxY; y++) {
                for (var x = minX; x <= maxX; x++) {
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
