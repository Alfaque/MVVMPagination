package jeuxdevelopers.com.mymoviesapp.converters;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@ProvidedTypeConverter
public class Converters {

    @TypeConverter
    public static String from_IntList_ToSting(List<Integer> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static List<Integer> from_String_ToIntList(String list) {
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(list, type);
    }
}
