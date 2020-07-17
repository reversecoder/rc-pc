package com.rc.designpattern.pattern.behavioural.observer;

import java.util.List;

/**
 * This is Observable
 *
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public interface Publisher <T>{
    public void setValue(T value);
    public void setValue(T value, Subscriber subscriber);
    boolean registerSubscriber(Subscriber subscriber);
    boolean removeSubscriber(Subscriber subscriber);
    public List<Subscriber> getSubscribers();
    public void notifySubscribers();
    public void notifySubscriber(Subscriber subscriber);
}