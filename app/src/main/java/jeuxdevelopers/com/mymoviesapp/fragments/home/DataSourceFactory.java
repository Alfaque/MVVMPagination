package jeuxdevelopers.com.mymoviesapp.fragments.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import jeuxdevelopers.com.mymoviesapp.models.Result;


public class DataSourceFactory extends DataSource.Factory<Integer, Result> {


    MutableLiveData<ItemDataSource> mutableLiveData = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, Result> create() {
        ItemDataSource itemDataSource = new ItemDataSource();
        mutableLiveData.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<ItemDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setMutableLiveData(MutableLiveData<ItemDataSource> mutableLiveData) {
        this.mutableLiveData = mutableLiveData;
    }
}
