package com.example.embeddedprogrammingassignment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.core.content.ContextCompat;

public class ActivityCanvas extends View{
    Paint paint;
    Rect rect;

    public ActivityCanvas(Context context) {
        super(context);
        paint = new Paint();
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        paint.setColor(Color.parseColor("#E4DFFF"));
        canvas.drawRect(0, 0, canvas.getWidth(), 400, paint);
    }
}
