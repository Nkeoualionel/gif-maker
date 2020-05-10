package com.lionelnkeoua.gifmaker.myCustomText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Chirag on 10-09-2017.
 */
public class TextsLarge extends TextView {


    public TextsLarge(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextsLarge(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextsLarge(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            try {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/bold.otf");
                setTypeface(tf);
            }catch (Exception e){

                Log.e("Custom Text",e.getMessage());
            }
        }
    }


}
