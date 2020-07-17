package com.rc.designpattern.pattern.behavioural.command;

import android.util.Log;
import android.view.ViewGroup;

import com.rc.designpattern.pattern.behavioural.memento.CareTaker;
import com.rc.designpattern.pattern.behavioural.memento.GenericMemento;
import com.rc.designpattern.pattern.behavioural.memento.GenericOriginator;
import com.rc.designpattern.pattern.creational.abstractfactory.Shape;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class AddShapeCommand implements Command {

    private String TAG = AddShapeCommand.class.getSimpleName();
    private ViewGroup parentView;
    private Shape shape;
    private String key;

    public AddShapeCommand(ViewGroup parentView, Shape shape) {
        this.parentView = parentView;
        this.shape = shape;
        this.key = TAG + shape.getShapeProperty().getShapeId();
        Log.d(TAG, TAG + ">>key: " + key);
    }

    @Override
    public void doIt() {
        parentView.addView(shape.getShapeView());

        // Memento
        GenericOriginator<Shape> mOriginator = new GenericOriginator<>(shape);
        GenericMemento<Shape> currentMemento = (GenericMemento<Shape>) mOriginator.saveToMemento();
        CareTaker.getInstance().add(key, currentMemento);
    }

    @Override
    public String whoAmI() {
        return key;
    }

    @Override
    public void undoIt() {
        // Memento
        GenericMemento mMemento = CareTaker.getInstance().get(key);
        Shape mShape = (Shape) mMemento.getState();
        parentView.removeView(mShape.getShapeView());
    }
}