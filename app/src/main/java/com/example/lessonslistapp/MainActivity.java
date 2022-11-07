package com.example.lessonslistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.StrictMode;

import com.example.lessonslistapp.fragments.LessonsFrament;
import com.example.lessonslistapp.fragments.TeachersFragment;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TabLayout mainTabLayout;
    private ViewPager mainViewPager;

    public static MainRepository mainRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initialize();
                    }
                });
            }
        }).start();
    }
    private void initialize() {
        mainTabLayout = findViewById(R.id.mainTabLayout);
        mainViewPager = findViewById(R.id.mainViewPager);

        mainTabLayout.setupWithViewPager(mainViewPager);

        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mainFragmentAdapter.addFragment(new LessonsFrament(), "By group");
        mainFragmentAdapter.addFragment(new TeachersFragment(), "By teacher");

        mainViewPager.setAdapter(mainFragmentAdapter);
    }
}