package com.ndroidlite.player.helper.menu;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.ndroidlite.player.R;
import com.ndroidlite.player.dialogs.AddToPlaylistDialog;
import com.ndroidlite.player.dialogs.DeleteSongsDialog;
import com.ndroidlite.player.helper.MusicPlayerRemote;
import com.ndroidlite.player.model.Song;

import java.util.ArrayList;

/**
 * Created by chiragpatel on 24-08-2017.
 */

public class SongsMenuHelper {
    public static boolean handleMenuClick(@NonNull FragmentActivity activity, @NonNull ArrayList<Song> songs, int menuItemId) {
        switch (menuItemId) {
            case R.id.action_play_next:
                MusicPlayerRemote.playNext(songs);
                return true;
            case R.id.action_add_to_current_playing:
                MusicPlayerRemote.enqueue(songs);
                return true;
            case R.id.action_add_to_playlist:
                AddToPlaylistDialog.create(songs).show(activity.getSupportFragmentManager(), "ADD_PLAYLIST");
                return true;
            case R.id.action_delete_from_device:
                DeleteSongsDialog.create(songs).show(activity.getSupportFragmentManager(), "DELETE_SONGS");
                return true;
        }
        return false;
    }
}
