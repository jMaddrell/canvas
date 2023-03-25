package com.example.command;

import com.example.canvas.element.Element;
import io.vavr.NotImplementedError;
import io.vavr.control.Try;

import java.util.StringTokenizer;

public class HelpCommand implements Command {
    public Try<Element> invoke(StringTokenizer tokenizer) {
        return Try.failure(new NotImplementedError());
    }
}
