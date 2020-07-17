package com.rc.designpattern.pattern.behavioural.state;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum ShapeType {

    CIRCLE(0), RECTANGLE(1), TRIANGLE(2), COMPOSITE(3);

    private int value;

    ShapeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}