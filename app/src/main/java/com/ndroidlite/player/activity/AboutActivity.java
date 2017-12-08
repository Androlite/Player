package com.ndroidlite.player.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kabouzeid.appthemehelper.ThemeStore;
import com.ndroidlite.player.R;
import com.ndroidlite.player.activity.base.AdlBaseActivity;

public class AboutActivity extends AdlBaseActivity {
    public static final String TAG = AboutActivity.class.getSimpleName();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setDrawUnderStatusbar(true);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setStatusbarColorAuto();
        setNavigationbarColorAuto();
        setTaskDescriptionColorAuto();

        toolbar.setBackgroundColor(ThemeStore.primaryColor(this));
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        clickListener();
    }

    private void init() {

    }

    private void clickListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
