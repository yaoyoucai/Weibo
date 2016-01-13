package com.yaohuan.myweibo.utils;

import android.util.Log;

import com.yaohuan.myweibo.constants.CommonConstants;

/**
 * Created by yh on 2016/1/13.
 */
public class Logger {

    /**
     * 显示LOG(默认Info级别)
     * @param TAG
     * @param msg
     */
    public static void show(String TAG, String msg) {
        if (!CommonConstants.isShowLog) {
            return;
        }
        show(TAG, msg, Log.INFO);
    }

    /**
     * 显示LOG
     * @param TAG
     * @param msg
     * @param level
     */
    public static void show(String TAG, String msg, int level) {
        if (!CommonConstants.isShowLog) {
            return;
        }
        switch (level) {
            case Log.VERBOSE:
                Log.v(TAG, msg);
                break;
            case Log.DEBUG:
                Log.d(TAG, msg);
                break;
            case Log.INFO:
                Log.i(TAG, msg);
                break;
            case Log.WARN:
                Log.w(TAG, msg);
                break;
            case Log.ERROR:
                Log.e(TAG, msg);
                break;
        }
    }
}

