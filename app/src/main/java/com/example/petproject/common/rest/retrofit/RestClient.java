package com.example.petproject.common.rest.retrofit;

import com.example.petproject.common.constants.RestConst;
import com.example.petproject.common.exception.RxErrorHandlingCallAdapterFactory;
import com.google.gson.Gson;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by f0x on 30.11.16.
 */

public class RestClient implements IRestClient {

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    @Inject
    public RestClient(OkHttpClient okHttpClient, Gson gson) {
        this.okHttpClient = okHttpClient;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(RestConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(new EnumConverterFactory())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public void cancelAllRequests() {
        okHttpClient.dispatcher().cancelAll();
    }
}
