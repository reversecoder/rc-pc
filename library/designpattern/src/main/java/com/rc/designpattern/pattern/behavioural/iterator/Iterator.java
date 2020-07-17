package com.rc.designpattern.pattern.behavioural.iterator;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public interface Iterator<E> {
    void reset();

    E next();

    E currentItem();

    boolean hasNext();
}