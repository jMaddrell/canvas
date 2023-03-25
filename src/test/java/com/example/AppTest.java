package com.example;

import com.example.canvas.element.Canvas;
import com.example.canvas.element.Pixel;
import com.example.command.*;
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
import static org.mockito.Mockito.mock;

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
                Arguments.of("C 1 -3", Pixel.INVALID_COORDINATE_MESSAGE)
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
        assertThat(outputStreamCaptor.toString().trim()).contains(expected);

        // Restore stdout
        System.setOut(standardOut);

    }
//    TODO: test exception output
//    TODO: processCommand("")
//    TODO: processCommand()
//    TODO: run()
}