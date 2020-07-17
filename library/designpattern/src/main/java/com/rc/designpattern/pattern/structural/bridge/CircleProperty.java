package com.rc.designpattern.pattern.structural.bridge;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.rc.designpattern.pattern.behavioural.state.StateType;
import com.rc.designpattern.util.RandomManager;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class CircleProperty implements Property {

    private int shapeId;
    private int shapeX, shapeY;
    private int shapeRadius;
    private Paint shapePaint;
    private int shapeColor, shapeBackgroundColor;
    private StateType stateType;

    public CircleProperty(int shapeX, int shapeY, int shapeRadius) {
        this.shapeId = RandomManager.getRandom(1, 1000);
        this.shapeX = shapeX;
        this.shapeY = shapeY;
        this.shapeRadius = shapeRadius;
        this.shapePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.shapeColor = RandomManager.getRandomColor();
        this.shapeBackgroundColor = RandomManager.getRandomColor();
        this.stateType = StateType.UNSELECTED;
    }

    @Override
    public int getShapeId() {
        return shapeId;
    }

    @Override
    public void setShapeId(int shapeId) {
        this.shapeId = shapeId;
    }

    @Override
    public int getShapeX() {
        return shapeX;
    }

    @Override
    public void setShapeX(int shapeX) {
        this.shapeX = shapeX;
    }

    @Override
    public int getShapeY() {
        return shapeY;
    }

    @Override
    public void setShapeY(int shapeY) {
        this.shapeY = shapeY;
    }

    @Override
    public int getShapeWidth() {
        return shapeRadius * 2;
    }

    @Override
    public void setShapeWidth(int shapeWidth) {
        this.shapeRadius = shapeWidth / 2;
    }

    @Override
    public int getShapeHeight() {
        return shapeRadius * 2;
    }

    @Override
    public void setShapeHeight(int shapeHeight) {
        this.shapeRadius = shapeHeight / 2;
    }

    @Override
    public StateType getStateType() {
        return stateType;
    }

    @Override
    public void setStateType(StateType stateType) {
        this.stateType = stateType;
    }

    @Override
    public int getShapeColor() {
        return shapeColor;
    }

    @Override
    public void setShapeColor(int shapeColor) {
        this.shapeColor = shapeColor;
    }

    @Override
    public int getShapeBackgroundColor() {
        return shapeBackgroundColor;
    }

    @Override
    public void setShapeBackgroundColor(int shapeBackgroundColor) {
        this.shapeBackgroundColor = shapeBackgroundColor;
    }

    @Override
    public Paint getShapePaint() {
        shapePaint.setColor(getShapeColor());
        shapePaint.setStyle(Paint.Style.STROKE);
        shapePaint.setStrokeWidth(5f);
        if (isShapeSelected()) {
            shapePaint.setPathEffect(new DashPathEffect(new float[]{5, 10}, 0));
        } else {
            shapePaint.setPathEffect(null);
        }
        return shapePaint;
    }

    @Override
    public void setShapePaint(Paint shapePaint) {
        this.shapePaint = shapePaint;
    }

    @Override
    public boolean isShapeSelected() {
        return (stateType == StateType.SELECTED);
    }

    public int getShapeRadius() {
        return shapeRadius;
    }

    public void setShapeRadius(int shapeRadius) {
        this.shapeRadius = shapeRadius;
    }

    @Override
    public String toString() {
        return "CircleProperty{" +
                "shapeId=" + shapeId +
                ", shapeX=" + shapeX +
                ", shapeY=" + shapeY +
                ", shapeRadius=" + shapeRadius +
                ", shapePaint=" + shapePaint +
                ", shapeColor=" + shapeColor +
                ", shapeState=" + stateType +
                '}';
    }
}