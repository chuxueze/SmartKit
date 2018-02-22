package com.steven.smartkit.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Steven on 2018/2/21.
 */

public class UtilTools {
    //设置字体
    public static void setFont(Context mContext, TextView textview) {
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        textview.setTypeface(fontType);
    }
}
