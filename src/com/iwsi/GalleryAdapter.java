package com.iwsi;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.app.Activity;

public class GalleryAdapter extends BaseAdapter 
{
    private Context mContext;

    private Bitmap[] mPictures;
    private int itemBackground;
    
    public GalleryAdapter(Context pContext, Bitmap[] pPictures) 
    {
        mContext = pContext;
        mPictures = pPictures;
        //---setting the style---
        TypedArray a = pContext.obtainStyledAttributes(R.styleable.Gallery1);
        itemBackground = a.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 0);
        a.recycle();
    }

    public int getCount() {
        return mPictures.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup) 
    {
        // TODO Auto-generated method stub
        ImageView i = new ImageView(mContext);

        i.setImageBitmap(mPictures[index]);
        i.setLayoutParams(new Gallery.LayoutParams(280, 190));
    
        i.setScaleType(ImageView.ScaleType.FIT_XY);
        i.setBackgroundResource(itemBackground);
        
        return i;
    }
}