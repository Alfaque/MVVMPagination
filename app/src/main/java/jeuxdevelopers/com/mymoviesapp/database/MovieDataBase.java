package jeuxdevelopers.com.mymoviesapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import jeuxdevelopers.com.mymoviesapp.converters.Converters;
import jeuxdevelopers.com.mymoviesapp.dao.MovieDao;
import jeuxdevelopers.com.mymoviesapp.models.Result;

@Database(entities = Result.class, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MovieDataBase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static MovieDataBase movieDataBase;


    public static MovieDataBase getInstance(Context context) {
        if (movieDataBase == null) {
            movieDataBase = Room.databaseBuilder(
                    context,
                    MovieDataBase.class,
                    "MovieDataBase"
            )
                    .fallbackToDestructiveMigration()
//                    .addTypeConverter(new Converters())
                    .build();
        }
        return movieDataBase;
    }


}
