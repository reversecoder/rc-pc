package com.meembusoft.postcreator.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.meembusoft.postcreator.R;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class BitmapManager {

    private static String TAG = BitmapManager.class.getSimpleName();

    public enum STAMP_POSITION {LEFT_TOP, LEFT_CENTER, LEFT_BOTTOM, TOP_CENTER, RIGHT_TOP, RIGHT_CENTER, RIGHT_BOTTOM, BOTTOM_CENTER, CENTER}

    public static Bitmap drawTextToBitmap(Context gContext, Bitmap bitmap, String gText, STAMP_POSITION stampPosition) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(resources.getColor(R.color.colorWhite));
        paint.setTypeface(Typeface.createFromAsset(gContext.getAssets(), "fonts/mathilde.otf"));
        paint.setTextSize((int) (24 * scale));
        paint.setShadowLayer(2f, 0f, 2f, resources.getColor(R.color.colorBlack));
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = 0, y = 0;
        switch (stampPosition) {
            case CENTER:
                x = (bitmap.getWidth() - bounds.width()) / 2;
                y = (bitmap.getHeight() + bounds.height()) / 2;
                break;
            case RIGHT_BOTTOM:
                x = (bitmap.getWidth() - bounds.width() - 10);
                y = (bitmap.getHeight() - (bounds.height() / 2) - 5);
                break;
            case LEFT_BOTTOM:
                x = 10;
                y = (bitmap.getHeight() - (bounds.height() / 2) - 5);
                break;
            case BOTTOM_CENTER:
                x = (bitmap.getWidth() - bounds.width()) / 2;
                y = (bitmap.getHeight() - (bounds.height() / 2) - 5);
                break;
            case TOP_CENTER:
                x = (bitmap.getWidth() - bounds.width()) / 2;
                y = 30;
                break;
            case LEFT_TOP:
                x = 10;
                y = 30;
                break;
            case LEFT_CENTER:
                x = 10;
                y = (bitmap.getHeight() + bounds.height()) / 2;
                break;
            case RIGHT_TOP:
                x = (bitmap.getWidth() - bounds.width() - 10);
                y = 30;
                break;
            case RIGHT_CENTER:
                x = (bitmap.getWidth() - bounds.width() - 10);
                y = (bitmap.getHeight() + bounds.height()) / 2;
                break;
        }
        canvas.drawText(gText, x, y, paint);
        return bitmap;
    }
}