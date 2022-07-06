package com.example.embeddedprogrammingassignment.singleton;

import android.os.Bundle;
import android.util.Log;

public class SingletonBundle {

    static Bundle bundle;

    public SingletonBundle() {
        if(bundle == null){
            bundle = new Bundle();
        }
    }

    public static void saveBundle(Bundle b) {
        bundle = b;
        if (bundle != null) {
            Log.i("@Bundle save bundle",bundle.toString());
        }
    }

    public static Bundle getBundle(){
        if(bundle != null){
            Log.i("@Bundle Get Bundle",bundle.toString());
        }
        return bundle;
    }
}
