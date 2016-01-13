package com.yaohuan.myweibo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.yaohuan.myweibo.constants.CommonConstants;

/**
 * Created by yh on 2016/1/13.
 */
public class BaseActivity extends Activity {
    protected String TAG;
    protected BaseApplication application;
    protected SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getClass().getSimpleName();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        application= (BaseApplication) getApplication();
        sp=getSharedPreferences(CommonConstants.sp_name,MODE_PRIVATE);
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity){
        Intent intent=new Intent(this,tarActivity);
        startActivity(intent);
    }

    protected void showToast(String msg){

    }

    protected void showLog(String msg){

    }
}
