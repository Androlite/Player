package com.ndroidlite.player.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndroidlite.player.R;
import com.ndroidlite.player.custom.TabMenuAdapter;
import com.ndroidlite.player.custom.TfTextView;
import com.ndroidlite.player.fragments.library.AlbumsFragment;
import com.ndroidlite.player.fragments.library.ArtistFragment;
import com.ndroidlite.player.fragments.library.GenresFragment;
import com.ndroidlite.player.fragments.library.PlaylistsFragment;
import com.ndroidlite.player.fragments.library.SongsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends Fragment {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabMenuAdapter tabMenuAdapter;
    private TfTextView txtTitle;

    public LibraryFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        LibraryFragment libraryFragment = new LibraryFragment();
        Bundle args = new Bundle();
        libraryFragment.setArguments(args);
        return libraryFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getResources().getString(R.string.library));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        init(view);

        actionListener();

        return view;
    }




    private void init(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        highLightCurrentTab(0);
    }

    private void clickListener() {

    }

    private void highLightCurrentTab(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(null);
            tab.setCustomView(tabMenuAdapter.getTabView(i));
        }

        TabLayout.Tab tab = tabLayout.getTabAt(position);
        tab.setCustomView(null);
        tab.setCustomView(tabMenuAdapter.getSelectedTabView(position));
    }

    private void setupViewPager(ViewPager viewPager) {
        tabMenuAdapter = new TabMenuAdapter(getFragmentManager(), getActivity());
        tabMenuAdapter.addFragment(new SongsFragment(), getString(R.string.songs_tab));
        tabMenuAdapter.addFragment(new ArtistFragment(), getString(R.string.artist_tab));
        tabMenuAdapter.addFragment(new AlbumsFragment(), getString(R.string.albums_tab));
        tabMenuAdapter.addFragment(new GenresFragment(), getString(R.string.genres_tab));
        tabMenuAdapter.addFragment(new PlaylistsFragment(), getString(R.string.playlists_tab));

        viewPager.setAdapter(tabMenuAdapter);
    }

    private void actionListener() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
