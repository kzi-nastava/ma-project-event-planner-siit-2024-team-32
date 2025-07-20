package com.example.eventplanner.clients;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {

    public static OkHttpClient test(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        return client;
    }
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(test())
            .build();

    public static RegisteredUserService registeredUserService = retrofit.create(RegisteredUserService.class);
    public static EventService eventService = retrofit.create(EventService.class);

}
