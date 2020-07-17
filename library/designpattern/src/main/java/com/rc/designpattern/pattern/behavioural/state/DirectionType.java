package com.rc.designpattern.pattern.behavioural.state;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum DirectionType {

    NONE(0), TOP(0x15), LEFT(0x16), BOTTOM(0x17), RIGHT(0x18), LEFT_TOP(0x11), RIGHT_TOP(0x12), LEFT_BOTTOM(0x13), RIGHT_BOTTOM(0x14), CENTER(0x19);

    private int value;

    DirectionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}