package com.ndroidlite.player.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndroidlite.player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {


    public FavouriteFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        FavouriteFragment favouriteFragment = new FavouriteFragment();
        Bundle args = new Bundle();
        favouriteFragment.setArguments(args);
        return favouriteFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("My Favourite");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

}