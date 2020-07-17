package com.rc.attributionpresenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rc.attributionpresenter.R;
import com.rc.attributionpresenter.adapter.AttributionAdapter;
import com.rc.attributionpresenter.model.Attribution;
import com.rc.attributionpresenter.model.Library;
import com.rc.attributionpresenter.view.AnimatedImageView;
import com.rc.attributionpresenter.view.AnimatedTextView;
import com.rc.attributionpresenter.view.ArcView;

import java.util.ArrayList;

public class LicenseActivity extends AppCompatActivity {

    //toolbar
    ArcView arcMenuView;
    AnimatedImageView arcMenuImage;
    AnimatedTextView toolbarTitle;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        initView();
    }

    private void initView() {
        initToolBar();

        ListView lvLicense = (ListView) findViewById(R.id.list);
        AttributionAdapter attributionAdapter = new AttributionAdapter(LicenseActivity.this);
        lvLicense.setAdapter(attributionAdapter);
        attributionAdapter.setData(getAllAttributions());
    }

    private void initToolBar() {
        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
        arcMenuImage = (AnimatedImageView) findViewById(R.id.arcImage);
        arcMenuView = (ArcView) findViewById(R.id.arcView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbarTitle.setAnimatedText(getString(R.string.title_activity_third_party_notice), 0L);

        arcMenuImage.setAnimatedImage(R.drawable.arrow_left, 0L);
        arcMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private ArrayList<Attribution> getAllAttributions() {
        ArrayList<Attribution> attributions = new ArrayList<>();

        //Gradle projects
        attributions.add(Library.FLOURISH_SKYDOVES.getAttribution());
        attributions.add(Library.MATERIAL_DESIGN_DIMENS_DMITRYMALKOVICH.getAttribution());
        attributions.add(Library.SPECTRUM_THE_BLUE_ALLIANCE.getAttribution());
        attributions.add(Library.DISCRETE_SEEKBAR_ANDER_WEB.getAttribution());

        //Library projects
        attributions.add(Library.CYCLE_MENU_CLEVEROAD.getAttribution());
        attributions.add(Library.ATTRIBUTE_PRESENTER_FRANMONTIEL.getAttribution());
        attributions.add(Library.POPUP_DIALOG_YMEX.getAttribution());

        return attributions;
    }
}