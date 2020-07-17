package com.rc.designpattern.pattern.structural.facade;

import android.content.Context;

import com.rc.designpattern.pattern.creational.builder.CirclePropertyBuilder;
import com.rc.designpattern.pattern.creational.builder.CompoundPropertyBuilder;
import com.rc.designpattern.pattern.structural.bridge.CircleProperty;
import com.rc.designpattern.pattern.structural.bridge.CompoundProperty;
import com.rc.designpattern.pattern.creational.abstractfactory.Shape;
import com.rc.designpattern.util.RandomManager;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class PropertyKeeper {

    private Context context;

    public PropertyKeeper(Context context) {
        this.context = context;
    }

    public CircleProperty getCircleProperty() {
        return new CirclePropertyBuilder()
                .setShapeX(RandomManager.getRandom(100, 500))
                .setShapeY(RandomManager.getRandom(100, 800))
                .setShapeRadius(RandomManager.getRandom(50, 100))
                .createCircleProperty();
    }

    public CompoundProperty getCompoundProperty(Shape... components) {
        return new CompoundPropertyBuilder()
                .setComponents(components)
                .createCompoundProperty();
    }
}