package com.example.rendering;

import com.example.canvas.element.Canvas;
import com.example.canvas.element.Pixel;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.stream.Collectors;

public class ConsoleRenderer {
    public static final String BORDER_Y = "-";
    public static final char BORDER_X = '|';
    @Getter
    @Setter
    private Canvas canvas;
    @Getter
    @Setter
    private boolean enableClipping = false;

    public void display() {
        if (this.canvas != null) {
            System.out.println(draw());
            System.out.println();
        }
    }

    public String draw() {
        if (this.canvas != null) {
            var output = new StringBuilder();
            var data = this.canvas.draw(this.enableClipping);

            // Border is two chars wider than the canvas
            var borderWidth = this.getCanvas().getWidth() + 2;

            // Top of the border
            output.append(BORDER_Y.repeat(borderWidth));
            output.append(System.lineSeparator());

            for (var y = 0; y < this.canvas.getHeight(); y++) {
                var finalY = y;

                String pixels = data.stream()
                        .filter(pixel -> (pixel.getY() == finalY))
                        .sorted(Comparator.comparing(Pixel::getX))
                        .map(Pixel::getData)
                        .map(Object::toString)
                        .collect(Collectors.joining());

                output.append(BORDER_X);
                output.append(pixels);
                output.append(BORDER_X);
                output.append(System.lineSeparator());
            }

            // Bottom of the border
            output.append(BORDER_Y.repeat(borderWidth));

            return output.toString();
        }

        return null;
    }
}
