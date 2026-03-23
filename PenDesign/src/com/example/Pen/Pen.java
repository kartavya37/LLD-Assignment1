package com.example.Pen;
abstract class Pen {

    protected String color;

    protected RefillStrategy refillStrategy;
    protected StartStrategy startStrategy;

    protected boolean isStarted = false;

    public Pen(String color,
               RefillStrategy refillStrategy,
               StartStrategy startStrategy) {

        this.color = color;
        this.refillStrategy = refillStrategy;
        this.startStrategy = startStrategy;
    }

    // All the 

    public abstract void write(String text);

    public void refill(String newColor) {
        refillStrategy.refill(this, newColor);
    }

    public void start() {
        startStrategy.start(this);
        isStarted = true;
    }

    public void close() {
        isStarted = false;
    }
}