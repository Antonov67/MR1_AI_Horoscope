package com.example.mr1aihoroscope.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer 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"
    })
    @POST("chat/completions")
    Call<OpenRouterResponse> sendMessage(@Body OpenRouterRequest request);
}
