package com.example.eventplanner.clients;

import com.example.eventplanner.dto.CreateServiceAndProductCategoryDTO;
import com.example.eventplanner.dto.CreateServiceDTO;
import com.example.eventplanner.dto.CreateServiceReviewDTO;
import com.example.eventplanner.dto.Page;
import com.example.eventplanner.dto.UpdateServiceDTO;
import com.example.eventplanner.model.EventOrganizer;
import com.example.eventplanner.model.GetService;
import com.example.eventplanner.model.Service;
import com.example.eventplanner.model.ServiceAndProductCategory;
import com.example.eventplanner.model.ServiceReview;
import com.example.eventplanner.model.enums.AppointmentType;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ServiceService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @POST("/api/services/review")
    Call<ServiceReview> createServiceReview(@Body CreateServiceReviewDTO serviceReviewDTO);

    @PUT("/api/services/review/{id}/approve")
    Call<ServiceReview> approveServiceReview(@Path("id") Integer id);

    @PUT("/api/services/review/{id}/deny")
    Call<ServiceReview> denyServiceReview(@Path("id") Integer id);

    @GET("/api/services")
    Call<ArrayList<Service>> findAll();

    @GET("/api/services/{id}")
    Call<Service> findOne(@Path("id") Integer id);

    @GET("/api/services/provider/{id}")
    Call<ArrayList<Service>> findByProviderId(@Path("id") Integer id);

    @GET("/api/services/search")
    Call<Page> search(@QueryMap Map<String, String> filters);

    @GET("/api/services/favService/{id}")
    Call<ArrayList<GetService>> getFavServices(@Path("id") Integer id);

    @POST("/api/services")
    Call<Service> createService(@Body CreateServiceDTO service);

    @PUT("/api/services/{id}")
    Call<Service> updateService(@Body UpdateServiceDTO service, @Path("id") Integer id);

    @DELETE("/api/services/{id}")
    Call<?> deleteService(@Path("id") Integer id);

    @DELETE("/api/services/delete/{id}")
    Call<?> deleteHardService(@Path("id") Integer id);

    @GET("/api/services/favorite")
    Call<ArrayList<Service>> findAllFavorites();

    @GET("/api/services/favorite/{id}")
    Call<Service> findOneFavorite(@Path("id") Integer id);

    @POST("/api/services/favorite/{userId}/{serviceId}")
    Call<Service> createFavoriteService(@Path("userId") Integer userId, @Path("serviceId") Integer serviceId);

    @DELETE("/api/services/favorite/delete/{userId}/{serviceId}")
    Call<?> deleteFavoriteService(@Path("userId") Integer userId, @Path("serviceId") Integer serviceId);

    @GET("/api/services/category")
    Call<Page> findAllCategories();

    @GET("/api/services/category/{id}")
    Call<ServiceAndProductCategory> findOneCategory(@Path("id") Integer id);

    @GET("/api/services/category/name/{name}")
    Call<ServiceAndProductCategory> findCategoryByName(@Path("name") String name);

    @POST("/api/services/category")
    Call<ServiceAndProductCategory> createCategory(@Body CreateServiceAndProductCategoryDTO category);

    @PUT("/api/services/category/{id}")
    Call<ServiceAndProductCategory> updateCategory(@Body CreateServiceAndProductCategoryDTO category, @Path("id") Integer id);

    @DELETE("/api/services/category/{id}")
    Call<?> deleteCategory(@Path("id") Integer id);

    @DELETE("/api/services/category/delete/{id}")
    Call<?> deleteHardCategory(@Path("id") Integer id);
}
