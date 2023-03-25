package com.example.canvas.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

import java.util.ArrayList;
import java.util.List;

@Generated
@EqualsAndHashCode
public class Line extends Element {
    private List<Pixel> pixels;

    public Line(Pixel start, Pixel end) {
        super(start, end);
    }

    List<Pixel> draw(boolean enableClipping) {
        if (this.pixels == null) {
            this.pixels = new ArrayList<>();

            var minX = Math.min(this.start.getX(), this.end.getX());
            var maxX = Math.max(this.start.getX(), this.end.getX());
            var minY = Math.min(this.start.getY(), this.end.getY());
            var maxY = Math.max(this.start.getY(), this.end.getY());

            for (var x = minX; x <= maxX; x++) {
                for (var y = minY; y <= maxY; y++) {
                    this.pixels.add(new Pixel(x, y, 'x'));
                }
            }
        }
        return this.pixels;
    }
}
