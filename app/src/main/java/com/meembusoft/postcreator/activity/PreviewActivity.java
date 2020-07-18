package com.meembusoft.postcreator.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.meembusoft.postcreator.BuildConfig;
import com.meembusoft.postcreator.R;
import com.meembusoft.postcreator.base.activity.BaseActivity;
import com.meembusoft.postcreator.util.AppUtil;
import com.meembusoft.postcreator.util.BitmapManager;
import com.meembusoft.postcreator.util.ColorPickerManager;
import com.meembusoft.postcreator.util.KeyboardManager;
import com.meembusoft.postcreator.view.GlazyImageView;
import com.meembusoft.postcreator.view.Utils;
import com.watermark.androidwm_light.WatermarkBuilder;
import com.watermark.androidwm_light.bean.WatermarkText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class PreviewActivity extends BaseActivity {

    // Toolbar
    private ImageView leftMenu;
    private ImageView rightMenu;
    private TextView toolbarTitle;

    // Bottom menu
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout llBottomSheet, llViewOptionsHeader;
    private AppCompatImageView ivToggleAttributes;

    // Content
    private ImageView ivBackground;
    private ImageView viewShadow;
    private TextView tvText;

    // Attribute
    private ImageView ivAttributeBackground;
    private DiscreteSeekBar seekBarAttributeShadow;
    private EditText edtAttributeText;

    // Image picker
    private int INTENT_REQUEST_CODE_IMAGE_PICKER = 420;
    private String mImagePath = "";

    @Override
    public String[] initActivityPermissions() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    }

    @Override
    public int initActivityLayout() {
        return R.layout.activity_preview;
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

        // Bottom sheet
        llBottomSheet = (LinearLayout) findViewById(R.id.bottomSheet);
        llViewOptionsHeader = (LinearLayout) findViewById(R.id.view_options_header);
        ivToggleAttributes = (AppCompatImageView) findViewById(R.id.image_toggle);

        // Content
        ivBackground = (ImageView) findViewById(R.id.iv_background);
        viewShadow = (ImageView) findViewById(R.id.view_shadow);
        tvText = (TextView) findViewById(R.id.tv_text);

        // Attribute
        ivAttributeBackground = (ImageView) findViewById(R.id.iv_attribute_background);
        seekBarAttributeShadow = (DiscreteSeekBar) findViewById(R.id.seekbar_attribute_shadow);
        edtAttributeText = (EditText) findViewById(R.id.edt_attribute_text);

//        ivBackground.setImageRes(R.drawable.logo_text);
//        ivBackground.setTitleText("Rashed");
//        ivBackground.setTitleTextColor(Color.BLACK);
//        ivBackground.setTitleTextSize(Utils.dpToPx(PreviewActivity.this, 22));
//        ivBackground.setSubTitleText("Software Engineer");
//        ivBackground.setSubTitleTextColor(R.color.colorPrimary);
//        ivBackground.setSubTitleTextSize(Utils.dpToPx(PreviewActivity.this, 16));
//////        imgView.setTextMargin(Utils.dpToPx(TestPagerActivity.this, 5));
//////        imgView.setLineSpacing(Utils.dpToPx(TestPagerActivity.this, 5));
//        ivBackground.setAutoTint(true);
//        ivBackground.setTintColor(Color.BLUE);
//        ivBackground.setTintAlpha(125);
//        ivBackground.setCutType(GlazyImageView.ImageCutType.WAVE);
//        ivBackground.setCutCount(3);
//        ivBackground.setCutHeight(Utils.dpToPx(PreviewActivity.this,40));
//        ivBackground.post(new Runnable() {
//            public void run() {
//                ivBackground.update(0.99f);
//            }
//        });
    }

    @Override
    public void initActivityViewsData(Bundle savedInstanceState) {
        toolbarTitle.setText("Preview");
        rightMenu.setBackgroundResource(R.drawable.vector_tick_circular_bg_white);
        rightMenu.setVisibility(View.VISIBLE);
        initBottomSheet();
        initAttributes();

        try {
            Log.d(TAG, "password>>super user: " + BuildConfig.ENCRYPTED_SUPER_USER_PASSWORD);
            Log.d(TAG, "password>>user: " + BuildConfig.ENCRYPTED_USER_PASSWORD);

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
        //                }
//            }, 1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initActivityActions(Bundle savedInstanceState) {
        leftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initActivityBackPress();
            }
        });

        rightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenShot();
            }
        });

        ivAttributeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse.from(getActivity())
                        .choose(MimeType.ofImage())
                        .theme(R.style.Matisse_Dracula)
                        .capture(true)
                        .setDefaultCaptureStrategy()
                        .countable(false)
                        .maxSelectable(1)
                        .imageEngine(new GlideEngine())
                        .forResult(INTENT_REQUEST_CODE_IMAGE_PICKER);
            }
        });

        edtAttributeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvText.setText(s.toString());
            }
        });

        seekBarAttributeShadow.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                setViewShadow(viewShadow, value);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar, int value) {

            }
        });
    }

    @Override
    public void initActivityOnResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_REQUEST_CODE_IMAGE_PICKER && resultCode == RESULT_OK) {
            List<String> mData = Matisse.obtainPathResult(data);

            if (mData.size() == 1) {
                mImagePath = mData.get(0);
                Log.d(TAG, "MatisseImage: " + mImagePath);

                AppUtil.loadImage(getActivity(), ivAttributeBackground, mImagePath, false, true, false);
                AppUtil.loadImage(getActivity(), ivBackground, mImagePath, false, false, false);
            }
        }
    }

    @Override
    public void initActivityBackPress() {
        if (bottomSheetBehavior != null && bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }
        finish();
    }

    @Override
    public void initActivityDestroyTasks() {

    }

    @Override
    public void initActivityPermissionResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    private void initAttributes() {
        seekBarAttributeShadow.setProgress(150);
        setViewShadow(viewShadow, 150);
    }

    private void initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        llViewOptionsHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    KeyboardManager.hideKeyboard(getActivity());
                }
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        ivToggleAttributes.setImageResource(R.drawable.ic_expand_less_black_24dp);

                        KeyboardManager.hideKeyboard(getActivity());
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        ivToggleAttributes.setImageResource(R.drawable.ic_expand_more_black_24dp);
                        break;
                    default:
                        ivToggleAttributes.setImageResource(R.drawable.ic_expand_less_black_24dp);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    private void takeScreenShot() {
        try {
            Bitmap bitmapPostView = createBitmap(R.drawable.courteny_cox);
            Bitmap logo = createBitmap(R.drawable.logo_text);
            Bitmap finalBitmap = BitmapManager.drawTextToBitmap(PreviewActivity.this, bitmapPostView,
                    "MeembuSoft", BitmapManager.STAMP_POSITION.RIGHT_BOTTOM);

            Bitmap watermarkedBitmap = setWaterMark(finalBitmap);
            saveBitmap(watermarkedBitmap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private Bitmap createBitmap(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    private Bitmap createBitmap(int drawableRes) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), drawableRes);
        return b;
    }

    public void saveBitmap(Bitmap bitmap) {
        Date now = new Date();
        android.text.format.DateFormat.format("dd-MM-yyyy_HH:mm_aaa", now);

        // Create folder if not exist
        String rootPath = Environment.getExternalStorageDirectory() + File.separator + "PostCreator";
        File folder = new File(rootPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        // image naming and path  to include sd card  appending name you choose for file
        String filePath = rootPath + File.separator + "PostCreator " + now + ".jpg";

        File imagePath = new File(filePath);
        FileOutputStream fos;
        if (bitmap != null) {
            try {
                fos = new FileOutputStream(imagePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();

                Toast.makeText(getApplicationContext(), "File saved successfully", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                Log.e("GREC", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                Log.e("GREC", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    private Bitmap setWaterMark(Bitmap bitmap) {
        WatermarkText watermarkText = new WatermarkText(getString(R.string.txt_group_name))
                .setPositionX(0.5)
                .setPositionY(0.5)
                .setTextColor(Color.WHITE)
                .setTextFont(R.font.champagne)
                .setTextShadow(0.1f, 5, 5, Color.BLUE)
                .setTextAlpha(90)
                .setRotation(50)
                .setTextSize(18);

        return WatermarkBuilder
                .create(this, bitmap)
                .loadWatermarkText(watermarkText)
                .setTileMode(true) // select different drawing mode.
                .getWatermark()
                .getOutputImage();
    }

    private void setViewShadow(ImageView imageView, int colorValue) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int updatedColor = ColorPickerManager.getAlphaColor(ContextCompat.getColor(getActivity(), R.color.colorAlphaShadeBlack), colorValue);
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
                                updatedColor,
                                updatedColor,
                                updatedColor,
                                updatedColor,
                                updatedColor
                        }
                );
                imageView.setBackgroundTintList(themeColorStateList);
            }
        }, 10);
    }
}