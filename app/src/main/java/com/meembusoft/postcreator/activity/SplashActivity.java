package com.meembusoft.postcreator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;
import com.meembusoft.animationmanager.AnimationManager;
import com.meembusoft.animationmanager.listener.AnimationUpdateListener;
import com.meembusoft.postcreator.R;
import com.meembusoft.postcreator.util.AppUtil;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class SplashActivity extends AppCompatActivity {
    private static String TAG = SplashActivity.class.getSimpleName();

    private TextView tvAppVersion;
    private ImageView ivAppLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initStatusBar();
        initActivityViews();
        initActivityViewsData();
    }

    private void initStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        StatusBarUtil.setTransparent(SplashActivity.this);
    }

    public void initActivityViews() {
        tvAppVersion = (TextView) findViewById(R.id.tv_app_version);
        ivAppLogo = (ImageView) findViewById(R.id.iv_app_logo);
    }

    public void initActivityViewsData() {
        //Set app version
        String appVersion = AppUtil.getAppVersion(SplashActivity.this);
        if (!TextUtils.isEmpty(appVersion)) {
            tvAppVersion.setText("Version: " + appVersion);
        }

        // Rotate app logo
        AnimationManager.makeRotateAnimation(ivAppLogo, 3, new AnimationUpdateListener() {
            @Override
            public void onUpdate(Object... update) {
                if ((boolean) update[0]) {
                    //Navigate to the next screen
                    navigateNextScreen();
                }
            }
        });
    }

    private void navigateNextScreen() {
        Intent intentAppCustomer = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intentAppCustomer);
        finish();
    }
}