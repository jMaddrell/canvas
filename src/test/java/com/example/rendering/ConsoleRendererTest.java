package com.example.rendering;

import com.example.App;
import com.example.canvas.element.Canvas;
import com.example.canvas.element.Line;
import com.example.canvas.element.Pixel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;
import static org.junit.jupiter.api.Assertions.*;

class ConsoleRendererTest {

    @Test
    void itHandlesMissingCanvas() {
        var renderer = new ConsoleRenderer();
        assertNull(renderer.draw());
    }

    @Test
    void itPrintsNothingIfMissingCanvas() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        var renderer = new ConsoleRenderer();
        renderer.display();

        assertThat(outputStreamCaptor.toString()).isBlank();

        // Restore stdout
        System.setOut(standardOut);
    }

    @Test
    void itDisplaysOnConsole() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        var renderer = new ConsoleRenderer();
        renderer.setCanvas(new Canvas(20, 4));
        renderer.getCanvas().addElement(new Line(new Pixel(1, 2), new Pixel(6, 2)));

        renderer.display();

        assertThat(outputStreamCaptor.toString()).contains(contentOf(this.getClass().getResource("/examples/2.txt")));

        // Restore stdout
        System.setOut(standardOut);
    }
}