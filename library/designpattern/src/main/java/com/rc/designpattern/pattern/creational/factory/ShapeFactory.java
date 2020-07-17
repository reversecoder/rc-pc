package com.rc.designpattern.pattern.creational.factory;

import android.content.Context;

import com.rc.designpattern.pattern.behavioural.state.ShapeType;
import com.rc.designpattern.pattern.structural.bridge.Property;
import com.rc.designpattern.pattern.structural.composite.Circle;
import com.rc.designpattern.pattern.creational.abstractfactory.Shape;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class ShapeFactory {

    public Shape createShape(Context context, ShapeType type, Property property) {
        Shape shape = null;
        switch (type) {
            case CIRCLE:
                shape = new Circle(context, property);
                break;
        }

        return shape;
    }
}