package jeuxdevelopers.com.mymoviesapp.fragments.home;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import jeuxdevelopers.com.mymoviesapp.models.GetMoviesResponse;
import jeuxdevelopers.com.mymoviesapp.models.NetworkResponse;
import jeuxdevelopers.com.mymoviesapp.models.Result;

public class HomeViewModel extends ViewModel {

    DataSourceFactory dataSourceFactory;
    LiveData<NetworkResponse> networkResponseLiveData;
    LiveData<PagedList<Result>> listLiveData;

    public HomeViewModel() {
        dataSourceFactory = new DataSourceFactory();
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setPrefetchDistance(4)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .build();


        listLiveData = (new LivePagedListBuilder(dataSourceFactory, config)).build();

        networkResponseLiveData = Transformations.switchMap(dataSourceFactory.mutableLiveData, new Function<ItemDataSource, LiveData<NetworkResponse>>() {
            @Override
            public LiveData<NetworkResponse> apply(ItemDataSource input) {
                return input.networkResponseMutableLiveData;
            }
        });

    }

    public LiveData<NetworkResponse> getNetworkResponseLiveData() {
        return networkResponseLiveData;
    }
}
