package com.ndroidlite.player.model.smartplaylist;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import com.ndroidlite.player.R;
import com.ndroidlite.player.loader.TopAndRecentlyPlayedTracksLoader;
import com.ndroidlite.player.model.Song;
import com.ndroidlite.player.provider.HistoryStore;

import java.util.ArrayList;

/**
 * Created by chiragpatel on 28-08-2017.
 */

public class HistoryPlaylist extends AdlSmartPlaylist {

    public HistoryPlaylist(@NonNull Context context) {
        super(context.getString(R.string.history), R.drawable.ic_access_time_white_24dp);
    }

    @NonNull
    @Override
    public ArrayList<Song> getSongs(@NonNull Context context) {
        return TopAndRecentlyPlayedTracksLoader.getRecentlyPlayedTracks(context);
    }

    @Override
    public void clear(@NonNull Context context) {
        HistoryStore.getInstance(context).clear();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected HistoryPlaylist(Parcel in) {
        super(in);
    }

    public static final Creator<HistoryPlaylist> CREATOR = new Creator<HistoryPlaylist>() {
        public HistoryPlaylist createFromParcel(Parcel source) {
            return new HistoryPlaylist(source);
        }

        public HistoryPlaylist[] newArray(int size) {
            return new HistoryPlaylist[size];
        }
    };
}
