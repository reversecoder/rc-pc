package com.rc.designpattern.pattern.structural.bridge;

import android.graphics.Paint;

import com.rc.designpattern.pattern.behavioural.state.StateType;
import com.rc.designpattern.pattern.creational.abstractfactory.Shape;
import com.rc.designpattern.util.RandomManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class CompoundProperty implements Property {

    private int shapeId;
    private List<Shape> children = new ArrayList<>();

    public CompoundProperty(Shape... components) {
        this.shapeId = RandomManager.getRandom(1, 1000);
        add(components);
    }

    public void add(Shape component) {
        children.add(component);
    }

    public void add(Shape... components) {
        for (Shape child : components) {
            add(child);
        }
    }

    public void remove(Shape child) {
        children.remove(child);
    }

    public void remove(Shape... components) {
        children.removeAll(Arrays.asList(components));
    }

    public void clear() {
        children.clear();
    }

    public List<Shape> getChildren() {
        return children;
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
        if (children.size() == 0) {
            return 0;
        }
        int x = children.get(0).getShapeProperty().getShapeX();
        for (Shape child : children) {
            if (child.getShapeProperty().getShapeX() < x) {
                x = child.getShapeProperty().getShapeX();
            }
        }
        return x;
    }

    @Override
    public void setShapeX(int shapeX) {
        if (children.size() > 0) {
            for (Shape child : children) {
                child.getShapeProperty().setShapeX(shapeX);
            }
        }
    }

    @Override
    public int getShapeY() {
        if (children.size() == 0) {
            return 0;
        }
        int y = children.get(0).getShapeProperty().getShapeY();
        for (Shape child : children) {
            if (child.getShapeProperty().getShapeY() < y) {
                y = child.getShapeProperty().getShapeY();
            }
        }
        return y;
    }

    @Override
    public void setShapeY(int shapeY) {
        if (children.size() > 0) {
            for (Shape child : children) {
                child.getShapeProperty().setShapeY(shapeY);
            }
        }
    }

    @Override
    public int getShapeWidth() {
        int maxWidth = 0;
        int x = getShapeX();
        for (Shape child : children) {
            int childsRelativeX = child.getShapeProperty().getShapeX() - x;
            int childWidth = childsRelativeX + child.getShapeProperty().getShapeWidth();
            if (childWidth > maxWidth) {
                maxWidth = childWidth;
            }
        }
        return maxWidth;
    }

    @Override
    public void setShapeWidth(int shapeWidth) {
        if (children.size() > 0) {
            for (Shape child : children) {
                child.getShapeProperty().setShapeWidth(shapeWidth);
            }
        }
    }

    @Override
    public int getShapeHeight() {
        int maxHeight = 0;
        int y = getShapeY();
        for (Shape child : children) {
            int childsRelativeY = child.getShapeProperty().getShapeY() - y;
            int childHeight = childsRelativeY + child.getShapeProperty().getShapeHeight();
            if (childHeight > maxHeight) {
                maxHeight = childHeight;
            }
        }
        return maxHeight;
    }

    @Override
    public void setShapeHeight(int shapeHeight) {
        if (children.size() > 0) {
            for (Shape child : children) {
                child.getShapeProperty().setShapeHeight(shapeHeight);
            }
        }
    }

    @Override
    public StateType getStateType() {
        if (children.size() > 0) {
            return children.get(0).getShapeProperty().getStateType();
        }
        return null;
    }

    @Override
    public void setStateType(StateType stateType) {
        if (children.size() > 0) {
            for (Shape child : children) {
                child.getShapeProperty().setStateType(stateType);
            }
        }
    }

    @Override
    public int getShapeColor() {
        if (children.size() > 0) {
            return children.get(0).getShapeProperty().getShapeColor();
        }
        return 0;
    }

    @Override
    public void setShapeColor(int shapeColor) {
        if (children.size() > 0) {
            for (Shape child : children) {
                child.getShapeProperty().setShapeColor(shapeColor);
            }
        }
    }

    @Override
    public int getShapeBackgroundColor() {
        if (children.size() > 0) {
            return children.get(0).getShapeProperty().getShapeBackgroundColor();
        }
        return 0;
    }

    @Override
    public void setShapeBackgroundColor(int shapeBackgroundColor) {
        if (children.size() > 0) {
            for (Shape child : children) {
                child.getShapeProperty().setShapeBackgroundColor(shapeBackgroundColor);
            }
        }
    }

    @Override
    public Paint getShapePaint() {
        if (children.size() > 0) {
            return children.get(0).getShapeProperty().getShapePaint();
        }
        return null;
    }

    @Override
    public void setShapePaint(Paint shapePaint) {
        if (children.size() > 0) {
            for (Shape child : children) {
                child.getShapeProperty().setShapePaint(shapePaint);
            }
        }
    }

    @Override
    public boolean isShapeSelected() {
        return (getStateType() == StateType.SELECTED);
    }

    @Override
    public String toString() {
        return "CompoundProperty{" +
                "shapeId=" + shapeId +
                ", shapeState=" + getStateType() +
                ", children=" + children +
                '}';
    }
}