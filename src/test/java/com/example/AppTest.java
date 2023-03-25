package com.example;

import com.example.canvas.element.*;
import com.example.command.*;
import com.example.rendering.ConsoleRenderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;
import static org.mockito.Mockito.*;

class AppTest {
    private static Stream<Arguments> commandsProvider() {
        return Stream.of(
                Arguments.of("C", CanvasCommand.class),
                Arguments.of("", HelpCommand.class), // no input
                Arguments.of("T", HelpCommand.class), // unknown command
                Arguments.of("L", LineCommand.class),
                Arguments.of("R", RectangleCommand.class),
                Arguments.of("Q", QuitCommand.class)
        );
    }
    @ParameterizedTest
    @MethodSource("commandsProvider")
    void itParsesCommandsCorrectly(String input, Class commandType) {
        Command command = App.getCommand(new StringTokenizer(input, " "));

        assertThat(command).isInstanceOf(commandType);
    }

    @Test
    void itQuits() {
        var app = new App(false);
        var canvas = mock(Canvas.class);
        app.consoleRenderer.setCanvas(canvas);
        boolean quit = app.processCommand("Q");

        assertThat(quit).isTrue();
    }

    private static Stream<Arguments> errorMessageProvider() {
        return Stream.of(
                Arguments.of("L", LineCommand.INVALID_COMMAND_MESSAGE),
                Arguments.of("L 1 2", LineCommand.INVALID_COMMAND_MESSAGE),
                Arguments.of("L 1 2 test 3", LineCommand.INVALID_NUMBER_MESSAGE),
                Arguments.of("L 1 2 -1 3", Pixel.INVALID_COORDINATE_MESSAGE),
                Arguments.of("R", RectangleCommand.INVALID_COMMAND_MESSAGE),
                Arguments.of("R 1 2", RectangleCommand.INVALID_COMMAND_MESSAGE),
                Arguments.of("R 1 2 test 3", RectangleCommand.INVALID_NUMBER_MESSAGE),
                Arguments.of("R 1 2 -1 3", Pixel.INVALID_COORDINATE_MESSAGE),
                Arguments.of("C", CanvasCommand.INVALID_COMMAND_MESSAGE),
                Arguments.of("C 1", CanvasCommand.INVALID_COMMAND_MESSAGE),
                Arguments.of("C test 3", CanvasCommand.INVALID_NUMBER_MESSAGE),
                Arguments.of("C 1 -3", Pixel.INVALID_COORDINATE_MESSAGE),
                Arguments.of("H", HelpCommand.HELP_MESSAGE),
                Arguments.of("", HelpCommand.HELP_MESSAGE)
        );
    }

    @ParameterizedTest
    @MethodSource("errorMessageProvider")
    void itPrintsErrorMessages(String input, String expected) {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        var app = new App(false);

        app.processCommand(input);
        assertThat(outputStreamCaptor.toString()).contains(expected);

        // Restore stdout
        System.setOut(standardOut);
    }

    private static Stream<Arguments> elementsProvider() {
        return Stream.of(
                Arguments.of("L 1 2 6 2", new Line(new Pixel(1, 2), new Pixel(6, 2))),
                Arguments.of("R 1 1 8 3", new Rectangle(new Pixel(1, 1), new Pixel(8, 3)))
        );
    }
    @ParameterizedTest
    @MethodSource("elementsProvider")
    void itAddsElementToCanvas(String input, Element element) {
        var app = new App(false);
        var canvas = mock(Canvas.class);
        app.consoleRenderer.setCanvas(canvas);
        app.processCommand(input);

        verify(canvas, times(1)).addElement(eq(element));
    }

    @Test
    void itCreatesCanvas() {
        var app = new App(false);
        var renderer = mock(ConsoleRenderer.class);
        app.consoleRenderer = renderer;
        app.processCommand("C 20 4");

        verify(renderer, times(1)).setCanvas(eq(new Canvas(20, 4)));
    }

//    TODO: processCommand() - with valid values
//    TODO: run()
}