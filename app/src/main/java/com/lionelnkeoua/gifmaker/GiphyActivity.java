package com.lionelnkeoua.gifmaker;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lionelnkeoua.gifmaker.myAdapters.GiphyAdapters;
import com.lionelnkeoua.gifmaker.myCustomText.EditsTextMedium;
import com.lionelnkeoua.gifmaker.Model.Data;
import com.lionelnkeoua.gifmaker.R;
import com.lionelnkeoua.gifmaker.Utils.BaseActivity;
import com.lionelnkeoua.gifmaker.https.RequestInterface;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chirag on 05-01-2018.
 */

public class GiphyActivity extends BaseActivity {

    private CompositeDisposable mCompositeDisposable;

    RecyclerView recyclerData;
    ArrayList<Data> mFixed_height_stills = new ArrayList<>();
    GiphyAdapters giphyAdapters;
    EditsTextMedium edtQuery;

    private AdView adView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gify_activity);


        initToolBar("Gify");
        initView();
        initData();
        initClickListenr();
       // applicationManager.displayBanner((Activity) mContext);

        adView = findViewById(R.id.adView);
        AdRequest adRequestBottom = new AdRequest
                .Builder()
                .build();
        adView.loadAd(adRequestBottom);

    }

    @Override
    public void initView() {
        recyclerData = (RecyclerView) findViewById(R.id.recyclerData);
        edtQuery = (EditsTextMedium) findViewById(R.id.edtQuery);
        recyclerData.setLayoutManager(new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false));

        mCompositeDisposable = new CompositeDisposable();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getGIF();
    }

    @Override
    public void initData() {

        applicationManager.displayBanner((Activity) mContext);
        appDialogue.showProgressDialogue();
        appDialogue.circle_progress.setVisibility(View.INVISIBLE);
        appDialogue.txtStatus.setText("");


    }


    @Override
    public void initClickListenr() {
        edtQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() >1) {
                    searchGify(s.toString());
                }else {
                    getGIF();
                }
            }
        });
    }


    public void searchGify(String q) {

        mCompositeDisposable = new CompositeDisposable();
        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl("https://api.giphy.com/v1/gifs/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface.class);
        mCompositeDisposable.add(requestInterface.getSearchGify(q)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(response -> response.data)
                .subscribe(this::handleResponse, this::handleError));

    }


    public void getGIF(){

        mCompositeDisposable = new CompositeDisposable();
        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl("https://api.giphy.com/v1/gifs/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface.class);
        mCompositeDisposable.add(requestInterface.getALLCategory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(response -> response.data)
                .subscribe(this::handleResponse, this::handleError));



    }
    private void handleResponse(ArrayList<Data> mCategorie) {

        try {
            appDialogue.dismisProgressDialogue();
        } catch (Exception e) {

        }
        appDialogue.dismisProgressDialogue();

        giphyAdapters = new GiphyAdapters(mContext, mCategorie);
        recyclerData.setAdapter(giphyAdapters);
        //  Toast.makeText(mContext, mCategorie.size(), Toast.LENGTH_SHORT).show();

    }

    private void handleError(Throwable error) {
        //   Toast.makeText(mContext, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        //  Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
