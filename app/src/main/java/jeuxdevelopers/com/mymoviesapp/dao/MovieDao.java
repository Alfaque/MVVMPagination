package jeuxdevelopers.com.mymoviesapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import jeuxdevelopers.com.mymoviesapp.models.Result;

@Dao
public interface MovieDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToFav(Result result);

    @Delete
    Completable removeFromFav(Result result);

    @Query("SELECT * FROM Movie_table WHERE id=:id")
    Flowable<Result> getMovie(int id);

    @Query("SELECT * FROM Movie_Table")
    Flowable<List<Result>> getFavMovies();

}
