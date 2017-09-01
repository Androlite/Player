package com.ndroidlite.player.fragments.library;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;

import com.ndroidlite.player.R;
import com.ndroidlite.player.adapter.artist.ArtistAdapter;
import com.ndroidlite.player.fragments.library.pager.AdlLibraryPagerRecyclerViewCustomGridSizeFragment;
import com.ndroidlite.player.interfaces.LoaderIds;
import com.ndroidlite.player.loader.ArtistLoader;
import com.ndroidlite.player.misc.WrappedAsyncTaskLoader;
import com.ndroidlite.player.model.Artist;
import com.ndroidlite.player.utils.PreferenceUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends AdlLibraryPagerRecyclerViewCustomGridSizeFragment<ArtistAdapter, GridLayoutManager> implements LoaderManager.LoaderCallbacks<ArrayList<Artist>> {

    public static final String TAG = ArtistFragment.class.getSimpleName();

    private static final int LOADER_ID = LoaderIds.ARTISTS_FRAGMENT;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    protected GridLayoutManager createLayoutManager() {
        return new GridLayoutManager(getActivity(), getGridSize());
    }

   /* public ArtistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }*/

    @NonNull
    @Override
    protected ArtistAdapter createAdapter() {
        int itemLayoutRes = getItemLayoutRes();
        notifyLayoutResChanged(itemLayoutRes);
        ArrayList<Artist> dataSet = getAdapter() == null ? new ArrayList<Artist>() : getAdapter().getDataSet();
        return new ArtistAdapter(
                getLibraryFragment().getMainActivity(),
                dataSet,
                itemLayoutRes,
                loadUsePalette(),
                getLibraryFragment());
    }

    @Override
    protected int getEmptyMessage() {
        return R.string.no_artists;
    }

    @Override
    public void onMediaStoreChanged() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    protected int loadGridSize() {
        return PreferenceUtil.getInstance(getActivity()).getArtistGridSize(getActivity());
    }

    @Override
    protected void saveGridSize(int gridSize) {
        PreferenceUtil.getInstance(getActivity()).setArtistGridSize(gridSize);
    }

    @Override
    protected int loadGridSizeLand() {
        return PreferenceUtil.getInstance(getActivity()).getArtistGridSizeLand(getActivity());
    }

    @Override
    protected void saveGridSizeLand(int gridSize) {
        PreferenceUtil.getInstance(getActivity()).setArtistGridSizeLand(gridSize);
    }

    @Override
    protected void saveUsePalette(boolean usePalette) {
        PreferenceUtil.getInstance(getActivity()).setArtistColoredFooters(usePalette);
    }

    @Override
    public boolean loadUsePalette() {
        return PreferenceUtil.getInstance(getActivity()).artistColoredFooters();
    }

    @Override
    protected void setUsePalette(boolean usePalette) {
        getAdapter().usePalette(usePalette);
    }

    @Override
    protected void setGridSize(int gridSize) {
        getLayoutManager().setSpanCount(gridSize);
        getAdapter().notifyDataSetChanged();
    }


    @Override
    public Loader<ArrayList<Artist>> onCreateLoader(int id, Bundle args) {
        return new AsyncArtistLoader(getActivity());
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<Artist>> loader, ArrayList<Artist> data) {
        getAdapter().swapDataSet(data);
    }


    @Override
    public void onLoaderReset(Loader<ArrayList<Artist>> loader) {
        getAdapter().swapDataSet(new ArrayList<Artist>());
    }

    private static class AsyncArtistLoader extends WrappedAsyncTaskLoader<ArrayList<Artist>> {
        public AsyncArtistLoader(Context context) {
            super(context);
        }

        @Override
        public ArrayList<Artist> loadInBackground() {
            return ArtistLoader.getAllArtists(getContext());
        }
    }
}
