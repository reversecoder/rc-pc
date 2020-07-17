package com.meembusoft.postcreator.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.meembusoft.postcreator.R;
import com.meembusoft.postcreator.base.activity.BaseActivity;
import com.meembusoft.postcreator.util.ColorPickerManager;

public class HomeActivity extends BaseActivity {

    // Bottom navigation
    public static final int ID_HOME = 1;
    public static final int ID_MESSAGE = 2;
    public static final int ID_NOTIFICATION = 3;
    public static final int ID_ACCOUNT = 4;
    private MeowBottomNavigation bottomNavigation;
    private ImageView ivCreatePost;

    //Toolbar
    private ImageView leftMenu;
    private ImageView rightMenu;
    private TextView toolbarTitle;

    @Override
    public String[] initActivityPermissions() {
        return new String[]{};
    }

    @Override
    public int initActivityLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initStatusBarView() {

    }

    @Override
    public void initNavigationBarView() {

    }

    @Override
    public void initIntentData(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    public void initActivityViews() {
        //toolbar view
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        leftMenu = (ImageView) findViewById(R.id.left_menu);
        rightMenu = (ImageView) findViewById(R.id.right_menu);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        ivCreatePost = findViewById(R.id.iv_create_post);
    }

    @Override
    public void initActivityViewsData(Bundle savedInstanceState) {
        toolbarTitle.setText("Home");
        setShadowColor(ivCreatePost);
        initBottomNavigation();
    }

    @Override
    public void initActivityActions(Bundle savedInstanceState) {
        leftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initActivityBackPress();
            }
        });

        ivCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreview = new Intent(HomeActivity.this, PreviewActivity.class);
                startActivity(intentPreview);
            }
        });
    }

    @Override
    public void initActivityOnResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void initActivityBackPress() {
        finish();
    }

    @Override
    public void initActivityDestroyTasks() {

    }

    @Override
    public void initActivityPermissionResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    private void initBottomNavigation() {
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_MESSAGE, R.drawable.ic_message));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_NOTIFICATION, R.drawable.ic_notification));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.ic_account));

        bottomNavigation.show(ID_HOME, false);
    }

    private void setShadowColor(ImageView imageView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int centerX = (imageView.getLeft() + imageView.getRight()) / 2;
                int centerY = (imageView.getTop() + imageView.getBottom()) / 2;
                int centerColorPure = ColorPickerManager.getPixelColor(imageView, centerX, centerY);
                int centerColorNormal = ColorPickerManager.getAlphaColor(centerColorPure, 40);
                int centerColorPressed = ColorPickerManager.getAlphaColor(centerColorPure, 80);
                Log.d("ColorPicker", "x: " + centerX + " y: " + centerY);
                Log.d("ColorPicker", "centerColorPure>> " + centerColorPure);
                Log.d("ColorPicker", "centerColorNormal>> " + centerColorNormal);
                Log.d("ColorPicker", "centerColorPressed>> " + centerColorPressed);

                // Create color state
                ColorStateList themeColorStateList = new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_pressed},
                                new int[]{android.R.attr.state_enabled},
                                new int[]{android.R.attr.state_focused, android.R.attr.state_pressed},
                                new int[]{-android.R.attr.state_enabled},
                                new int[]{} // this should be empty to make default color as we want
                        },
                        new int[]{
                                centerColorPressed,
                                centerColorNormal,
                                centerColorPressed,
                                centerColorNormal,
                                centerColorNormal
                        }
                );
                imageView.setBackgroundTintList(themeColorStateList);


//                int[][] states = new int[][] {
//                        new int[] { android.R.attr.state_pressed} , // pressed
//                        new int[] { android.R.attr.state_enabled}, // enabled
//                        new int[] {-android.R.attr.state_enabled}, // disabled
//                        new int[] {-android.R.attr.state_checked} // unchecked
//                };
//
//                int[] colors = new int[] {
//                        centerColorPressed,
//                        centerColorNormal,
//                        centerColorNormal,
//                        centerColorPressed
//                };
//
//                ColorStateList myList = new ColorStateList(states, colors);
//                imageView.setBackgroundTintList(myList);
            }
        }, 100);
    }
}