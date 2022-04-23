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
        imageList.add(R.raw.do_wear_mask);
        imageList.add(R.raw.do_wash_hand);
        imageList.add(R.raw.do_sanitize);
        return imageList;
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
        LottieAnimationView image = view.findViewById(R.id.lottieHomeThingsToDoLabel);
        TextView title = view.findViewById(R.id.tvHomeThingsToDoLabel);
        title.setText(imageTitle[position]);
        image.setAnimation(images.get(position));
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
