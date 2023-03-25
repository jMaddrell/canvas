package com.example.rendering;

import com.example.canvas.element.Canvas;
import com.example.canvas.element.Pixel;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleRenderer {
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
            StringBuilder output = new StringBuilder();
            List<Pixel> data = this.canvas.draw(this.enableClipping);

            // Border is two chars wider than the canvas
            var borderWidth = this.getCanvas().getWidth() + 2;

            // Top of the border
            output.append("-".repeat(borderWidth));
            output.append(System.lineSeparator());

            for (int y = 0; y < this.canvas.getHeight(); y++) {
                int finalY = y;

                String pixels = data.stream()
                        .filter(pixel -> (pixel.getY() == finalY))
                        .sorted(Comparator.comparing(Pixel::getX))
                        .map(Pixel::getData)
                        .map(Object::toString)
                        .collect(Collectors.joining());

                output.append('|');
                output.append(pixels);
                output.append('|');
                output.append(System.lineSeparator());
            }

            // Bottom of the border
            output.append("-".repeat(borderWidth));

            return output.toString();
        }

        return null;
    }
}
