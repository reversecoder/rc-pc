package com.rc.designpattern.pattern.behavioural.command;

import android.util.Log;

import com.rc.designpattern.pattern.behavioural.iterator.TopicIteratorManager;
import com.rc.designpattern.pattern.behavioural.memento.CareTaker;
import com.rc.designpattern.pattern.behavioural.memento.GenericMemento;
import com.rc.designpattern.pattern.behavioural.memento.GenericOriginator;
import com.rc.designpattern.pattern.behavioural.observer.Topic;
import com.rc.designpattern.pattern.behavioural.state.CommandType;
import com.rc.designpattern.pattern.behavioural.state.DecorationType;
import com.rc.designpattern.pattern.creational.abstractfactory.Shape;
import com.rc.designpattern.pattern.structural.decorator.ShapeDecorator;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class UpdateShapeCommand implements Command {

    private String TAG = UpdateShapeCommand.class.getSimpleName();
    private Shape shape;
    private String key;
    private CommandType commandType;
    private MutableVariable oldPropertyValue;
    private MutableVariable newPropertyValue;

    public UpdateShapeCommand(Shape shape, CommandType commandType, MutableVariable oldPropertyValue, MutableVariable newPropertyValue) {
        this.shape = shape;
        this.commandType = commandType;
        this.oldPropertyValue = oldPropertyValue;
        this.newPropertyValue = newPropertyValue;
        this.key = commandType.name() + shape.getShapeProperty().getShapeId();
        Log.d(TAG, TAG + ">>key: " + key);
    }

    @Override
    public void doIt() {
        ShapeDecorator shapeDecoratorDo = new ShapeDecorator(shape, DecorationType.valueOf(commandType.name()), new MutableVariable(oldPropertyValue.getValue()), new MutableVariable(newPropertyValue.getValue()));
        shapeDecoratorDo.refreshView();

//        switch (commandType) {
//            case SHAPE_STATE:
//                ShapeDecorator shapeDecoratorState = new ShapeDecorator(shape, DecorationType.SHAPE_STATE, new MutableVariable(oldPropertyValue.getValue()), new MutableVariable(newPropertyValue.getValue()));
//                shapeDecoratorState.refreshView();
//                break;
//            case SHAPE_BACKGROUND_COLOR:
//                ShapeDecorator shapeDecoratorBackgroundColor = new ShapeDecorator(shape, DecorationType.SHAPE_BACKGROUND_COLOR, new MutableVariable(oldPropertyValue.getValue()), new MutableVariable(newPropertyValue.getValue()));
//                shapeDecoratorBackgroundColor.refreshView();
//                break;
//            case SHAPE_COLOR:
//                ShapeDecorator shapeDecoratorColor = new ShapeDecorator(shape, DecorationType.SHAPE_COLOR, new MutableVariable(oldPropertyValue.getValue()), new MutableVariable(newPropertyValue.getValue()));
//                shapeDecoratorColor.refreshView();
//                break;
//            case SHAPE_RADIUS:
//                ShapeDecorator shapeDecoratorRadius = new ShapeDecorator(shape, DecorationType.SHAPE_RADIUS, new MutableVariable(oldPropertyValue.getValue()), new MutableVariable(newPropertyValue.getValue()));
//                shapeDecoratorRadius.refreshView();
//                break;
//            case SHAPE_WIDTH:
//                ShapeDecorator shapeDecoratorWidth = new ShapeDecorator(shape, DecorationType.SHAPE_WIDTH, new MutableVariable(oldPropertyValue.getValue()), new MutableVariable(newPropertyValue.getValue()));
//                shapeDecoratorWidth.refreshView();
//                break;
//            case SHAPE_HEIGHT:
//                ShapeDecorator shapeDecoratorHeight = new ShapeDecorator(shape, DecorationType.SHAPE_HEIGHT, new MutableVariable(oldPropertyValue.getValue()), new MutableVariable(newPropertyValue.getValue()));
//                shapeDecoratorHeight.refreshView();
//                break;
//        }

        // Notify Observer for shape do
        Topic<Shape> topic = TopicIteratorManager.getInstance().getTopic(AddShapeCommand.class.getSimpleName() + shape.getShapeProperty().getShapeId());
        if (topic != null) {
            topic.setValue(shape);
        }

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

        ShapeDecorator shapeDecoratorUndo = new ShapeDecorator(shape, DecorationType.valueOf(commandType.name()), new MutableVariable(newPropertyValue.getValue()), new MutableVariable(oldPropertyValue.getValue()));
        shapeDecoratorUndo.refreshView();

//        switch (commandType) {
//            case SHAPE_STATE:
//                ShapeDecorator shapeDecoratorState = new ShapeDecorator(shape, DecorationType.SHAPE_STATE, new MutableVariable(newPropertyValue.getValue()), new MutableVariable(oldPropertyValue.getValue()));
//                shapeDecoratorState.refreshView();
//                break;
//            case SHAPE_BACKGROUND_COLOR:
//                ShapeDecorator shapeDecoratorBackgroundColor = new ShapeDecorator(shape, DecorationType.SHAPE_BACKGROUND_COLOR, new MutableVariable(newPropertyValue.getValue()), new MutableVariable(oldPropertyValue.getValue()));
//                shapeDecoratorBackgroundColor.refreshView();
//                break;
//            case SHAPE_COLOR:
//                ShapeDecorator shapeDecoratorColor = new ShapeDecorator(shape, DecorationType.SHAPE_COLOR, new MutableVariable(newPropertyValue.getValue()), new MutableVariable(oldPropertyValue.getValue()));
//                shapeDecoratorColor.refreshView();
//                break;
//            case SHAPE_RADIUS:
//                ShapeDecorator shapeDecoratorRadius = new ShapeDecorator(shape, DecorationType.SHAPE_RADIUS, new MutableVariable(newPropertyValue.getValue()), new MutableVariable(oldPropertyValue.getValue()));
//                shapeDecoratorRadius.refreshView();
//                break;
//            case SHAPE_WIDTH:
//                ShapeDecorator shapeDecoratorWidth = new ShapeDecorator(shape, DecorationType.SHAPE_WIDTH, new MutableVariable(newPropertyValue.getValue()), new MutableVariable(oldPropertyValue.getValue()));
//                shapeDecoratorWidth.refreshView();
//                break;
//            case SHAPE_HEIGHT:
//                ShapeDecorator shapeDecoratorHeight = new ShapeDecorator(shape, DecorationType.SHAPE_HEIGHT, new MutableVariable(newPropertyValue.getValue()), new MutableVariable(oldPropertyValue.getValue()));
//                shapeDecoratorHeight.refreshView();
//                break;
//        }

        // Notify Observer for shape undo
        Topic<Shape> topic = TopicIteratorManager.getInstance().getTopic(AddShapeCommand.class.getSimpleName() + shape.getShapeProperty().getShapeId());
        if (topic != null) {
            topic.setValue(mShape);
        }
    }
}