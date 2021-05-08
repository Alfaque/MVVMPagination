package jeuxdevelopers.com.mymoviesapp.fragments.moviedetail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jeuxdevelopers.com.mymoviesapp.R;
import jeuxdevelopers.com.mymoviesapp.Utils.Constants;
import jeuxdevelopers.com.mymoviesapp.databinding.FragmentMovieDetailBinding;
import jeuxdevelopers.com.mymoviesapp.models.GetMovieDetailResponse;
import jeuxdevelopers.com.mymoviesapp.models.Result;
import jeuxdevelopers.com.mymoviesapp.network.ApiInterface;
import jeuxdevelopers.com.mymoviesapp.network.RetrofitClient;
import jeuxdevelopers.com.mymoviesapp.viewmodel.MovieViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailFragment extends Fragment {


    private static final String TAG = "MovieDetailFragment";
    FragmentMovieDetailBinding fragmentMovieDetailBinding;
    Context context;
    Result movieModel;
    GetMovieDetailResponse getMovieDetailResponse;
    boolean isSaved = false;
    MovieViewModel movieViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        fragmentMovieDetailBinding = FragmentMovieDetailBinding.bind(view);
        return fragmentMovieDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        MovieDetailFragmentArgs args = MovieDetailFragmentArgs.fromBundle(getArguments());
        movieModel = new Gson().fromJson(args.getMovieModel(), Result.class);
        Log.d(TAG, "onViewCreated: ");


        checkMovie();

        getMovieDetails(movieModel.getId());
        initViews();


    }

    private void checkMovie() {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(movieViewModel.getMovie(movieModel.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(result -> {
                    Animation likeAnimation = AnimationUtils.loadAnimation(context, R.anim.button_click_anim);
                    fragmentMovieDetailBinding.heartImageview.startAnimation(likeAnimation);
                    isSaved = true;
                    Glide.with(context).load(R.drawable.ic_heart_red).into(fragmentMovieDetailBinding.heartImageview);
                    compositeDisposable.dispose();
                }));

    }

    private void initViews() {
        fragmentMovieDetailBinding.heartImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation likeAnimation = AnimationUtils.loadAnimation(context, R.anim.button_click_anim);
                fragmentMovieDetailBinding.heartImageview.startAnimation(likeAnimation);


                if (isSaved) {

                    movieViewModel.deleteMovie(movieModel);
                    isSaved = false;
                    Glide.with(context).load(R.drawable.ic_heart).into(fragmentMovieDetailBinding.heartImageview);

                    CompositeDisposable compositeDisposable = new CompositeDisposable();
                    compositeDisposable.add(movieViewModel.deleteMovie(movieModel)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(() -> {
                                isSaved = false;
                                Glide.with(context).load(R.drawable.ic_heart).into(fragmentMovieDetailBinding.heartImageview);
                                compositeDisposable.dispose();
                            })
                    );
                } else {

                    CompositeDisposable compositeDisposable = new CompositeDisposable();
                    compositeDisposable.add(movieViewModel.addToFav(movieModel)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(() -> {
                                isSaved = true;
                                Glide.with(context).load(R.drawable.ic_heart_red).into(fragmentMovieDetailBinding.heartImageview);
                                compositeDisposable.dispose();
                            })
                    );

                }
            }
        });

    }

    private void getMovieDetails(int movieId) {
        Call<GetMovieDetailResponse> call = RetrofitClient.getInterface().getMovieDetail(movieId, Constants.API_KEY);
        call.enqueue(new Callback<GetMovieDetailResponse>() {
            @Override
            public void onResponse(Call<GetMovieDetailResponse> call, Response<GetMovieDetailResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    getMovieDetailResponse = response.body();
                    setMovie();
                } else {
                    Toast.makeText(context, "Somthing Went Wrong. Try again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetMovieDetailResponse> call, Throwable t) {
                Toast.makeText(context, "Somthing Went Wrong. Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setMovie() {

        fragmentMovieDetailBinding.moviePoster.setAlpha(0f);
        Glide.with(context).load(Constants.IMAGE_BASE_URL + getMovieDetailResponse.getPoster_path())
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        fragmentMovieDetailBinding.moviePoster.animate().alpha(1f).setDuration(500).start();
                        return false;
                    }
                })
                .into(fragmentMovieDetailBinding.moviePoster);
    }

}