package com.rc.designpattern.pattern.structural.decorator;

import android.graphics.Canvas;
import android.view.View;

import com.rc.designpattern.pattern.behavioural.state.ShapeType;
import com.rc.designpattern.pattern.creational.abstractfactory.Shape;
import com.rc.designpattern.pattern.structural.bridge.Property;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public abstract class Decorator implements Shape {

    private Shape decoratedShape;

    public Decorator(Shape decoratedShape) {
        super();
        this.decoratedShape = decoratedShape;
    }

    public Shape getDecoratedShape() {
        return decoratedShape;
    }

    @Override
    public ShapeType getShapeType() {
        return getDecoratedShape().getShapeType();
    }

    @Override
    public Property getShapeProperty() {
        return getDecoratedShape().getShapeProperty();
    }

    @Override
    public void setShapeProperty(Property shapeProperty) {
        getDecoratedShape().setShapeProperty(shapeProperty);
    }

    @Override
    public Shape getShape() {
        return getDecoratedShape();
    }

    @Override
    public View getShapeView() {
        return getDecoratedShape().getShapeView();
    }

    @Override
    public void drawShape(Canvas canvas) {
        getDecoratedShape().drawShape(canvas);
    }

    @Override
    public void refreshView() {
        getDecoratedShape().refreshView();
    }
}