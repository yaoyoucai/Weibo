package com.yaohuan.myweibo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.widget.LoginButton;
import com.yaohuan.myweibo.BaseActivity;
import com.yaohuan.myweibo.constants.AccessTokenKeeper;
import com.yaohuan.myweibo.constants.WeiboConstants;

/**
 * Created by yh on 2016/1/13.
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "weibosdk";

    /**
     * 显示认证后的信息，如 AccessToken
     */
    private TextView mTokenText;

    private AuthInfo mAuthInfo;

    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;

    /**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    private SsoHandler mSsoHandler;

    private LoginButton mLoginBtnDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuthInfo = new AuthInfo(this, WeiboConstants.APP_KEY,
                WeiboConstants.REDIRECT_URL, WeiboConstants.SCOPE);
        mLoginBtnDefault = (LoginButton) findViewById(R.id.login_button_default);
        mLoginBtnDefault.setWeiboAuthInfo(mAuthInfo, new AuthListener()); // 为按钮设置授权认证信息
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String phoneNum = mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
                Toast.makeText(LoginActivity.this,
                        "auth_success", Toast.LENGTH_SHORT).show();
                intent2Activity(MainActivity.class);
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = "auth_failed";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "Obtained the code: " + code;
                }
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this,
                    "auth_canceled", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
