package com.lionelnkeoua.gifmaker.https;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {
    @GET("trending?api_key=1p9R1LLeEY1YFj9ZkGLcWPXSIJ25s4dH&limit=70&rating=G")
    Observable<com.lionelnkeoua.gifmaker.Model.Response> getALLCategory();

    @GET("search?api_key=1p9R1LLeEY1YFj9ZkGLcWPXSIJ25s4dH&limit=50&rating=G")
    Observable<com.lionelnkeoua.gifmaker.Model.Response> getSearchGify(@Query("q") String q);


}
