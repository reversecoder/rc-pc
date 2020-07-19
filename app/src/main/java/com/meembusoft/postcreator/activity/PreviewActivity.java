package com.meembusoft.postcreator.activity;

import android.Manifest;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
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
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.File;
import java.util.List;

import me.echodev.resizer.Resizer;

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
    private String mText = "";

    // Attribute
    private ImageView ivAttributeBackground;
    private DiscreteSeekBar seekBarAttributeShadow;
    private EditText edtAttributeText;
    private int mAlphaColor;

    // Image picker
    private int INTENT_REQUEST_CODE_IMAGE_PICKER = 420;
    private String mImagePath = "";
    private Bitmap mOriginalBitmap = null;

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
                mText = s.toString();
                tvText.setText(mText);
            }
        });

        seekBarAttributeShadow.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                if (mOriginalBitmap != null) {
                    setViewShadow(viewShadow, value);
                    mAlphaColor = ColorPickerManager.getAlphaColor(ContextCompat.getColor(getActivity(), R.color.colorAlphaShadeBlack), value);
                } else {
                    Log.d(TAG, "onProgressChanged>> mOriginalBitmap null");
                }
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

                if(!TextUtils.isEmpty(mImagePath)){
                    try {
                        File imageFile = new File(mImagePath);
                        Log.d(TAG, "MatisseImage>> image length is greater than 1 mega bit.");
                        if(imageFile.length()>(1024 * 1024)){
                            mOriginalBitmap = new Resizer(this)
                                    .setQuality(100)
                                    .setSourceImage(imageFile)
                                    .getResizedBitmap();
                        } else {
                            Log.d(TAG, "MatisseImage>> image length is less than 1 mega bit.");
                            mOriginalBitmap = BitmapManager.createBitmap(getActivity(), mImagePath);
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
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
        if (mOriginalBitmap != null) {
            mOriginalBitmap.recycle();
        }
    }

    @Override
    public void initActivityPermissionResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    private void initAttributes() {
        seekBarAttributeShadow.setProgress(50);
        setViewShadow(viewShadow, 50);
        mAlphaColor = ColorPickerManager.getAlphaColor(ContextCompat.getColor(getActivity(), R.color.colorAlphaShadeBlack), 50);
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
            if (mOriginalBitmap != null) {
                // Add watermark
                Bitmap watermarkedBitmap = BitmapManager.setWaterMark(getActivity(), mOriginalBitmap, getString(R.string.txt_group_name));
                // Add shade
                Bitmap shadedBitmap = BitmapManager.addShade(watermarkedBitmap, mAlphaColor);
                // Add stamp
                Bitmap stampedBitmap = BitmapManager.addStamp(PreviewActivity.this, shadedBitmap, "MeembuSoft", BitmapManager.STAMP_POSITION.RIGHT_BOTTOM);
                // Add text
                Bitmap textedBitmap = BitmapManager.addText(PreviewActivity.this, stampedBitmap, mText, BitmapManager.TEXT_POSITION.CENTER);

//            Bitmap frame = BitmapManager.addFrame(watermarkedBitmap);
//            Bitmap shadow = BitmapManager.addShadow(watermarkedBitmap, watermarkedBitmap.getHeight(), watermarkedBitmap.getWidth(),  Color.BLACK, 3, 1, 3 );
//            Bitmap linearShade = BitmapManager.addLinearGradient( watermarkedBitmap, watermarkedBitmap.getHeight());

                BitmapManager.saveBitmap(getActivity(), textedBitmap);
            } else {
                Log.d(TAG, "takeScreenShot>> mOriginalBitmap null");
                Toast.makeText(getActivity(), "Please set background image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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