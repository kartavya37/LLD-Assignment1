package com.example.Pen;
public class InkPen extends Pen {

    public InkPen(String color,
                  RefillStrategy refillStrategy,
                  StartStrategy startStrategy) {
        super(color, refillStrategy, startStrategy);
    }

    @Override
    public void write(String text) {

        if(!isStarted) {
            throw new RuntimeException("Pen not started!");
        }

        System.out.println("InkPen writing: " + text);
    }
}