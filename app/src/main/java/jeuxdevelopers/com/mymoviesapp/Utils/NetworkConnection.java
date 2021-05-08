package jeuxdevelopers.com.mymoviesapp.Utils;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class NetworkConnection extends LiveData<Boolean> {


    Context context;
    ConnectivityManager connectivityManager;

    public NetworkConnection(Context context) {
        this.context = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    protected void onActive() {
        super.onActive();

        updateConnection();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lollipopNetWorkRequest();
        } else {
            context.registerReceiver(broadcastReceiver,
                    new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            connectivityManager.unregisterNetworkCallback(networkCallback());
//        } else {
//            context.unregisterReceiver(broadcastReceiver);
//        }
    }

    public ConnectivityManager.NetworkCallback networkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {


                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    postValue(true);
                }

                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    postValue(false);
                }
            };

            return networkCallback;
        } else {
            throw new IllegalAccessError("Error");
        }


    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void lollipopNetWorkRequest() {
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
                .build();

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback());
    }

    private void updateConnection() {

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            postValue(networkInfo.isConnected() == true);
        }

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateConnection();
        }
    };

}
