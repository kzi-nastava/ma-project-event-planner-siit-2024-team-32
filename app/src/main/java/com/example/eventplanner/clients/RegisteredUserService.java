package com.example.eventplanner.clients;

import com.example.eventplanner.model.EventOrganizer;
import com.example.eventplanner.model.EventType;
import com.example.eventplanner.model.LogIn;
import com.example.eventplanner.model.ServiceAndProductProvider;
import com.example.eventplanner.model.Token;
import com.example.eventplanner.model.UpdateEventOrganizer;
import com.example.eventplanner.model.UpdateServiceAndProductProvider;

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
    @POST("api/users/registration1/eventOrganizer")
    Call<EventOrganizer> add(@Body EventOrganizer user);

    @POST("api/users/registration/serviceAndProductProvider")
    Call<ServiceAndProductProvider> addSpp(@Body ServiceAndProductProvider user);

    @GET("/api/users/getting/eventOrganizer/{id}")
    Call<EventOrganizer> getEo(@Path("id") Integer id);

    @GET("/api/users/getting/user/{id}")
    Call<ServiceAndProductProvider> getSpp(@Path("id") Integer id);

    @PUT("/api/users/updating/eventOrganizer/{id}")
    Call<UpdateEventOrganizer> updateEo(@Path("id") Integer id,@Body UpdateEventOrganizer updateEventOrganizer);

    @PUT("/api/users/updating/company/{id}")
    Call<UpdateServiceAndProductProvider> updateSpp(@Path("id") Integer id, @Body UpdateServiceAndProductProvider updateSpp);

    @PUT("/api/users/deactivate/{id}")
    Call<Void> deactivateEventOrganizer(@Path("id") Integer id, @Body boolean p);

    @PUT("/api/users/deactivatePup/{id}")
    Call<Void> deactivateServiceAndProductProvider(@Path("id") Integer id, @Body boolean p);

    @POST("/logIn")
    Call<Token> logIn(@Body LogIn user);
}
