package com.rc.designpattern.pattern.creational.abstractfactory;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.rc.designpattern.pattern.structural.bridge.Property;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public abstract class ShapeView extends View implements Shape{

//    @Override
//    public boolean isInsideBounds(int x, int y) {
//        return x > getShapeX() && x < (getShapeX() + getShapeWidth()) &&
//                y > getShapeY() && y < (getShapeY() + getShapeHeight());
//    }

    private Property shapeProperty;

    public ShapeView(Context context, Property shapeProperty) {
        super(context);
        this.shapeProperty = shapeProperty;
        setId(shapeProperty.getShapeId());
    }

    @Override
    public Shape getShape() {
        return this;
    }

    @Override
    public Property getShapeProperty() {
        return shapeProperty;
    }

    @Override
    public void setShapeProperty(Property shapeProperty) {
        this.shapeProperty = shapeProperty;
    }

    @Override
    public void refreshView() {
        invalidate();
    }

    @Override
    public View getShapeView() {
        return this;
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawShape(canvas);
    }
}