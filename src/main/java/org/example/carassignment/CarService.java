package org.example.carassignment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CarService {
    private static final String API_URL = "https://car-data.p.rapidapi.com/cars?limit=10&page=0";
    private static final String API_KEY = "69777ab306msh505ca15cae9f198p15c351jsn20363828244e";
    private static final String API_HOST = "car-data.p.rapidapi.com";

    public static CompletableFuture<List<Object>> getCars() {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        return client.prepare("GET", API_URL)
                .setHeader("x-rapidapi-key", API_KEY)
                .setHeader("x-rapidapi-host", API_HOST)
                .execute()
                .toCompletableFuture()
                .thenApply(response -> {
                    if (response.getStatusCode() == 200) {
                        return new Gson().fromJson(response.getResponseBody(), new TypeToken<List<Car>>(){}.getType());
                    } else {
                        System.err.println("Error: " + response.getStatusCode());
                        return List.of();
                    }
                })
                .whenComplete((result, throwable) -> {
                    try {
                        client.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
