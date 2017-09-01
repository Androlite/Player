package com.ndroidlite.player.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialcab.MaterialCab;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import com.kabouzeid.appthemehelper.ThemeStore;
import com.ndroidlite.player.R;
import com.ndroidlite.player.activity.MainActivity;
import com.ndroidlite.player.activity.PlaylistDetailActivity;
import com.ndroidlite.player.adapter.SongAdapter;
import com.ndroidlite.player.adapter.song.CustomPlaylistSongAdapter;
import com.ndroidlite.player.adapter.song.PlaylistSongAdapter;
import com.ndroidlite.player.dialogs.SleepTimerDialog;
import com.ndroidlite.player.helper.MusicPlayerRemote;
import com.ndroidlite.player.interfaces.CabHolder;
import com.ndroidlite.player.interfaces.LoaderIds;
import com.ndroidlite.player.loader.PlaylistSongLoader;
import com.ndroidlite.player.misc.WrappedAsyncTaskLoader;
import com.ndroidlite.player.model.AdlCustomPlaylist;
import com.ndroidlite.player.model.Playlist;
import com.ndroidlite.player.model.PlaylistSong;
import com.ndroidlite.player.model.Song;
import com.ndroidlite.player.utils.MusicUtil;
import com.ndroidlite.player.utils.NavigationUtil;
import com.ndroidlite.player.utils.PlayerColorUtil;
import com.ndroidlite.player.utils.PlaylistsUtil;
import com.ndroidlite.player.utils.ViewUtil;
import com.ndroidlite.player.views.BreadCrumbLayout;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends AdlMainActivityFragment implements CabHolder, MainActivity.MainActivityFragmentCallbacks, AppBarLayout.OnOffsetChangedListener, LoaderManager.LoaderCallbacks<ArrayList<Song>> {

    public static final String TAG = FavouriteFragment.class.getSimpleName();

    private static final int LOADER_ID = LoaderIds.FAVOURITE_FRAGMENT;
    private MaterialCab cab;
    private Toolbar toolbar;
    private AppBarLayout appbar;
    private TextView empty;
    private FastScrollRecyclerView recyclerView;
    private SongAdapter adapter;
    private Playlist playlist;
    private View contain;
    private RecyclerView.Adapter wrappedAdapter;
    private RecyclerViewDragDropManager recyclerViewDragDropManager;

    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        } else {
            getLoaderManager().initLoader(LOADER_ID, null, this);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        contain = view.findViewById(R.id.container);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        appbar = (AppBarLayout) view.findViewById(R.id.appbar);
        empty = (TextView) view.findViewById(R.id.empty);
        recyclerView = (FastScrollRecyclerView) view.findViewById(R.id.recycler_view);

        playlist = MusicUtil.getFavoritesPlaylist(getActivity());
        Log.e("fav", playlist.toString());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setStatusbarColorAuto(view);
        getMainActivity().setNavigationbarColorAuto();
        getMainActivity().setTaskDescriptionColorAuto();

        setUpToolbar();

        setUpRecyclerView();

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void setUpToolbar() {
        int primaryColor = ThemeStore.primaryColor(getActivity());
        appbar.setBackgroundColor(primaryColor);
        toolbar.setBackgroundColor(primaryColor);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        getActivity().setTitle(R.string.favorites);
        getMainActivity().setSupportActionBar(toolbar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_playlist_detail, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sleep_timer:
                new SleepTimerDialog().show(getFragmentManager(), "SET_SLEEP_TIMER");
                return true;
            case R.id.action_shuffle_playlist:
                MusicPlayerRemote.openAndShuffleQueue(adapter.getDataSet(), true);
                return true;
            case R.id.action_equalizer:
                NavigationUtil.openEqualizer(getMainActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpRecyclerView() {
        ViewUtil.setUpFastScrollRecyclerViewColor(getActivity(), recyclerView, ThemeStore.accentColor(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (playlist instanceof AdlCustomPlaylist) {
            adapter = new CustomPlaylistSongAdapter(getMainActivity(), new ArrayList<Song>(), R.layout.item_list, false, this);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerViewDragDropManager = new RecyclerViewDragDropManager();
            final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();
            adapter = new PlaylistSongAdapter(getMainActivity(), new ArrayList<PlaylistSong>(), R.layout.item_list, false, this, new PlaylistSongAdapter.OnMoveItemListener() {
                @Override
                public void onMoveItem(int fromPosition, int toPosition) {
                    if (PlaylistsUtil.moveItem(getMainActivity(), playlist.id, fromPosition, toPosition)) {
                        Song song = adapter.getDataSet().remove(fromPosition);
                        adapter.getDataSet().add(toPosition, song);
                        adapter.notifyItemMoved(fromPosition, toPosition);
                    }
                }
            });

            wrappedAdapter = recyclerViewDragDropManager.createWrappedAdapter(adapter);

            recyclerView.setAdapter(wrappedAdapter);
            recyclerView.setItemAnimator(animator);

            recyclerViewDragDropManager.attachRecyclerView(recyclerView);

        }

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkIsEmpty();
            }
        });
    }

    @NonNull
    @Override
    public MaterialCab openCab(final int menuRes, final MaterialCab.Callback callback) {
        if (cab != null && cab.isActive()) cab.finish();
        cab = new MaterialCab(getMainActivity(), R.id.cab_stub)
                .setMenu(menuRes)
                .setCloseDrawableRes(R.drawable.ic_close_white_24dp)
                .setBackgroundColor(PlayerColorUtil.shiftBackgroundColorForLightText(ThemeStore.primaryColor(getActivity())))
                .start(callback);
        return cab;
    }

    @Override
    public void onDestroyView() {
        appbar.removeOnOffsetChangedListener(this);
        super.onDestroyView();
    }


    private void checkIsEmpty() {
        empty.setVisibility(
                adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE
        );
    }

    @Override
    public void onPause() {
        if (recyclerViewDragDropManager != null) {
            recyclerViewDragDropManager.cancelDrag();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (recyclerViewDragDropManager != null) {
            recyclerViewDragDropManager.release();
            recyclerViewDragDropManager = null;
        }

        if (recyclerView != null) {
            recyclerView.setItemAnimator(null);
            recyclerView.setAdapter(null);
            recyclerView = null;
        }

        if (wrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(wrappedAdapter);
            wrappedAdapter = null;
        }
        adapter = null;

        super.onDestroy();
    }

    @Override
    public boolean handleBackPress() {
        if (cab != null && cab.isActive()) {
            cab.finish();
            return true;
        }
        return false;
    }

    @Override
    public Loader<ArrayList<Song>> onCreateLoader(int id, Bundle args) {
        return new AsyncPlaylistSongLoader(getMainActivity(), playlist);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Song>> loader, ArrayList<Song> data) {
        if (adapter != null)
            adapter.swapDataSet(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Song>> loader) {
        if (adapter != null)
            adapter.swapDataSet(new ArrayList<Song>());
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        contain.setPadding(contain.getPaddingLeft(), contain.getPaddingTop(), contain.getPaddingRight(), appbar.getTotalScrollRange() + verticalOffset);
    }

    private static class AsyncPlaylistSongLoader extends WrappedAsyncTaskLoader<ArrayList<Song>> {
        private final Playlist playlist;

        public AsyncPlaylistSongLoader(Context context, Playlist playlist) {
            super(context);
            this.playlist = playlist;
        }

        @Override
        public ArrayList<Song> loadInBackground() {
            if (playlist instanceof AdlCustomPlaylist) {
                return ((AdlCustomPlaylist) playlist).getSongs(getContext());
            } else {
                //noinspection unchecked
                return (ArrayList<Song>) (List) PlaylistSongLoader.getPlaylistSongList(getContext(), playlist.id);
            }
        }
    }
}
