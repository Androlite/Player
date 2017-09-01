package com.ndroidlite.player.model.smartplaylist;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import com.ndroidlite.player.R;
import com.ndroidlite.player.loader.SongLoader;
import com.ndroidlite.player.model.Song;

import java.util.ArrayList;

/**
 * Created by chiragpatel on 28-08-2017.
 */

public class ShuffleAllPlaylist extends AdlSmartPlaylist {

    public ShuffleAllPlaylist(@NonNull Context context) {
        super(context.getString(R.string.action_shuffle_all), R.drawable.ic_shuffle_white_24dp);
    }

    @NonNull
    @Override
    public ArrayList<Song> getSongs(@NonNull Context context) {
        return SongLoader.getAllSongs(context);
    }

    @Override
    public void clear(@NonNull Context context) {
        // Shuffle all is not a real "Smart Playlist"
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected ShuffleAllPlaylist(Parcel in) {
        super(in);
    }

    public static final Creator<ShuffleAllPlaylist> CREATOR = new Creator<ShuffleAllPlaylist>() {
        public ShuffleAllPlaylist createFromParcel(Parcel source) {
            return new ShuffleAllPlaylist(source);
        }

        public ShuffleAllPlaylist[] newArray(int size) {
            return new ShuffleAllPlaylist[size];
        }
    };
}
