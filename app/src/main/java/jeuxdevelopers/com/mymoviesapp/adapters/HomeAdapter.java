package jeuxdevelopers.com.mymoviesapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import jeuxdevelopers.com.mymoviesapp.R;
import jeuxdevelopers.com.mymoviesapp.Utils.Constants;
import jeuxdevelopers.com.mymoviesapp.databinding.ItemMovieLayoutBinding;
import jeuxdevelopers.com.mymoviesapp.interfaces.OnMovieClicked;
import jeuxdevelopers.com.mymoviesapp.models.GetMoviesResponse;
import jeuxdevelopers.com.mymoviesapp.models.Result;

public class HomeAdapter extends PagedListAdapter<Result, HomeAdapter.MyHomeHolder> {
    Context context;
    OnMovieClicked onMovieClicked;

    public HomeAdapter(Context context, OnMovieClicked onMovieClicked) {
        super(diffCallback);
        this.context = context;
        this.onMovieClicked = onMovieClicked;
    }

    @NonNull
    @Override
    public MyHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHomeHolder(ItemMovieLayoutBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHomeHolder holder, int position) {
        Result movie = getItem(position);

//        holder.itemMovieLayoutBinding.setMovieImageUrl("https://image.tmdb.org/t/p/original" + movie.getPoster_path());
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
        holder.itemMovieLayoutBinding.getRoot().setAnimation(animation);


        holder.itemMovieLayoutBinding.setMovieImageUrl(Constants.IMAGE_BASE_URL + movie.getPoster_path());
        holder.itemMovieLayoutBinding.setMovieName(movie.getTitle());

        holder.itemMovieLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMovieClicked.onMovieClickedListener(movie);
            }
        });

    }

    public class MyHomeHolder extends RecyclerView.ViewHolder {
        ItemMovieLayoutBinding itemMovieLayoutBinding;

        public MyHomeHolder(@NonNull ItemMovieLayoutBinding itemMovieLayoutBinding) {
            super(itemMovieLayoutBinding.getRoot());
            this.itemMovieLayoutBinding = itemMovieLayoutBinding;
        }
    }

    public static @NonNull
    DiffUtil.ItemCallback<Result> diffCallback = new DiffUtil.ItemCallback<Result>() {
        @Override
        public boolean areItemsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            return oldItem.equals(newItem);
        }
    };


}
