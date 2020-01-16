package com.example.apinstagramclone.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.apinstagramclone.R;
import com.example.apinstagramclone.adapter.TabAdapter;
import com.google.android.material.tabs.TabLayout;

public class SocialMediaActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tablayout;
    TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        setTitle("Social Media App!!!");

        toolbar =  findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        viewPager =  findViewById(R.id.viewPager);
        tablayout = findViewById(R.id.myTablayout);
        tabAdapter =  new TabAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(tabAdapter);
        tablayout.setupWithViewPager(viewPager,false);
    }


}
