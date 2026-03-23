package com.example.Pen;

public class Application {
    public static void main(String[] args) {
        Pen pen = PenFactory.getPen("ink-pen", "blue", "with-cap");

        pen.start();
        pen.write("Hello World");
        pen.close();

        pen.refill("black");
    }
}
