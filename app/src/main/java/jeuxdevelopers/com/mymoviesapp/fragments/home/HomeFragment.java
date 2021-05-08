package jeuxdevelopers.com.mymoviesapp.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import jeuxdevelopers.com.mymoviesapp.R;
import jeuxdevelopers.com.mymoviesapp.adapters.HomeAdapter;
import jeuxdevelopers.com.mymoviesapp.databinding.FragmentHomeBinding;
import jeuxdevelopers.com.mymoviesapp.interfaces.OnMovieClicked;
import jeuxdevelopers.com.mymoviesapp.models.GetMoviesResponse;
import jeuxdevelopers.com.mymoviesapp.models.NetworkResponse;
import jeuxdevelopers.com.mymoviesapp.models.Result;


public class HomeFragment extends Fragment implements OnMovieClicked {

    HomeAdapter homeAdapter;
    FragmentHomeBinding fragmentHomeBinding;
    HomeViewModel homeViewModel;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        fragmentHomeBinding = FragmentHomeBinding.bind(view);
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        homeAdapter = new HomeAdapter(getActivity(), this);
        fragmentHomeBinding.homefragRecylcerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        homeViewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<Result>>() {
            @Override
            public void onChanged(PagedList<Result> results) {
                homeAdapter.submitList(results);
            }
        });
        fragmentHomeBinding.homefragRecylcerview.setAdapter(homeAdapter);

        homeViewModel.getNetworkResponseLiveData().observe(getViewLifecycleOwner(), new Observer<NetworkResponse>() {
            @Override
            public void onChanged(NetworkResponse networkResponse) {
                Toast.makeText(getActivity(), String.valueOf(networkResponse.getStatus()), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onMovieClickedListener(Result movie) {

        HomeFragmentDirections.ActionHomeFragmentToMovieDetailFragment action = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment();
        action.setMovieModel(new Gson().toJson(movie));
        navController.navigate(action);

    }
}