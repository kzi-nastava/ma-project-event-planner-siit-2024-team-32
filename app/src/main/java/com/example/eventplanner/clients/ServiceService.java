package com.example.eventplanner.clients;

import com.example.eventplanner.model.GetService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ServiceService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @GET("/api/services/favService/{id}")
    Call<ArrayList<GetService>> getFavServices(@Path("id") Integer id);
}
