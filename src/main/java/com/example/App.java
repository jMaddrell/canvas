package com.example;

import com.example.canvas.element.Canvas;
import com.example.canvas.element.Element;
import com.example.command.CanvasCommand;
import com.example.command.Command;
import com.example.command.HelpCommand;
import com.example.command.LineCommand;
import com.example.command.QuitCommand;
import com.example.command.RectangleCommand;
import com.example.rendering.ConsoleRenderer;
import io.vavr.control.Try;

import java.util.Scanner;
import java.util.StringTokenizer;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public class App {
    private boolean enableClipping;

    private ConsoleRenderer consoleRenderer;

    public App(boolean enableClipping) {
        this.enableClipping = enableClipping;
        this.consoleRenderer = new ConsoleRenderer();
    }

    public void run() {
        boolean quit = false;

        try (Scanner scanner = new Scanner(System.in)) {
            while (!quit) {
                this.consoleRenderer.display();
                System.out.print("enter command: ");
                quit = processCommand(scanner.nextLine());
            }
        }
    }

    public boolean processCommand(String input) {
        var tokenizer = new StringTokenizer(input, " ");

        if (!tokenizer.hasMoreTokens()) {
            return false;
        }

        var command = getCommand(tokenizer);

        Try<Element> element = command.invoke(tokenizer);

        if (element.isFailure()) {
            if (element.getCause() instanceof com.example.exception.QuitException) {
                return true;
            }

            System.out.println(element.getCause().getMessage());
        } else {
            Element data = element.get();

            if (data instanceof Canvas) {
                this.consoleRenderer.setCanvas((Canvas) data);
            } else {
                this.consoleRenderer.getCanvas().addElement(data);
            }
        }

        return false;
    }

    protected static Command getCommand(StringTokenizer tokenizer) {
        var command = Match(tokenizer.nextToken()).of(
                Case($("Q"), new QuitCommand()),
                Case($("C"), new CanvasCommand()),
                Case($("L"), new LineCommand()),
                Case($("R"), new RectangleCommand()),
                Case($(), new HelpCommand())
        );
        return command;
    }

    public static void main(String[] args) {
        new App(false).run();
    }
}
