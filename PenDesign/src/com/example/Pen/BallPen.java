package com.example.Pen;
class BallPen extends Pen {

    public BallPen(String color,
                   RefillStrategy refillStrategy,
                   StartStrategy startStrategy) {
        super(color, refillStrategy, startStrategy);
    }

    @Override
    public void write(String text) {

        if(!isStarted) {
            throw new RuntimeException("Pen not started!");
        }

        System.out.println("BallPen writing: " + text);
    }
}