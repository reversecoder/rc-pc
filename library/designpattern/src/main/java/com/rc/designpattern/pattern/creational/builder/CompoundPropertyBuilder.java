package com.rc.designpattern.pattern.creational.builder;

import com.rc.designpattern.pattern.structural.bridge.CompoundProperty;
import com.rc.designpattern.pattern.creational.abstractfactory.Shape;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class CompoundPropertyBuilder {
    private Shape[] components;

    public CompoundPropertyBuilder setComponents(Shape... components) {
        this.components = components;
        return this;
    }

    public CompoundProperty createCompoundProperty() {
        return new CompoundProperty(components);
    }
}