package com.example.eventplanner.clients;

import com.example.eventplanner.model.Activity;
import com.example.eventplanner.model.CreatedEvent;
import com.example.eventplanner.model.DisplayEvent;
import com.example.eventplanner.model.DisplayEventType;
import com.example.eventplanner.model.Event;
import com.example.eventplanner.model.EventType;
import com.example.eventplanner.model.ServiceAndProductCategory;
import com.example.eventplanner.model.UpdateEventType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EventService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @POST("api/eventType/{logInUserid}")
    Call<EventType> addEt(@Path("logInUserid") Integer logInUserid,@Body EventType et);

    @POST("/api/events/{id}")
    Call<Event> addEv(@Path("id") Integer id,@Body Event ev);

    @GET("/api/eventType")
    Call<ArrayList<String>> getEventTypes();

    @GET("/api/eventType/{id}")
    Call<Collection<DisplayEventType>> getEventTypesForSpp(@Path("id") Integer id);

    @GET("/api/eventType/serviceCategory/{id}")
    Call<Collection<ServiceAndProductCategory>> getCategoriesForSpp(@Path("id") Integer id);

    @PUT("api/eventType/{id}")
    Call<UpdateEventType> updateEt(@Path("id") Integer id, @Body UpdateEventType et);

    @POST("api/events/activity")
    Call<Activity> createActivity(@Body Activity activity);

    @GET("/api/events/all")
    Call<Collection<DisplayEvent>> getAllEvents();

    @GET("/api/events/ratings/{id}")
    Call<ArrayList<Integer>> getRatingsForEvent(@Path("id") Integer id);

    @GET("/api/events/participants/{id}")
    Call<ArrayList<String>> getParticipationDateTime(@Path("id") Integer id);

    @GET("/api/events/specificEvent/{id}")
    Call<DisplayEvent> getSpecificEvent(@Path("id") Integer id);

    @GET("/api/events/reserved/{id}")
    Call<Collection<DisplayEvent>> getReservedEvent(@Path("id") Integer id);

    @GET("/api/events/{year}/{month}/{day}/{id}")
    Call<DisplayEvent> getEvent( @Path("year") Integer year,@Path("month") Integer month,@Path("day") Integer day,@Path("id") Integer id);

    @POST("api/events/{id}")
    Call<Integer> addToFavourite(@Path("id") Integer id,@Body Integer idEv);

    @GET("/api/events/eventParticipants/{id}")
    Call<ArrayList<String>> getParticipantsNames(@Path("id") Integer id);

    @GET("/api/events/favEvents/{id}")
    Call<ArrayList<CreatedEvent>> getFavEvents(@Path("id") Integer id);


    @GET("/api/events/top5Events")
    Call<ArrayList<Event>> getTop5Events();

}
