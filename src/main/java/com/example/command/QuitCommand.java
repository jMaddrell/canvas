package com.example.command;

import com.example.canvas.element.Element;
import com.example.exception.QuitException;
import io.vavr.control.Try;

import java.util.StringTokenizer;

public class QuitCommand implements Command {
    public Try<Element> invoke(StringTokenizer tokenizer) {
        return Try.failure(new QuitException());
    }
}
