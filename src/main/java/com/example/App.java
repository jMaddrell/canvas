package com.example;

import com.example.canvas.element.Canvas;
import com.example.command.CanvasCommand;
import com.example.command.Command;
import com.example.command.HelpCommand;
import com.example.command.LineCommand;
import com.example.command.QuitCommand;
import com.example.command.RectangleCommand;
import com.example.exception.QuitException;
import com.example.rendering.ConsoleRenderer;
import io.vavr.control.Try;

import java.util.Scanner;
import java.util.StringTokenizer;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public class App {

    protected ConsoleRenderer consoleRenderer;

    public App(boolean enableClipping) {
        this.consoleRenderer = new ConsoleRenderer();
        this.consoleRenderer.setEnableClipping(enableClipping);
    }

    public void run() {
        boolean quit = false;

        try (Scanner scanner = new Scanner(System.in)) {
            while (!quit) {
                this.consoleRenderer.display();
                System.out.print("enter command: ");
                quit = processCommand(scanner.nextLine());
                System.out.println();
            }
        }
    }

    public boolean processCommand(String input) {
        var tokenizer = new StringTokenizer(input, " ");

        var command = getCommand(tokenizer);

        Try<Boolean> result = command.invoke(tokenizer)
                .onSuccess(data -> {
                    if (data instanceof Canvas) {
                        this.consoleRenderer.setCanvas((Canvas) data);
                    } else {
                        Canvas canvas = this.consoleRenderer.getCanvas();

                        if (canvas == null) {
                            System.out.println();
                            System.out.println("Please create a canvas first.");
                        } else {
                            canvas.addElement(data)
                                    .onFailure(t -> System.out.println(t.getMessage()));
                        }
                    }
                })
                .map(data -> false)
                .recover(t -> {
                    if (t instanceof QuitException) {
                        return true;
                    }

                    System.out.println(t.getMessage());
                    return false;
                });


        return result.get();
    }

    protected static Command getCommand(StringTokenizer tokenizer) {
        if (tokenizer.hasMoreTokens()) {
            return Match(tokenizer.nextToken()).of(
                    Case($("Q"), new QuitCommand()),
                    Case($("C"), new CanvasCommand()),
                    Case($("L"), new LineCommand()),
                    Case($("R"), new RectangleCommand()),
                    Case($(), new HelpCommand())
            );
        }

        return new HelpCommand();
    }

    public static void main(String[] args) {
        boolean enableClipping = isClippingEnabled(args);
        new App(enableClipping).run();
    }

    public static boolean isClippingEnabled(String[] args) {
        return args != null && args.length > 0 && "enableClipping".equals(args[0]);
    }
}
