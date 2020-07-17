package com.rc.designpattern.pattern.behavioural.state;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public enum StateType {

    UNSELECTED(0), SELECTED(1);

    private int value = 0;

    StateType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}