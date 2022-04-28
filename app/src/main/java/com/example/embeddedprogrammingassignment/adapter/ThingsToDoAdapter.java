package com.example.embeddedprogrammingassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;

import com.example.embeddedprogrammingassignment.R;

import java.util.ArrayList;
import java.util.List;

public class ThingsToDoAdapter extends PagerAdapter {

    List<Integer> images;
    LayoutInflater layoutInflater;
    Boolean firstItem = true;

    public ThingsToDoAdapter(Context context) {
        images = getImagesList();
        layoutInflater = LayoutInflater.from(context);
    }

    private List<Integer> getImagesList() {
        List<Integer> imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.banner1);
        imageList.add(R.drawable.banner2);
        imageList.add(R.drawable.banner3);
        imageList.add(R.drawable.banner1);
        imageList.add(R.drawable.banner2);
        imageList.add(R.drawable.banner3);

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
        ImageView iv = view.findViewById(R.id.ivHomeThingsBanner);
        iv.setImageResource(images.get(position));
        container.addView(view);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_thingsToDoActivity);
            }
        });

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
