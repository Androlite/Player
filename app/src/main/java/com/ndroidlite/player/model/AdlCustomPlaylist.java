package com.ndroidlite.player.model;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */

public abstract class AdlCustomPlaylist extends Playlist {
    public AdlCustomPlaylist(int id, String name) {
        super(id, name);
    }

    public AdlCustomPlaylist() {
    }

    public AdlCustomPlaylist(Parcel in) {
        super(in);
    }

    @NonNull
    public abstract ArrayList<Song> getSongs(Context context);
}
