package com.example.canvas.element;

import com.example.exception.InvalidArgumentsException;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CanvasTest {

    private static Stream<Arguments> invalidElementPositions() {
        return Stream.of(
                Arguments.of(new Line(new Pixel(20, 1), new Pixel(21, 1))),
                Arguments.of(new Line(new Pixel(21, 1), new Pixel(21, 4))),
                Arguments.of(new Line(new Pixel(5, 4), new Pixel(5, 5))),
                Arguments.of(new Line(new Pixel(5, 5), new Pixel(5, 6))),
                Arguments.of(new Rectangle(new Pixel(20, 1), new Pixel(21, 3))),
                Arguments.of(new Rectangle(new Pixel(21, 1), new Pixel(23, 4))),
                Arguments.of(new Rectangle(new Pixel(5, 4), new Pixel(7, 5))),
                Arguments.of(new Rectangle(new Pixel(5, 5), new Pixel(7, 7)))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidElementPositions")
    void itPreventsElementsBeingAddedOutsideCanvas(Element element) {
        var canvas = new Canvas(20, 4);
        Try<Boolean> offRight = canvas.addElement(element);

        assertThat(offRight.isFailure()).isTrue();
        assertThat(offRight.getCause()).isInstanceOf(InvalidArgumentsException.class);
        assertThat(offRight.getCause().getMessage()).isEqualTo(Canvas.INVALID_ELEMENT_LOCATION_MESSAGE);
    }

    @Test
    void itPreventsDiagonalLines() {
        var canvas = new Canvas(20, 4);
        Try<Boolean> diagonal = canvas.addElement(new Line(new Pixel(1, 1), new Pixel(3, 3)));

        assertThat(diagonal.isFailure()).isTrue();
        assertThat(diagonal.getCause()).isInstanceOf(InvalidArgumentsException.class);
        assertThat(diagonal.getCause().getMessage()).isEqualTo(Canvas.INVALID_LINE_MESSAGE);
    }

    @Test
    void itPreventsInvalidRectangles() {
        var canvas = new Canvas(20, 4);
        Try<Boolean> lineY = canvas.addElement(new Rectangle(new Pixel(1, 1), new Pixel(3, 1)));

        assertThat(lineY.isFailure()).isTrue();
        assertThat(lineY.getCause()).isInstanceOf(InvalidArgumentsException.class);
        assertThat(lineY.getCause().getMessage()).isEqualTo(Canvas.INVALID_RECT_MESSAGE);

        Try<Boolean> lineX = canvas.addElement(new Rectangle(new Pixel(1, 1), new Pixel(1, 3)));

        assertThat(lineX.isFailure()).isTrue();
        assertThat(lineX.getCause()).isInstanceOf(InvalidArgumentsException.class);
        assertThat(lineX.getCause().getMessage()).isEqualTo(Canvas.INVALID_RECT_MESSAGE);
    }
}