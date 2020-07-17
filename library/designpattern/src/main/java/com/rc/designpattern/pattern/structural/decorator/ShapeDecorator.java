package com.rc.designpattern.pattern.structural.decorator;

import com.rc.designpattern.pattern.behavioural.command.MutableVariable;
import com.rc.designpattern.pattern.behavioural.state.DecorationType;
import com.rc.designpattern.pattern.behavioural.state.StateType;
import com.rc.designpattern.pattern.creational.abstractfactory.Shape;
import com.rc.designpattern.pattern.structural.bridge.CircleProperty;
import com.rc.designpattern.pattern.structural.bridge.CompoundProperty;
import com.rc.designpattern.pattern.structural.composite.CompoundShape;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class ShapeDecorator extends Decorator {

    public ShapeDecorator(Shape decoratedShape, DecorationType decorationType, MutableVariable oldPropertyValue, MutableVariable newPropertyValue) {
        super(decoratedShape);
        updateProperty(decorationType, oldPropertyValue, newPropertyValue);
    }

    private void updateProperty(DecorationType decorationType, MutableVariable oldProperty, MutableVariable newProperty) {
        switch (decorationType) {
            case SHAPE_X:
                getDecoratedShape().getShapeProperty().setShapeX((int) newProperty.getValue());
                break;
            case SHAPE_Y:
                getDecoratedShape().getShapeProperty().setShapeY((int) newProperty.getValue());
                break;
            case SHAPE_RADIUS:
                int radius = (int) newProperty.getValue();
                CircleProperty circleProperty = ((CircleProperty) ((CompoundProperty) ((CompoundShape) getDecoratedShape()).getShapeProperty()).getChildren().get(0).getShapeProperty());
                circleProperty.setShapeRadius(radius);
                ((CompoundShape) getDecoratedShape()).resizeShape((radius * 2), (radius * 2), ((CompoundShape) getDecoratedShape()).getLeft(), ((CompoundShape) getDecoratedShape()).getTop());
                break;
            case SHAPE_WIDTH:
                getDecoratedShape().getShapeProperty().setShapeWidth((int) newProperty.getValue());
                ((CompoundShape) getDecoratedShape()).resizeShape((int) newProperty.getValue(), getDecoratedShape().getShapeProperty().getShapeHeight(), ((CompoundShape) getDecoratedShape()).getLeft(), ((CompoundShape) getDecoratedShape()).getTop());
                break;
            case SHAPE_HEIGHT:
                getDecoratedShape().getShapeProperty().setShapeHeight((int) newProperty.getValue());
                ((CompoundShape) getDecoratedShape()).resizeShape(getDecoratedShape().getShapeProperty().getShapeWidth(), (int) newProperty.getValue(), ((CompoundShape) getDecoratedShape()).getLeft(), ((CompoundShape) getDecoratedShape()).getTop());
                break;
            case SHAPE_BACKGROUND_COLOR:
                getDecoratedShape().getShapeProperty().setShapeBackgroundColor((int) newProperty.getValue());
                break;
            case SHAPE_COLOR:
                getDecoratedShape().getShapeProperty().setShapeColor((int) newProperty.getValue());
                break;
            case SHAPE_STATE:
                getDecoratedShape().getShapeProperty().setStateType((StateType) newProperty.getValue());
                break;
        }
    }
}