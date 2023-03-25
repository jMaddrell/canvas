package com.example.rendering;


import com.example.canvas.element.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;
class ConsoleRendererTest {
    private static ConsoleRenderer renderer;

    @BeforeAll
    static void setup() {
        renderer = new ConsoleRenderer();
        renderer.setCanvas(new Canvas(20, 4));
    }

    private static Stream<Arguments> examplesProvider() {
        return Stream.of(
                Arguments.of(null, "/examples/1.txt"),
                Arguments.of(new Line(new Pixel(1, 2), new Pixel(6, 2)), "/examples/2.txt"),
                Arguments.of(new Line(new Pixel(6,3), new Pixel(6,4)), "/examples/3.txt"),
                Arguments.of(new Rectangle(new Pixel(16, 1), new Pixel(20, 3)), "/examples/4.txt")
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


//TODO: Test for clipping support?
//    TODO: Test command parsing
//    TODO: Test bounds checking on element creation
}