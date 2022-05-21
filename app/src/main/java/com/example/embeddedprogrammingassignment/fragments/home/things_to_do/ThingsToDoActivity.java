package com.example.embeddedprogrammingassignment.fragments.home.things_to_do;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

import com.example.embeddedprogrammingassignment.R;
import com.jem.liquidswipe.LiquidSwipeViewPager;

public class ThingsToDoActivity extends AppCompatActivity {

    private static final int NUM_PAGE = 3;
    LiquidSwipeViewPager viewPager;
    // https://github.com/Chrisvin/LiquidSwipe
    ScreenSlidePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_things_to_do);

        viewPager= findViewById(R.id.vpThingsToDo);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DoOneFragment();
                case 1:
                    return new DoTwoFragment();
                case 2:
                    return new DoThreeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGE;
        }
    }
}