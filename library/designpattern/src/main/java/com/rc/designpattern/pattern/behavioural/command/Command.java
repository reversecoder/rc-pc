package com.rc.designpattern.pattern.behavioural.command;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public interface Command {

    void doIt();

    void undoIt();

    String whoAmI();
}