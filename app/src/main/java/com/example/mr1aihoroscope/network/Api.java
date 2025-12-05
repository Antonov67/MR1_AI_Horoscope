package com.example.mr1aihoroscope.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer "
    })
    @POST("chat/completions")
    Call<OpenRouterResponse> sendMessage(@Body OpenRouterRequest request);
}
