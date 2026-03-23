package com.example.Pen;
public class SimpleRefill implements RefillStrategy {

    public void refill(Pen pen, String color) {

        pen.color = color;

        System.out.println("Refilled pen with color: " + color);
    }
}