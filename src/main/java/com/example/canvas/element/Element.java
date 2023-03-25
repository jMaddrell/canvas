package com.example.canvas.element;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public abstract class Element {
    @Getter
    protected Pixel start;
    @Getter
    protected Pixel end;

    abstract List<Pixel> draw(boolean enableClipping);
}
