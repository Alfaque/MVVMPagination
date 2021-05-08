package jeuxdevelopers.com.mymoviesapp.network;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static jeuxdevelopers.com.mymoviesapp.Utils.Constants.BASE_URL;

public class RetrofitClient {
    private static Retrofit retrofit;
    private final String API_KEY = "6bdebd091d11ba6c920d09fb6025b484";

    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiInterface getInterface() {
        return getClient().create(ApiInterface.class);
    }


}
