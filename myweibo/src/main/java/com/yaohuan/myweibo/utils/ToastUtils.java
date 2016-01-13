package com.yaohuan.myweibo.utils;

import android.content.Context;
import android.widget.Toast;

import com.yaohuan.myweibo.activity.R;

/**
 * Created by yh on 2016/1/13.
 */
public class ToastUtils {
    private static Toast mToast;

    public static void showToast(Context context, CharSequence text, int duration) {
        if (mToast != null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}
