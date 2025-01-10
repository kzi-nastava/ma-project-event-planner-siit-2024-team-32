package com.example.eventplanner.clients;

import com.example.eventplanner.model.RegisteredUser;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RegisteredUserService {
   @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("api/users/registration/user")
    Call<RegisteredUser> add(@Body RegisteredUser user);
}
