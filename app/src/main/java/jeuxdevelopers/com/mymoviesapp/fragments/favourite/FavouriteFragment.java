package jeuxdevelopers.com.mymoviesapp.fragments.favourite;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import jeuxdevelopers.com.mymoviesapp.R;
import jeuxdevelopers.com.mymoviesapp.adapters.FavMoviesAdapter;
import jeuxdevelopers.com.mymoviesapp.databinding.FragmentFavouriteBinding;
import jeuxdevelopers.com.mymoviesapp.fragments.home.HomeFragmentDirections;
import jeuxdevelopers.com.mymoviesapp.interfaces.OnMovieClicked;
import jeuxdevelopers.com.mymoviesapp.models.Result;
import jeuxdevelopers.com.mymoviesapp.viewmodel.MovieViewModel;


public class FavouriteFragment extends Fragment implements OnMovieClicked {

    FragmentFavouriteBinding fragmentFavouriteBinding;
    Context context;
    FavMoviesAdapter favMoviesAdapter;
    MovieViewModel movieViewModel;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        fragmentFavouriteBinding = FragmentFavouriteBinding.bind(view);
        return fragmentFavouriteBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        navController = Navigation.findNavController(view);
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        favMoviesAdapter = new FavMoviesAdapter(context, this);
        fragmentFavouriteBinding.favRecyclerview.setLayoutManager(new LinearLayoutManager(context));

        getData();


    }

    private void getData() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(movieViewModel.getFavMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(results -> {
                    Log.d("TAG", "getData: ");
                    favMoviesAdapter.submitList(results);
                }));

        fragmentFavouriteBinding.favRecyclerview.setAdapter(favMoviesAdapter);


    }

    @Override
    public void onMovieClickedListener(Result movie) {
        FavouriteFragmentDirections.ActionFavouriteFragmentToMovieDetailFragment action
                = FavouriteFragmentDirections.actionFavouriteFragmentToMovieDetailFragment();
        action.setMovieModel(new Gson().toJson(movie));
        navController.navigate(action);
    }
}