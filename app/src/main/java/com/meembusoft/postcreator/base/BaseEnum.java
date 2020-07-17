package com.meembusoft.postcreator.base;

import android.content.Context;

public interface BaseEnum {

    /**
     * Get the localized value
     *
     * @param context Context
     * @return The localized value
     */
    public String getLabel(Context context);

    /**
     * Get the key
     *
     * @return The key value
     */
    public int getKey();
}
