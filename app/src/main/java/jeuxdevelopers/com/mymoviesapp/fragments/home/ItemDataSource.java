package jeuxdevelopers.com.mymoviesapp.fragments.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import jeuxdevelopers.com.mymoviesapp.Utils.Constants;
import jeuxdevelopers.com.mymoviesapp.models.GetMoviesResponse;
import jeuxdevelopers.com.mymoviesapp.models.NetworkResponse;
import jeuxdevelopers.com.mymoviesapp.models.Result;
import jeuxdevelopers.com.mymoviesapp.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Result> {

    MutableLiveData<NetworkResponse> networkResponseMutableLiveData = new MutableLiveData<>();
    private final int FIRST_PAGE = 1;


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Result> callback) {
        Call<GetMoviesResponse> getMoviesResponseCall = RetrofitClient.getInterface().getMovies(Constants.API_KEY, FIRST_PAGE);
        networkResponseMutableLiveData.postValue(new NetworkResponse(NetworkResponse.Status.LOADING, ""));
        getMoviesResponseCall.enqueue(new Callback<GetMoviesResponse>() {
            @Override
            public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    networkResponseMutableLiveData.postValue(new NetworkResponse(NetworkResponse.Status.SUCCESS, ""));
                    callback.onResult(response.body().getResults(), null, FIRST_PAGE + 1);
                } else {

                    //
                    networkResponseMutableLiveData.postValue(new NetworkResponse(NetworkResponse.Status.ERROR, "Response is Unsuccesfull"));

                }


            }

            @Override
            public void onFailure(Call<GetMoviesResponse> call, Throwable t) {
                networkResponseMutableLiveData.postValue(new NetworkResponse(NetworkResponse.Status.ERROR, t.getMessage()));

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Result> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Result> callback) {
        Call<GetMoviesResponse> getMoviesResponseCall = RetrofitClient.getInterface().getMovies(Constants.API_KEY, params.key);
        networkResponseMutableLiveData.postValue(new NetworkResponse(NetworkResponse.Status.LOADING, ""));

        getMoviesResponseCall.enqueue(new Callback<GetMoviesResponse>() {
            @Override
            public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {
                if (response.code() == 200 && response.isSuccessful()) {

                    networkResponseMutableLiveData.postValue(new NetworkResponse(NetworkResponse.Status.SUCCESS, ""));
                    callback.onResult(response.body().getResults(), params.key + 1);

                } else {
                    networkResponseMutableLiveData.postValue(new NetworkResponse(NetworkResponse.Status.ERROR,
                            "Response is UnSuccessfull"));
                }
            }

            @Override
            public void onFailure(Call<GetMoviesResponse> call, Throwable t) {
                networkResponseMutableLiveData.postValue(new NetworkResponse(NetworkResponse.Status.ERROR, t.getMessage()));

            }
        });
    }
}
