package com.medavarsity.user.medavarsity.Utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        setFont();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }


    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Nunito-Regular.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}