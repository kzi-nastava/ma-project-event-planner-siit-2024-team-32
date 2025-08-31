package com.example.eventplanner.clients;

import com.example.eventplanner.model.DisplayProduct;
import com.example.eventplanner.model.EventType;
import com.example.eventplanner.model.GetProduct;
import com.example.eventplanner.model.Product;
import com.example.eventplanner.model.UpdateProduct;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @GET("api/products/categories")
    Call<ArrayList<String>> getCategories();

    @POST("/api/products/{logInUserId}")
    Call<Product> addPro(@Path("logInUserId") Integer id,@Body Product p);

    @GET("api/products/{id}")
    Call<Collection<DisplayProduct>> getProductsForSpp(@Path("id") Integer id);

    @PUT("api/products/{id1}/{id}")
    Call<Collection<DisplayProduct>> deleteProductLogically(@Path("id1") Integer id1,@Path("id") Integer id,@Body String str);

    @PUT("api/products/update/{id}")
    Call<UpdateProduct> updateProduct(@Path("id") Integer id,@Body UpdateProduct up);

    @GET("api/products/search/{id}/{name}")
    Call<Collection<DisplayProduct>> getSearchedProducts(@Path("id") Integer id, @Path("name") String name);

    @GET("api/products/filter/{id}/{filt}/{rel}/{val}")
    Call<Collection<DisplayProduct>> getFilteredProducts(@Path("id") Integer id, @Path("filt") String filt,@Path("rel") String rel,@Path("val") String val);


    @GET("/api/products/favProduct/{id}")
    Call<ArrayList<GetProduct>> getFavProducts(@Path("id") Integer id);
}
