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
