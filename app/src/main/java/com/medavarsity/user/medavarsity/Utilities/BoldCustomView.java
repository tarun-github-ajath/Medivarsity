package com.medavarsity.user.medavarsity.Utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by USER2 on 27-01-2016.
 */
public class BoldCustomView extends TextView {

    public BoldCustomView(Context context) {
        super(context);
        setFont();
    }

    public BoldCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }


    public BoldCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Nunito-Bold.ttf");
        setTypeface(font, Typeface.BOLD);
    }
}