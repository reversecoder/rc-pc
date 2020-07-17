package com.rc.designpattern.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class CustomViewManager {

    public static View getChildView(Context context, ViewGroup parentView, int childLayoutRes) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View childView = inflater.inflate(childLayoutRes, null);
        parentView.addView(childView);
        return childView;
    }

    public static void doVibrate(Context context, long duration) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibrator.vibrate(duration);
            }
        }
    }

    public static int getRandom(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }

    public static double distance(PointF initialPoint, float finalPointX, float finalPointY) {
        return Math.sqrt(
                Math.pow(Math.abs(finalPointX - initialPoint.x), 2) +
                        Math.pow(Math.abs(finalPointY - initialPoint.y), 2));
    }

    public static double distance(PointF initialPoint, PointF finalPoint) {
        return Math.sqrt(
                Math.pow(Math.abs(finalPoint.x - initialPoint.x), 2) +
                        Math.pow(Math.abs(finalPoint.y - initialPoint.y), 2));
    }

    public static double distance(float initialPointX, float initialPointY, PointF finalPoint) {
        return Math.sqrt(
                Math.pow(Math.abs(finalPoint.x - initialPointX), 2) +
                        Math.pow(Math.abs(finalPoint.y - initialPointY), 2));
    }

    public static double distance(float initialPointX, float initialPointY, float finalPointX, float finalPointY) {
        return Math.sqrt(
                Math.pow(Math.abs(finalPointX - initialPointX), 2) +
                        Math.pow(Math.abs(finalPointY - initialPointY), 2));
    }

    public static void checkNonNullParams(Object param, String paramName) {
        if (param == null) {
            throw new IllegalArgumentException("Parameter \"" + paramName + "\" can't be null.");
        }
    }

    public static int measureDimension(int desiredSize, int measureSpec) {
        int result;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = desiredSize;
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        if (result < desiredSize) {
            Log.d("measureDimension", "The view is too small, the content might get cut");
        }
        return result;
    }

    public static int reconcileSize(int contentSize, int measureSpec) {
        final int mode = View.MeasureSpec.getMode(measureSpec);
        final int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case View.MeasureSpec.EXACTLY:
                return specSize;
            case View.MeasureSpec.AT_MOST:
                if (contentSize < specSize) {
                    return contentSize;
                } else {
                    return specSize;
                }
            case View.MeasureSpec.UNSPECIFIED:
            default:
                return contentSize;
        }
    }

    public static void drawBitmaps(Canvas canvas, int centerX, int centerY, Bitmap bitmap, Paint paint) {
        int x = centerX - bitmap.getWidth() / 2;
        int y = centerY - bitmap.getHeight() / 2;
        canvas.drawBitmap(bitmap, x, y, paint);
    }

    public static void drawCircle(Canvas canvas, int centerX, int centerY, int radius, int padding, Paint paint) {
        canvas.drawCircle(centerX, centerY, (radius > 0 ? radius : (centerX - padding)), paint);
    }

    public static void drawText(Canvas canvas, int centerX, int centerY, String text, TextPaint textPaint) {
        final float textWidth = textPaint.measureText(text);
        final float textX = Math.round(centerX - textWidth * .5f);
        final float textY = Math.round(centerY + getTextHeight(text, textPaint) * .5f);
        canvas.drawText(text, textX, textY, textPaint);
    }

    public static float getTextHeight(String text, TextPaint textPaint) {
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    public static Bitmap convertToBitmap(Drawable drawable, int width, int height) {
        if (drawable != null && width > 0 && height > 0) {
            Bitmap mutableBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mutableBitmap);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            return mutableBitmap;
        }
        return null;
    }

    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

//            if (drawable instanceof ColorDrawable) {
//                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
//            } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    private ArcAnimator animateExpandView(View view, float cx, float cy, float radius,
//                                          int mainViewRadius, int strokeWidth,
//                                          float degree, float animationDegree, long startDelay, long delay) {
//        float angle = (float) Math.toRadians(degree);
//        float stopX = (float) (cx + (radius - mainViewRadius - strokeWidth * 2) * Math.sin(angle));
//        float stopY = (float) (cy - (radius - mainViewRadius - strokeWidth * 2) * Math.cos(angle));
//        ArcAnimator arcAnimator = ArcAnimator.createArcAnimator(view, stopX, stopY, animationDegree, Side.RIGHT);
//        arcAnimator.setDuration(delay);
//        arcAnimator.setStartDelay(startDelay);
//        arcAnimator.setInterpolator(new DecelerateInterpolator());
//        arcAnimator.start();
//        return arcAnimator;
//    }

    public static void scaleViewOnClick(final View view) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", .8f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", .8f);
        set.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                AnimatorSet set = new AnimatorSet();
                ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f);
                ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1f);
                set.playTogether(scaleXAnimator, scaleYAnimator);
                set.setDuration(100);
                set.start();
            }
        });
        set.playTogether(scaleXAnimator, scaleYAnimator);
        set.setDuration(100);
        set.start();
    }
}