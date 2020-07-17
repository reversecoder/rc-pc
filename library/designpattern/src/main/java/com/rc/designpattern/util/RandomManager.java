package com.rc.designpattern.util;

import android.graphics.Color;
import android.util.Log;

import java.util.Random;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class RandomManager {

    private static final String NUMBERS = "0123456789";
    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static int getRandomColor() {
//        int r = (int) (Math.random() * 255);
//        int g = (int) (Math.random() * 255);
//        int b = (int) (Math.random() * 255);
//        int color = Color.rgb(r, g, b);

//        Random rnd = new Random();
//        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

//        int color = ((int) (Math.random() * 16777215)) | (0xFF << 24);

        String colors[] = {"#F44336", "#E91E63", "#9C27B0", "#673AB7", "#3F51B5", "#2196F3", "#03A9F4",
                "#00BCD4", "#009688", "#4CAF50", "#8BC34A", "#CDDC39", "#FFEB3B", "#FFC107", "#FF9800",
                "#FF5722", "#795548", "#9E9E9E", "#607D8B", "#FFFFFF"};
        String randomColor = colors[new Random().nextInt(colors.length)];
        Log.d("color>>", "color>>randomColor " + randomColor);
        int color = Color.parseColor(randomColor);
        Log.d("color>>", "color>>color " + color);

        return color;
    }

    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    public static String getRandom(String source, int length) {
        return isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
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

    public static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }

        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }
}