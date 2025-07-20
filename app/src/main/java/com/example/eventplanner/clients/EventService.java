package com.example.eventplanner.clients;

import com.example.eventplanner.model.DisplayEventType;
import com.example.eventplanner.model.Event;
import com.example.eventplanner.model.EventType;
import com.example.eventplanner.model.ServiceAndProductCategory;
import com.example.eventplanner.model.UpdateEventType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @POST("api/eventType")
    Call<EventType> addEt(@Body EventType et);

    @POST("/api/events")
    Call<Event> addEv(@Body Event ev);

    @GET("/api/eventType")
    Call<ArrayList<String>> getEventTypes();

    @GET("/api/eventType/{id}")
    Call<List<DisplayEventType>> getEventTypesForSpp(@Path("id") Integer id);

    @GET("/api/eventType/serviceCategory/{id}")
    Call<Collection<ServiceAndProductCategory>> getCategoriesForSpp(@Path("id") Integer id);

    @POST("api/eventType/{id}")
    Call<UpdateEventType> updateEt(@Path("id") Integer id, @Body UpdateEventType et);
}
