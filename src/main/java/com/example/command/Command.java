package com.example.command;

import com.example.canvas.element.Element;
import io.vavr.control.Try;

import java.util.StringTokenizer;

public interface Command {
    Try<Element> invoke(StringTokenizer paramStringTokenizer);
}
