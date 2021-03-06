package com.ndroidlite.player.adapter.song;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.ndroidlite.player.R;
import com.ndroidlite.player.adapter.SongAdapter;
import com.ndroidlite.player.interfaces.CabHolder;
import com.ndroidlite.player.model.Song;
import com.ndroidlite.player.utils.NavigationUtil;

import java.util.ArrayList;

/**
 * Created by chiragpatel on 29-08-2017.
 */

public class CustomPlaylistSongAdapter extends SongAdapter {
    public static final String TAG = CustomPlaylistSongAdapter.class.getSimpleName();

    public CustomPlaylistSongAdapter(AppCompatActivity activity, @NonNull ArrayList<Song> dataSet, @LayoutRes int itemLayoutRes, boolean usePalette, @Nullable CabHolder cabHolder) {
        super(activity, dataSet, itemLayoutRes, usePalette, cabHolder, false);
        overrideMultiSelectMenuRes(R.menu.menu_cannot_delete_single_songs_playlist_songs_selection);
    }

    @Override
    protected SongAdapter.ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends SongAdapter.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected int getSongMenuRes() {
            return R.menu.menu_item_cannot_delete_single_songs_playlist_song;
        }

        @Override
        protected boolean onSongMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.action_go_to_album) {
                Pair[] albumPairs = new Pair[]{
                        Pair.create(image, activity.getString(R.string.transition_album_art))
                };
                NavigationUtil.goToAlbum(activity, dataSet.get(getAdapterPosition()).albumId, albumPairs);
                return true;
            }
            return super.onSongMenuItemClick(item);
        }
    }
}
