package com.rc.designpattern.pattern.creational.singleton;

import android.content.Context;

import com.rc.designpattern.pattern.behavioural.state.ShapeType;
import com.rc.designpattern.pattern.creational.prototype.ShapeStore;
import com.rc.designpattern.pattern.structural.composite.Circle;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class ShapeManager {

    private volatile static ShapeManager shapeManager;
    private Context context;
    private ShapeStore shapeStore;

    private ShapeManager(Context context) {
        this.context = context;
        this.shapeStore = new ShapeStore(context);
    }

    public static ShapeManager getInstance(Context context) {
        if (shapeManager == null) {
            // To make thread safe
            synchronized (ShapeManager.class) {
                // check again as multiple threads
                // can reach above step
                if (shapeManager == null)
                    shapeManager = new ShapeManager(context);
            }
        }
        return shapeManager;
    }

    public Circle getCircleShape() {
        return (Circle) shapeStore.getShape(context, ShapeType.CIRCLE);
    }

    public void destroyObject() {
        shapeManager = null;
        shapeStore = null;
    }
}