package jeuxdevelopers.com.mymoviesapp.network;

import jeuxdevelopers.com.mymoviesapp.models.GetMovieDetailResponse;
import jeuxdevelopers.com.mymoviesapp.models.GetMoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top_rated")
    Call<GetMoviesResponse> getMovies(
            @Query("api_key") String api_key,
            @Query("page") int page

    );

    @GET("{movieId}")
    Call<GetMovieDetailResponse> getMovieDetail(
            @Path("movieId") int movieId,
            @Query("api_key") String api_key
    );


}
