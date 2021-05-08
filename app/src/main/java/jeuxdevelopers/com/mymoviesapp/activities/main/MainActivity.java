package jeuxdevelopers.com.mymoviesapp.activities.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import jeuxdevelopers.com.mymoviesapp.Utils.NetworkConnection;
import jeuxdevelopers.com.mymoviesapp.R;
import jeuxdevelopers.com.mymoviesapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Context context;
    ActivityMainBinding activityMainBinding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = MainActivity.this;
        navController= Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupWithNavController(activityMainBinding.homeBtmNav,navController);


    }

    @Override
    protected void onStart() {
        super.onStart();

        NetworkConnection networkConnection = new NetworkConnection(getApplicationContext());
        networkConnection.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {

                if (isConnected) {
                    activityMainBinding.networkLayout.setVisibility(View.VISIBLE);
                    activityMainBinding.connectionText.setText("Connected");
                    activityMainBinding.networkLayout.setBackgroundColor(getResources().getColor(R.color.colorConnectedNetwork));

                    new Handler(getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activityMainBinding.networkLayout.setVisibility(View.GONE);

                        }
                    }, 2000);

                } else {
                    activityMainBinding.networkLayout.setVisibility(View.VISIBLE);
                    activityMainBinding.connectionText.setText("Not Connected");

                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}