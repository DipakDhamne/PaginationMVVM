package com.innofied.dipakdhamne.services;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.innofied.dipakdhamne.R;

public class CustomeBindingAdapter {

    @BindingAdapter("visibility")
    public static void showHide(View view, boolean show){
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String imageUrl){
        Glide.with(imageView.getContext()).load(imageUrl).placeholder(R.mipmap.ic_launcher).into(imageView);
    }

    @BindingAdapter("fullName")
    public static void fulName(TextView textView, String name){
        textView.setText(name);
    }

}
