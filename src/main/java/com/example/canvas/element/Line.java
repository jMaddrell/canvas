package com.example.canvas.element;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
public class Line extends Element {
    private List<Pixel> pixels;

    public Line(Pixel start, Pixel end) {
        super(start, end);
    }

    @Override
    List<Pixel> draw(boolean enableClipping) {
        if (this.pixels == null) {
            this.pixels = new ArrayList<>();

            var minX = Math.min(this.start.getX(), this.end.getX());
            var maxX = Math.max(this.start.getX(), this.end.getX());
            var minY = Math.min(this.start.getY(), this.end.getY());
            var maxY = Math.max(this.start.getY(), this.end.getY());

            for (var x = minX; x <= maxX; x++) {
                for (var y = minY; y <= maxY; y++) {
                    this.pixels.add(new Pixel(x, y, OCCUPIED));
                }
            }
        }

        return this.pixels;
    }
}
