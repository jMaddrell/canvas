package com.example.rendering;


import com.example.canvas.element.Canvas;
import com.example.canvas.element.Element;
import com.example.canvas.element.Pixel;
import com.example.canvas.element.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

class ConsoleRendererClippingTest {
    private static ConsoleRenderer renderer;

    @BeforeAll
    static void setup() {
        renderer = new ConsoleRenderer();
        renderer.setEnableClipping(true);
        renderer.setCanvas(new Canvas(20, 4));
    }

    private static Stream<Arguments> examplesProvider() {
        return Stream.of(
                Arguments.of(null, "/examples/1.txt"),
                Arguments.of(new Rectangle(new Pixel(1, 1), new Pixel(8, 3)), "/clipping/1.txt"),
                Arguments.of(new Rectangle(new Pixel(6,2), new Pixel(16,4)), "/clipping/2.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("examplesProvider")
    void itDrawsExpectedOutput(Element element, String exampleOutput) {
        if (element != null) {
            renderer.getCanvas().addElement(element);
        }

        assertThat(renderer.draw()).isEqualTo(contentOf(this.getClass().getResource(exampleOutput)));
    }
}