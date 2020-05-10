package com.lionelnkeoua.gifmaker.myImageChooser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lionelnkeoua.gifmaker.Model.Image;
import com.lionelnkeoua.gifmaker.R;
import com.lionelnkeoua.gifmaker.Utils.BaseFragment;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Chirag on 05-01-2018.
 */

public class MostRecentFragment extends BaseFragment {


    RecyclerView recyclerData;
    ImagePOJOAdapter imageAdapter;

    ArrayList<Image> mImages;

    public MostRecentFragment(ArrayList<Image> mImages) {
        this.mImages = mImages;
    }

    private CompositeDisposable mCompositeDisposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mediastorage_activity, container, false);
        initViews(view);
        initData();
        initClickListner();

        return view;

    }


    @Override
    public void initViews(View view) {

        recyclerData = (RecyclerView) view.findViewById(R.id.recyclerData);
        recyclerData.setLayoutManager(new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void initData() {
        mCompositeDisposable = new CompositeDisposable();


        imageAdapter = new ImagePOJOAdapter(mContext, mImages);
        recyclerData.setAdapter(imageAdapter);

    }

    @Override
    public void initClickListner() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.clear();

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
