package com.blanke.commonandroidlib;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by blanke on 2017/5/23.
 */

public interface MainApi {
    @GET("user")
    Observable<LoginResponse> login();
}
