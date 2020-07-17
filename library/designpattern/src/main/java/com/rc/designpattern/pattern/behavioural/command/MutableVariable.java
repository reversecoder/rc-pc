package com.rc.designpattern.pattern.behavioural.command;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class MutableVariable <T> {

    private T value;

    public MutableVariable(T value) {
        setValue(value);
    }

    public T getValue() {
        return value;
    }

    public MutableVariable setValue(T value) {
        this.value = value;
        return this;
    }
}