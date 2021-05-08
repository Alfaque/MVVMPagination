package jeuxdevelopers.com.mymoviesapp.repository;

import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import jeuxdevelopers.com.mymoviesapp.dao.MovieDao;
import jeuxdevelopers.com.mymoviesapp.database.MovieDataBase;
import jeuxdevelopers.com.mymoviesapp.models.Result;

public class MovieRepository {

    MovieDao movieDao;
    MovieDataBase movieDataBase;

    public MovieRepository(Context context) {
        movieDataBase = MovieDataBase.getInstance(context);
        movieDao = movieDataBase.movieDao();

    }

    public Completable deleteMovie(Result result) {
        return movieDao.removeFromFav(result);
    }

    public Completable addMovie(Result result) {
        return movieDao.addToFav(result);
    }


    public Flowable<Result> getMoive(int id) {
        return movieDao.getMovie(id);
    }

    public Flowable<List<Result>> getFavMovies() {
        return movieDao.getFavMovies();
    }

}
