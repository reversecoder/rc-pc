package com.rc.designpattern.pattern.creational.abstractfactory;

import android.graphics.Canvas;
import android.view.View;

import com.rc.designpattern.pattern.behavioural.state.ShapeType;
import com.rc.designpattern.pattern.structural.bridge.Property;

import java.io.Serializable;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public interface Shape extends Serializable {

    ShapeType getShapeType();

    Property getShapeProperty();

    void setShapeProperty(Property shapeProperty);

    Shape getShape();

    View getShapeView();

    void drawShape(Canvas canvas);

    void refreshView();

//    void drag();
//    void drop();
//    void moveTo(int x, int y);
//    void moveBy(int x, int y);
//    boolean isInsideBounds(int x, int y);
}