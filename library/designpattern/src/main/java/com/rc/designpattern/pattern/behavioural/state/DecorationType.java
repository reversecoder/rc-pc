package com.rc.designpattern.pattern.behavioural.state;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum DecorationType {

    SHAPE_X(0), SHAPE_Y(1), SHAPE_RADIUS(2), SHAPE_WIDTH(3), SHAPE_HEIGHT(4), SHAPE_BACKGROUND_COLOR(5), SHAPE_COLOR(6), SHAPE_STATE(7);

    private int value;

    DecorationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}