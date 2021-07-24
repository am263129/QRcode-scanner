package com.learntodroid.androidqrcodescanner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learntodroid.androidqrcodescanner.MainActivity;
import com.learntodroid.androidqrcodescanner.R;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;
import com.learntodroid.androidqrcodescanner.Util.PrivacyAdapter;
import com.learntodroid.androidqrcodescanner.Util.Util;
import com.learntodroid.androidqrcodescanner.Views.ClickableViewPager;
import com.learntodroid.androidqrcodescanner.Views.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OnboardActivity extends AppCompatActivity {

    private ClickableViewPager view_pager_slide;
    private PrivacyAdapter slide_adapter;
    private List<Integer> slideList= new ArrayList<>();
    private ViewPagerIndicator view_pager_indicator;
    private RelativeLayout relative_layout_slide;
    private LinearLayout linear_layout_skip;
    private PrefManager prefManager;
    private LinearLayout linear_layout_next;
    private TextView text_view_next_done;

    private String email, password, name;
    private String TAG = "PrivacyPolicy";
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);
        prefManager= new PrefManager(getApplicationContext());

        slideList.add(1);
        slideList.add(2);
        slideList.add(3);
        slideList.add(4);
        slideList.add(5);

        this.text_view_next_done=(TextView) findViewById(R.id.text_view_next_done);
        this.linear_layout_next=(LinearLayout) findViewById(R.id.linear_layout_next);
        this.linear_layout_skip=(LinearLayout) findViewById(R.id.linear_layout_skip);
        this.view_pager_indicator=(ViewPagerIndicator) findViewById(R.id.view_pager_indicator);
        this.view_pager_slide=(ClickableViewPager) findViewById(R.id.view_pager_slide);
        this.relative_layout_slide=(RelativeLayout) findViewById(R.id.relative_layout_slide);
        slide_adapter = new PrivacyAdapter(getApplicationContext(),slideList);
        view_pager_slide.setAdapter(this.slide_adapter);
        view_pager_slide.setOffscreenPageLimit(1);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        //view_pager_slide.setPageTransformer(false, new CarouselEffectTransformer(IntroActivity.this)); // Set transformer


        view_pager_slide.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position <6){
                    view_pager_slide.setCurrentItem(position+1);
                }else{
                    start();
                }
            }
        });
        this.linear_layout_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text_view_next_done.getText().equals("DONE")){

                    start();
                }
                if ( view_pager_slide.getCurrentItem() < slideList.size()) {
                    view_pager_slide.setCurrentItem(view_pager_slide.getCurrentItem() + 1);
                    return;
                }

            }
        });
        view_pager_slide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position+1==slideList.size()){
                    text_view_next_done.setText("DONE");
                }else{
                    text_view_next_done.setText("NEXT");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        this.linear_layout_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();

            }
        });
        this.view_pager_slide.setClipToPadding(false);
        this.view_pager_slide.setPageMargin(0);
        view_pager_indicator.setupWithViewPager(view_pager_slide);
    }


    public void start(){
        Util.saveSetting(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}