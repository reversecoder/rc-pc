package com.rc.designpattern.pattern.structural.bridge;

import android.graphics.Paint;

import com.rc.designpattern.pattern.behavioural.state.StateType;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public interface Property {

    int getShapeId();

    void setShapeId(int shapeId);

    int getShapeX();

    void setShapeX(int shapeX);

    int getShapeY();

    void setShapeY(int shapeY);

    int getShapeWidth();

    void setShapeWidth(int shapeWidth);

    int getShapeHeight();

    void setShapeHeight(int shapeHeight);

    StateType getStateType();

    void setStateType(StateType stateType);

    int getShapeColor();

    void setShapeColor(int shapeColor);

    int getShapeBackgroundColor();

    void setShapeBackgroundColor(int shapeBackgroundColor);

    Paint getShapePaint();

    void setShapePaint(Paint shapePaint);

    boolean isShapeSelected();
}