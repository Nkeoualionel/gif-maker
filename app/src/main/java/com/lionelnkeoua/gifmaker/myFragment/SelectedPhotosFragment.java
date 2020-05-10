package com.lionelnkeoua.gifmaker.myFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lionelnkeoua.gifmaker.MainActivity;
import com.lionelnkeoua.gifmaker.Model.Picture;
import com.lionelnkeoua.gifmaker.R;
import com.lionelnkeoua.gifmaker.Utils.BaseFragment;

import java.io.File;


/**
 * Created by Chirag on 23-04-2017.
 */
public class SelectedPhotosFragment extends BaseFragment {

    Picture picture;
    ImageView imgSelectedPIC;
    MainActivity mainActivity;
    int position;

    public SelectedPhotosFragment(Picture picture, int position) {
        this.picture = picture;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selected_photo_fragment, container, false);
        mainActivity = (MainActivity) getActivity();
        initViews(view);
        initData();
        initClickListner();

        return view;

    }


    @Override
    public void initViews(View view) {
        imgSelectedPIC = (ImageView) view.findViewById(R.id.imgSelectedPIC);
    }

    @Override
    public void initData() {

        File file = new File(picture.getPath());
        Glide.clear(imgSelectedPIC);
        Glide.with(mContext).load(file).asBitmap().placeholder(R.drawable.image_loader).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgSelectedPIC);


    }

    @Override
    public void initClickListner() {
    }


}
