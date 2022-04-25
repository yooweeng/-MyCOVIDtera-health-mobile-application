package com.example.embeddedprogrammingassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.example.embeddedprogrammingassignment.R;

import java.util.ArrayList;
import java.util.List;

public class ThingsToDoAdapter extends PagerAdapter {

    List<Integer> images;
    LayoutInflater layoutInflater;

    public ThingsToDoAdapter(Context context) {
        images = getImagesList();
        layoutInflater = LayoutInflater.from(context);
    }

    private List<Integer> getImagesList() {
        List<Integer> imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.test1);
        imageList.add(R.drawable.test1);
        imageList.add(R.drawable.test1);
        return imageList;
    }

    public void setCovers() {
        images.clear();
        images.add(R.drawable.test1);
        images.add(R.drawable.test1);
        images.add(R.drawable.test1);
        notifyDataSetChanged();
    }

    private final String[] imageTitle = new String[] {"Wear a mask!", "Wash your hands!", "Sanitize regularly!"};

    @Override
    public int getCount() {
        return images.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.layout_things_scroll, container, false);
        ImageView iv = view.findViewById(R.id.ivHomeThingsBanner);
        iv.setImageResource(images.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return imageTitle[position];
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}
