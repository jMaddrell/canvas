package com.example.canvas.element;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public abstract class Element {
    public static final char BLANK = ' ';
    public static final char OCCUPIED = 'x';

    @Getter
    protected Pixel start;
    @Getter
    protected Pixel end;

    abstract List<Pixel> draw(boolean enableClipping);
}
