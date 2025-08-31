package com.example.eventplanner.clients;

import android.content.Context;
import android.util.Log;

import com.example.eventplanner.model.AuthInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {

    private static Context appContext;
    public static Retrofit retrofit;

    public static RegisteredUserService registeredUserService;
    public static EventService eventService;
    public static ServiceService serviceService;
    public static ProductService productService;
    public static void init(Context context){
        appContext = context;
        retrofit= new Retrofit.Builder()
                .baseUrl("http://192.168.0.3:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(test())
                .build();
        registeredUserService = retrofit.create(RegisteredUserService.class);
        eventService = retrofit.create(EventService.class);
        serviceService = retrofit.create(ServiceService.class);
        productService=retrofit.create(ProductService.class);
    }
    public static OkHttpClient test(){
        //HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Log.d("REZULTATIC","Contekst je: " + (appContext==null));

        AuthInterceptor authInterceptor = new AuthInterceptor(appContext);

        /*OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();*/

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor).build();

        return client;
    }

}
