package com.rc.designpattern.pattern.creational.prototype;

import android.content.Context;

import com.rc.designpattern.pattern.behavioural.state.ShapeType;
import com.rc.designpattern.pattern.creational.factory.ShapeFactory;
import com.rc.designpattern.pattern.structural.bridge.Property;
import com.rc.designpattern.pattern.creational.abstractfactory.Shape;
import com.rc.designpattern.pattern.structural.facade.PropertyKeeper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class ShapeStore {

    private Context context;
    private Map<String, Shape> shapeMap = new HashMap<String, Shape>();
    private ShapeFactory shapeFactory;
    private PropertyKeeper propertyKeeper;

    public ShapeStore(Context context) {
        this.context = context;
        shapeFactory = new ShapeFactory();
        propertyKeeper = new PropertyKeeper(context);

//        loadShape();
    }

//    private void loadShape() {
//        shapeMap.put(ShapeType.CIRCLE.name(), prepareShape(context, ShapeType.CIRCLE));
//    }
//
//    public Shape getShape(ShapeType shapeType) {
//        if (shapeType != ShapeType.COMPOSITE) {
//            Shape shape = shapeMap.get(shapeType.name());
//            if (shape != null) {;
//                Property property = shape.getShapeProperty();
//                switch (shape.getShapeType()) {
//                    case CIRCLE:
//                        CircleProperty circleProperty = (CircleProperty) property;
//                        circleProperty.setShapeId(RandomManager.getRandom(5));
//                        shape.setShapeProperty(circleProperty);
//                        shape.refreshView();
//                        break;
//                }
//                return shape;
//            }
//        }
//        return null;
//    }
//
//    public void storeShape(ShapeType shapeType, Shape shape) {
//        if (!shapeMap.containsKey(shapeType.name())) {
//            shapeMap.put(shapeType.name(), shape);
//        }
//    }

    public Shape getShape(Context context, ShapeType shapeType) {
        Property property = null;
        Shape shape = null;
        // Facade implementation
        if (shapeType == ShapeType.CIRCLE) {
            property = propertyKeeper.getCircleProperty();
        }

        // Factory implementation
        if (property != null) {
            shape = shapeFactory.createShape(context, shapeType, property);
        }
        return shape;
    }
}