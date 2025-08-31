package com.example.eventplanner.model;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private Context context;

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Get the token from SharedPreferences (or EncryptedSharedPreferences)
        String jwtToken = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE)
                .getString("JWT_TOKEN", null);

        // Add the token to the request if it exists
        Request.Builder requestBuilder = chain.request().newBuilder();
        if (jwtToken != null) {
            requestBuilder.addHeader("Authorization", jwtToken);
        }

        requestBuilder.addHeader("User-Agent", "Mobile-Android");
        requestBuilder.addHeader("Content-Type", "application/json");

        return chain.proceed(requestBuilder.build());
    }
}
