package com.ndroidlite.player.fragments.library;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndroidlite.player.R;
import com.ndroidlite.player.adapter.PlaylistAdapter;
import com.ndroidlite.player.fragments.library.pager.AdlLibraryPagerRecyclerViewFragment;
import com.ndroidlite.player.interfaces.LoaderIds;
import com.ndroidlite.player.loader.PlaylistLoader;
import com.ndroidlite.player.misc.WrappedAsyncTaskLoader;
import com.ndroidlite.player.model.Playlist;
import com.ndroidlite.player.model.smartplaylist.HistoryPlaylist;
import com.ndroidlite.player.model.smartplaylist.LastAddedPlaylist;
import com.ndroidlite.player.model.smartplaylist.MyTopTracksPlaylist;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistsFragment extends AdlLibraryPagerRecyclerViewFragment<PlaylistAdapter, LinearLayoutManager> implements LoaderManager.LoaderCallbacks<ArrayList<Playlist>>  {

    public static final String TAG = PlaylistsFragment.class.getSimpleName();

    private static final int LOADER_ID = LoaderIds.PLAYLISTS_FRAGMENT;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    protected LinearLayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }
    /*public PlaylistsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlists, container, false);
    }*/

    @NonNull
    @Override
    protected PlaylistAdapter createAdapter() {
        ArrayList<Playlist> dataSet = getAdapter() == null ? new ArrayList<Playlist>() : getAdapter().getDataSet();
        return new PlaylistAdapter(getLibraryFragment().getMainActivity(), dataSet, R.layout.item_list_single_row, getLibraryFragment());
    }

    @Override
    protected int getEmptyMessage() {
        return R.string.no_playlists;
    }

    @Override
    public void onMediaStoreChanged() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<Playlist>> onCreateLoader(int id, Bundle args) {
        return new AsyncPlaylistLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Playlist>> loader, ArrayList<Playlist> data) {
        getAdapter().swapDataSet(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Playlist>> loader) {
        getAdapter().swapDataSet(new ArrayList<Playlist>());
    }

    private static class AsyncPlaylistLoader extends WrappedAsyncTaskLoader<ArrayList<Playlist>> {
        public AsyncPlaylistLoader(Context context) {
            super(context);
        }

        private static ArrayList<Playlist> getAllPlaylists(Context context) {
            ArrayList<Playlist> playlists = new ArrayList<>();

            playlists.add(new LastAddedPlaylist(context));
            playlists.add(new HistoryPlaylist(context));
            playlists.add(new MyTopTracksPlaylist(context));

            playlists.addAll(PlaylistLoader.getAllPlaylists(context));

            return playlists;
        }

        @Override
        public ArrayList<Playlist> loadInBackground() {
            return getAllPlaylists(getContext());
        }
    }
}
