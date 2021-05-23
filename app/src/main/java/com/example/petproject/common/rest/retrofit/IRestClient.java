package com.example.petproject.common.rest.retrofit;

/**
 * Created by f0x on 30.11.16.
 */

public interface IRestClient {
    <S> S createService(Class<S> serviceClass);

    void cancelAllRequests();

}
