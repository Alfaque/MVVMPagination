package jeuxdevelopers.com.mymoviesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import jeuxdevelopers.com.mymoviesapp.models.Result;
import jeuxdevelopers.com.mymoviesapp.repository.MovieRepository;

public class MovieViewModel extends AndroidViewModel {
    MovieRepository movieRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public Flowable<Result> getMovie(int id) {
        return movieRepository.getMoive(id);
    }

    public Flowable<List<Result>> getFavMovies() {
        return movieRepository.getFavMovies();
    }

    public Completable deleteMovie(Result result) {
        return movieRepository.deleteMovie(result);
    }

    public Completable addToFav(Result result) {
        return movieRepository.addMovie(result);
    }

}
