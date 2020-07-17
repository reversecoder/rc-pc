package com.rc.designpattern.pattern.structural.composite;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rc.designpattern.R;
import com.rc.designpattern.gesture.TouchGestureDetector;
import com.rc.designpattern.pattern.behavioural.command.CommandExecutor;
import com.rc.designpattern.pattern.behavioural.command.MutableVariable;
import com.rc.designpattern.pattern.behavioural.command.UpdateShapeCommand;
import com.rc.designpattern.pattern.behavioural.observer.Subscriber;
import com.rc.designpattern.pattern.behavioural.state.CommandType;
import com.rc.designpattern.pattern.behavioural.state.DirectionType;
import com.rc.designpattern.pattern.behavioural.state.ShapeType;
import com.rc.designpattern.pattern.behavioural.state.StateType;
import com.rc.designpattern.pattern.creational.abstractfactory.Shape;
import com.rc.designpattern.pattern.structural.bridge.CircleProperty;
import com.rc.designpattern.pattern.structural.bridge.CompoundProperty;
import com.rc.designpattern.pattern.structural.bridge.Property;
import com.rc.designpattern.pattern.structural.facade.PropertyKeeper;
import com.rc.designpattern.util.CustomViewManager;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class CompoundShape extends ViewGroup implements Shape, Subscriber<Shape> {

    private String TAG = CompoundShape.class.getSimpleName();
    private int centerX = 0;
    private int centerY = 0;
    private View editableView;
    private CompoundProperty property;

    public CompoundShape(Context context, Shape... components) {
        super(context);

        property = prepareProperty(components);
        setId(property.getShapeId());
        setWillNotDraw(false);
        // Add component views
        add(components);
        // Fixed screen size
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;

        // Draw views
        drawViews();
    }

    public void add(Shape component) {
        property.add(component);
    }

    public void add(Shape... components) {
        property.add(components);
    }

    public void remove(Shape component) {
        property.remove(component);
    }

    public void remove(Shape... components) {
        property.remove(components);
    }

    public void clear() {
        property.clear();
        removeAllViews();
    }

    private CompoundProperty prepareProperty(Shape... components) {
        return new PropertyKeeper(getContext()).getCompoundProperty(components);
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.COMPOSITE;
    }

    @Override
    public Property getShapeProperty() {
        return property;
    }

    @Override
    public void setShapeProperty(Property shapeProperty) {
        this.property = (CompoundProperty) shapeProperty;
    }

    @Override
    public View getShapeView() {
        return this;
    }

    @Override
    public Shape getShape() {
        return this;
    }

    @Override
    public void refreshView() {
        for (Shape child : property.getChildren()) {
            child.refreshView();
        }

        if (editableView != null) {
            editableView.setVisibility((getShapeProperty().isShapeSelected()) ? VISIBLE : GONE);
        }

        invalidate();
    }

    @Override
    public void drawShape(Canvas canvas) {
        setBackgroundColor(property.getShapeBackgroundColor());
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Optimized measurement
        int width = CustomViewManager.reconcileSize(MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec);
        int height = CustomViewManager.reconcileSize(MeasureSpec.getSize(heightMeasureSpec), heightMeasureSpec);
        Log.d(TAG, "onMeasure>> width: " + width + " height: " + height);
        int size = Math.min(width, height);
        Log.d(TAG, "onMeasure>> size: " + size);
        // measure children size
        centerX = centerY = size / 2;
        Log.d(TAG, "onMeasure>> centerX=centerY: " + centerX);
        for (Shape child : property.getChildren()) {
//            ((View) child).measure(size, size);
            ((View) child).measure(child.getShapeProperty().getShapeWidth(), child.getShapeProperty().getShapeHeight());
        }
        if (editableView != null) {
            editableView.measure(size, size);
        }
        // measure parent size
//        setMeasuredDimension(getShapeWidth() + 50, getShapeHeight() + 50);
        setMeasuredDimension(size, size);
    }

    private void drawViews() {
        for (Shape child : property.getChildren()) {
            removeView(child.getShapeView());
            addView(child.getShapeView());
        }

        editableView = CustomViewManager.getChildView(getContext(), this, R.layout.layout_editable_border_controller);
        editableView.setVisibility((getShapeProperty().isShapeSelected()) ? VISIBLE : GONE);
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
//        for(int i = 0 ; i < getChildCount() ; i++){
//            getChildAt(i).layout(l, t, r, b);
//            View view = getChildAt(i);
//            Log.d(TAG, "onLayout()>>view>>" + view.getClass().getSimpleName() + " width: " + view.getLayoutParams().width + " height: " + view.getLayoutParams().height);
//
//            if(view.getVisibility() != GONE && view instanceof FrameLayout){
//                view.layout(l, t, r, b);
//            }
//        }

        for (Shape child : property.getChildren()) {
            int left = centerX - child.getShapeProperty().getShapeWidth() / 2;
            int top = centerY - child.getShapeProperty().getShapeHeight() / 2;
            int right = left + child.getShapeProperty().getShapeWidth();
            int bottom = top + child.getShapeProperty().getShapeHeight();

            View childView = (View) child;
            if (childView.getVisibility() != GONE) {
                Log.d(TAG, "onLayout()>>child>>" + child.getClass().getSimpleName() + " left: " + left + " top: " + top + " right: " + right + " bottom: " + bottom);
                childView.layout(left, top, right, bottom);
            }
        }

        if (editableView != null && editableView.getVisibility() != GONE) {
            int left = centerX - getMeasuredWidth() / 2;
            int top = centerY - getMeasuredHeight() / 2;
            int right = left + getMeasuredWidth();
            int bottom = top + getMeasuredHeight();

//            editableView.layout(
//                    14
//                    , 14
//                    , 186
//                    , 186
//            );
            editableView.layout(
                    left
                    , top
                    , right
                    , bottom
            );
        }

        Log.d(TAG, "onLayout()>>Inside onLayout()");
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        drawShape(canvas);
    }

    /***********************
     * Touch events
     *************************/
    private DirectionType dragDirection;
    private int lastX;
    private int lastY;
    private int screenWidth;
    private int screenHeight;
    private int oriLeft;
    private int oriRight;
    private int oriTop;
    private int oriBottom;
    private int touchAreaLength = 60;
    private int minHeight = 150;
    private int minWidth = 150;
    private boolean mFixedSize = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchGestureDetector.onTouchEvent(event);
        if (getShapeProperty().isShapeSelected()) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    oriLeft = getLeft();
                    oriRight = getRight();
                    oriTop = getTop();
                    oriBottom = getBottom();

                    lastY = (int) event.getRawY();
                    lastX = (int) event.getRawX();

                    dragDirection = getDirection((int) event.getX(), (int) event.getY());
                    Log.d(TAG, "onTouchEvent(MotionEvent.ACTION_DOWN): lastX: " + lastX + " lastY: " + lastY);
                    Log.d(TAG, "onTouchEvent(MotionEvent.ACTION_DOWN): oriLeft: " + oriLeft + " oriRight: " + oriRight + " oriTop: " + oriTop + " oriBottom: " + oriBottom);
                    Log.d(TAG, "onTouchEvent(MotionEvent.ACTION_DOWN): dragDirection: " + dragDirection);
                    Toast.makeText(getContext(), "" + dragDirection, Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_UP:
                    //      Log.d(TAG, "onTouchEvent: up");
//                spotLT = false;
//                spotT = false;
//                spotRT = false;
//                spotR = false;
//                spotRB = false;
//                spotB = false;
//                spotLB = false;
//                spotL = false;
//                requestLayout();
                    // invalidate();
                    break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.d(TAG, "onTouchEvent: cancel");
//                spotL = false;
//                spotT = false;
//                spotR = false;
//                spotB = false;
//                invalidate();
//                break;
                case MotionEvent.ACTION_MOVE:
                    // Log.d(TAG, "onTouchEvent: move");
                    int tempRawX = (int) event.getRawX();
                    int tempRawY = (int) event.getRawY();

                    int dx = tempRawX - lastX;
                    int dy = tempRawY - lastY;
                    lastX = tempRawX;
                    lastY = tempRawY;
                    Log.d(TAG, "onTouchEvent(MotionEvent.ACTION_MOVE): dx= " + dx + " dy= " + dy);

                    if (dragDirection != null) {
                        switch (dragDirection) {
                            case LEFT:
                                left(dx);
                                break;
                            case RIGHT:
                                right(dx);
                                break;
                            case BOTTOM:
                                bottom(dy);
                                break;
                            case TOP:
                                top(dy);
                                break;
                            case CENTER:
                                center(dx, dy);
                                break;
                            case LEFT_BOTTOM:
                                left(dx);
                                bottom(dy);
                                break;
                            case LEFT_TOP:
                                left(dx);
                                top(dy);
                                break;
                            case RIGHT_BOTTOM:
                                right(dx);
                                bottom(dy);
                                break;
                            case RIGHT_TOP:
                                right(dx);
                                top(dy);
                                break;
                        }

                        int finalWidth = oriRight - oriLeft;
                        int finalHeight = oriBottom - oriTop;

                        // Resize shape
                        Log.d(TAG, "onTouchEvent(MotionEvent.ACTION_MOVE): finalWidth= " + finalWidth + " finalHeight= " + finalHeight);
                        Log.d(TAG, "onTouchEvent(MotionEvent.ACTION_MOVE): marginLeft= " + oriLeft + " marginTop= " + oriTop);
                        resizeShape(finalWidth, finalHeight, oriLeft, oriTop);
                    }
                    break;
            }

            return true;
        }
        return true;
    }

    public void resizeShape(int width, int height, int marginLeft, int marginTop) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
        lp.setMargins(marginLeft, marginTop, 0, 0);

        for (Shape child : property.getChildren()) {
            if (child instanceof Circle) {
                int circleRadius = Math.min(width / 2, height / 2);
                Log.d(TAG, "onTouchEvent(MotionEvent.ACTION_MOVE): circleRadius= " + circleRadius);
                ((CircleProperty) ((Circle) child).getShapeProperty()).setShapeRadius(circleRadius);
            }
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(lp);
        if (editableView != null) {
            editableView.setLayoutParams(layoutParams);
        }
        setLayoutParams(lp);
    }

    TouchGestureDetector touchGestureDetector = new TouchGestureDetector(new TouchGestureDetector.TouchGestureListener() {
        @Override
        public void onPress(MotionEvent motionEvent) {

        }

        @Override
        public void onTap(MotionEvent motionEvent) {

        }

        @Override
        public void onDrag(MotionEvent motionEvent) {

        }

        @Override
        public void onMove(MotionEvent motionEvent) {

        }

        @Override
        public void onRelease(MotionEvent motionEvent) {

        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            if (!getShapeProperty().isShapeSelected()) {
                Log.d(TAG, "touchGestureDetector>>onLongPress: ");
                CustomViewManager.doVibrate(getContext(), 100);

                UpdateShapeCommand previousState = new UpdateShapeCommand(getShape(), CommandType.SHAPE_STATE, new MutableVariable(StateType.UNSELECTED), new MutableVariable(StateType.SELECTED));
                CommandExecutor.getInstance().executeCommand(previousState);
            }
        }

        @Override
        public void onMultiTap(MotionEvent motionEvent, int clicks) {

        }
    });

    private void center(int dx, int dy) {
        int left = getLeft() + dx;
        int top = getTop() + dy;
        int right = getRight() + dx;
        int bottom = getBottom() + dy;

        if (left < 0) {
            left = 0;
            right = left + getWidth();
        }
        if (right > screenWidth) {
            right = screenWidth;
            left = right - getWidth();
        }
        if (top < 0) {
            top = 0;
            bottom = top + getHeight();
        }
        if (bottom > screenHeight) {
            bottom = screenHeight;
            top = bottom - getHeight();
        }

        oriLeft = left;
        oriTop = top;
        oriRight = right;
        oriBottom = bottom;
    }

    private void top(int dy) {
        oriTop += dy;
        if (oriTop < 0) {
            oriTop = 0;
        }
        if (oriBottom - oriTop < minHeight) {
            oriTop = oriBottom - minHeight;
        }
    }

    private void bottom(int dy) {

        oriBottom += dy;
        if (oriBottom > screenHeight) {
            oriBottom = screenHeight;
        }
        if (oriBottom - oriTop < minHeight) {
            oriBottom = minHeight + oriTop;
        }
    }

    private void right(int dx) {
        oriRight += dx;
        if (oriRight > screenWidth) {
            oriRight = screenWidth;
        }
        if (oriRight - oriLeft < minWidth) {
            oriRight = oriLeft + minWidth;
        }
    }

    private void left(int dx) {
        oriLeft += dx;
        if (oriLeft < 0) {
            oriLeft = 0;
        }
        if (oriRight - oriLeft < minWidth) {
            oriLeft = oriRight - minWidth;
        }
    }

    private DirectionType getDirection(int x, int y) {
        int left = getLeft();
        int right = getRight();
        int bottom = getBottom();
        int top = getTop();

        if (x < touchAreaLength && y < touchAreaLength) {
//            spotLT = true;
            return DirectionType.LEFT_TOP;
        }
        if (y < touchAreaLength && right - left - x < touchAreaLength) {
//            spotRT = true;
            return DirectionType.RIGHT_TOP;
        }
        if (x < touchAreaLength && bottom - top - y < touchAreaLength) {
//            spotLB = true;
            return DirectionType.LEFT_BOTTOM;
        }
        if (right - left - x < touchAreaLength && bottom - top - y < touchAreaLength) {
//            spotRB = true;
            return DirectionType.RIGHT_BOTTOM;
        }
        if (mFixedSize) {
            return DirectionType.CENTER;
        }

        if (x < touchAreaLength) {
//            spotL = true;
            requestLayout();
            return DirectionType.LEFT;
        }
        if (y < touchAreaLength) {
//            spotT = true;
            requestLayout();
            return DirectionType.TOP;
        }
        if (right - left - x < touchAreaLength) {
//            spotR = true;
            requestLayout();
            return DirectionType.RIGHT;
        }
        if (bottom - top - y < touchAreaLength) {
//            spotB = true;
            requestLayout();
            return DirectionType.BOTTOM;
        }
        return DirectionType.CENTER;
    }

    @Override
    public void updateSubscriber(Shape item) {
//        Toast.makeText(getContext(), getClass().getSimpleName() + item.getShapeProperty().getShapeId(), Toast.LENGTH_SHORT).show();
        setShapeProperty(item.getShapeProperty());
        refreshView();
        Log.d("AddShapeCommand", "AddShapeCommand>>background after: " + property.getShapeBackgroundColor());
    }
}