package com.abdulkarim.introscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private Button previousButton,nextButton;
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private SliderAdapter sliderAdapter;
    private TextView[] mDot;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_welcome);

        changeStatusBarColor();

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager = findViewById(R.id.slideViewPagerId);
        mDotLayout = findViewById(R.id.dotLayoutId);
        previousButton = findViewById(R.id.previousButtonId);
        nextButton = findViewById(R.id.nextButtonId);



        mSlideViewPager.setAdapter(sliderAdapter);

        addDotIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = mCurrentPage + 1;
                if (current < mDot.length) {
                    // move to next screen
                    mSlideViewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mSlideViewPager.setCurrentItem(mCurrentPage - 1);
                launchHomeScreen();
            }
        });
    }

    private void launchHomeScreen() {
        //prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    public void addDotIndicator(int position){
        mDot = new TextView[4];

        mDotLayout.removeAllViews();

        for (int i = 0; i<mDot.length; i++){
            mDot[i] = new TextView(this);
            mDot[i].setText(Html.fromHtml("&#8226;"));
            mDot[i].setTextSize(35);
            mDot[i].setTextColor(getResources().getColor(R.color.white));

            mDotLayout.addView(mDot[i]);

        }

        if (mDot.length > 0){
            mDot[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotIndicator(position);
            mCurrentPage = position;

            if (position == mDot.length -1){
                nextButton.setEnabled(true);
                previousButton.setEnabled(true);
                previousButton.setVisibility(View.GONE);
                nextButton.setText("Done");

            }else {

                nextButton.setEnabled(true);
                previousButton.setEnabled(true);
                previousButton.setVisibility(View.VISIBLE);
                nextButton.setText("Next");
                previousButton.setText("Skip");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
