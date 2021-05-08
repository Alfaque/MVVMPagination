package jeuxdevelopers.com.mymoviesapp.adapters;

import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter("android:setImage")
    public static void setImage(ImageView imageView, String imageUrl) {

        imageView.setAlpha(0f);
        Picasso.get().load(imageUrl).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                imageView.animate().setDuration(400L).alpha(1f).start();
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }


}
