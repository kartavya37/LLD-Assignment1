package com.example.Pen;

public class PenFactory {

    public static Pen getPen(String type, String color, String mechanism) {

        RefillStrategy refill = new SimpleRefill();

        StartStrategy start;

        if(mechanism.equals("with-cap")) start = new CapStart();
        else start = new ClickStart();

        if(type.equals("ink-pen")) {
            return new InkPen(color, refill, start);
        }
        else if(type.equals("ball-pen")) {
            return new BallPen(color, refill, start);
        }

        throw new IllegalArgumentException("Unknown pen type");
    }
}
    

