package com.rc.designpattern.pattern.behavioural.memento;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public interface Originator<T> {

    Memento<T> saveToMemento();

    void restoreFromMemento(Memento<T> memento);

    T getState();
}