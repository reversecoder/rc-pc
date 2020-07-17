package com.rc.designpattern.pattern.behavioural.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class Topic<T> implements Publisher<T> {

    private List<Subscriber> subscribers;
    private String topicName = "";
    private T topicValue;

    public Topic(List<Subscriber> subscribers, String topicName, T topicValue) {
        this.subscribers = subscribers;
        this.topicName = topicName;
        this.topicValue = topicValue;
    }

    public Topic(String topicName) {
        this.subscribers = new ArrayList<Subscriber>();
        this.topicName = topicName;
    }

    public Topic(String topicName, T topicValue) {
        this.subscribers = new ArrayList<Subscriber>();
        this.topicName = topicName;
        this.topicValue = topicValue;
    }

    public String getTopicName() {
        return topicName;
    }

    public T getTopicValue() {
        return topicValue;
    }

    @Override
    public void setValue(T value) {
        this.topicValue = value;
        notifySubscribers();
    }

    @Override
    public void setValue(T value, Subscriber subscriber) {
        this.topicValue = value;
        notifySubscriber(subscriber);
    }

    @Override
    public boolean registerSubscriber(Subscriber subscriber) {
        if (subscriber == null)
            throw new NullPointerException("Subscriber should not null");
        else if (!subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeSubscriber(Subscriber subscriber) {
        if (subscriber == null)
            throw new NullPointerException("Subscriber should not null");
        else if (subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
            return true;
        }
        return false;
    }

    @Override
    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    @Override
    public void notifySubscribers() {
        if (getTopicValue() == null) {
            throw new NullPointerException("Property should not null");
        } else {
            for (Subscriber subscriber : subscribers) {
                subscriber.updateSubscriber(getTopicValue());
            }
        }
    }

    @Override
    public void notifySubscriber(Subscriber subscriber) {
        if (getTopicValue() == null) {
            throw new NullPointerException("Property should not null");
        } else if (subscriber == null)
            throw new NullPointerException("Subscriber should not null");
        else if (subscribers.contains(subscriber)) {
            subscriber.updateSubscriber(getTopicValue());
        }
    }
}