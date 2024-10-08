package com.dauba.news;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements HeadlinesFragment.OnHeadlineSelectedListener {

    private boolean isLandscape;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLandscape = findViewById(R.id.fragment_container_content) != null;

        if (savedInstanceState == null) {
            HeadlinesFragment headlinesFragment = new HeadlinesFragment();
            if (isLandscape) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_headlines, headlinesFragment)
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, headlinesFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onHeadlineSelected(String content) {
        ContentFragment contentFragment = ContentFragment.newInstance(content);

        if (isLandscape) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_content, contentFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, contentFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}