package com.rc.designpattern.pattern.behavioural.iterator;

import com.rc.designpattern.pattern.behavioural.observer.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class TopicIterator implements Iterator<Topic> {

    private List<Topic> topics ;
    private int position;

    public TopicIterator(List<Topic> topics) {
        this.topics = topics;
        position = 0;
    }

    @Override
    public void reset() {
        position = 0;
    }

    @Override
    public Topic next() {
        return topics.get(position++);
    }

    @Override
    public Topic currentItem() {
        return topics.get(position);
    }

    @Override
    public boolean hasNext() {
        if (position >= topics.size())
            return false;
        return true;
    }
}