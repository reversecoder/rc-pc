package com.rc.designpattern.pattern.creational.builder;

import android.graphics.Paint;

import com.rc.designpattern.pattern.behavioural.state.StateType;
import com.rc.designpattern.pattern.structural.bridge.CircleProperty;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class CirclePropertyBuilder {

    private int shapeId;
    private int shapeX;
    private int shapeY;
    private int shapeRadius;
    private Paint shapePaint;
    private int shapeColor, shapeBackgroundColor;
    private StateType stateType;

    public CirclePropertyBuilder setShapeId(int shapeId) {
        this.shapeId = shapeId;
        return this;
    }

    public CirclePropertyBuilder setShapeX(int shapeX) {
        this.shapeX = shapeX;
        return this;
    }

    public CirclePropertyBuilder setShapeY(int shapeY) {
        this.shapeY = shapeY;
        return this;
    }

    public CirclePropertyBuilder setShapeRadius(int shapeRadius) {
        this.shapeRadius = shapeRadius;
        return this;
    }

    public CirclePropertyBuilder setShapePaint(Paint shapePaint) {
        this.shapePaint = shapePaint;
        return this;
    }

    public CirclePropertyBuilder setShapeColor(int shapeColor) {
        this.shapeColor = shapeColor;
        return this;
    }

    public CirclePropertyBuilder setShapeBackgroundColor(int shapeBackgroundColor) {
        this.shapeBackgroundColor = shapeBackgroundColor;
        return this;
    }

    public CirclePropertyBuilder setStateType(StateType stateType) {
        this.stateType = stateType;
        return this;
    }

    public CircleProperty createCircleProperty() {
        return new CircleProperty(shapeX, shapeY, shapeRadius);
    }
}