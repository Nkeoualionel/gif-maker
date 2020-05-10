package com.lionelnkeoua.gifmaker.myImageChooser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lionelnkeoua.gifmaker.myAdapters.ViewPagerAdapters;
import com.lionelnkeoua.gifmaker.myCustomText.TextsMediumBold;
import com.lionelnkeoua.gifmaker.EditActivity;
import com.lionelnkeoua.gifmaker.myHomeStorage.Utils;
import com.lionelnkeoua.gifmaker.Model.Image;
import com.lionelnkeoua.gifmaker.R;
import com.lionelnkeoua.gifmaker.Utils.BaseActivity;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chirag on 05-01-2018.
 */

public class SelectImageActivity extends BaseActivity {


    public TabLayout tabLayout;
    public ViewPager pager;
    public TextsMediumBold txtNext;
    ArrayList<Image> mSelected = new ArrayList<>();
    private AdView mAdView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_image_activity);
        initToolBar("ADD IMAGES");
        initView();
        initData();
        initClickListenr();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequestBottom = new AdRequest
                .Builder()
                .build();
        mAdView.loadAd(adRequestBottom);

    }

    @Override
    public void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.viewpager);
        txtNext = (TextsMediumBold) toolbar.findViewById(R.id.txtNext);

        checkIsSelect();
    }

    @Override
    public void initData() {
        final ViewPagerAdapters adapter = new ViewPagerAdapters(getSupportFragmentManager());

        Observable.just(Utils.getImagesFromDevice(mContext, false, null, 0)).subscribe(new Observer<ArrayList<Image>>() {
            @Override
            public void onSubscribe(Disposable d) {


            }

            @Override
            public void onNext(ArrayList<Image> value) {
                adapter.addFragment(new MostRecentFragment(value), "MOST RECENT");


                Observable.just(getFolder(value)).subscribe(new Observer<ArrayList<Image>>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(ArrayList<Image> value) {
                        adapter.addFragment(new FolderFragment(value), "ALL");
                        pager.setAdapter(adapter);
                        tabLayout.setupWithViewPager(pager);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        appDialogue.dismisProgressDialogue();

                    }
                });

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {


            }
        });


    }

    @Override
    public void initClickListenr() {


        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prefrences.setCourseList(mSelected);
                Intent intent = new Intent(mContext, EditActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }


    public ArrayList<Image> getFolder(ArrayList<Image> mImages) {


        ArrayList<Image> mDataFiles = new ArrayList<>();
        mDataFiles.addAll(mImages);


        for (int i = 0; i < mDataFiles.size(); i++) {


            Image dataFiles = mDataFiles.get(i);
            ArrayList<Image> mDataVideos = new ArrayList<Image>();
            mDataVideos.add(dataFiles);

            for (int j = i + 1; j < mDataFiles.size(); j++) {
                if (dataFiles.getFolderName().equalsIgnoreCase(mDataFiles.get(j).getFolderName())) {
                    mDataVideos.add(mDataFiles.get(j));
                    mDataFiles.remove(j);
                    j--;
                }


            }

            dataFiles.setmImages(mDataVideos);
            mDataFiles.set(i, dataFiles);

        }


        return mDataFiles;
    }


    public void addImages(Image images) {
        

            Image images1 = new Image();
            images1.setThumbPath(images.getThumbPath());
            images1.setFolderName(images.getFolderName());
            images1.setUri(images.getUri());
            mSelected.add(images1);
            checkIsSelect();


    
    }

    public void removeImages(Image images) {
        int i = 0;
        mSelected.remove(getNumbers(images));
        checkIsSelect();
    }


    public int getNumbers(Image images) {

        int i = 0;
        int selectedIndex = 0;
        for (Image selectedImages : mSelected) {
            if (selectedImages.getUri().equalsIgnoreCase(images.getUri())) {
                selectedIndex = i;
            }
            i++;

        }
        return selectedIndex;
    }


    public void checkIsSelect() {

        if (mSelected.size() == 0) {
            txtNext.setVisibility(View.GONE);

        } else {
            txtNext.setVisibility(View.VISIBLE);
            txtNext.setText("Next " + mSelected.size() + "");
        }
    }
}
