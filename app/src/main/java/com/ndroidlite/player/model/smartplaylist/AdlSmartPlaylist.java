package com.ndroidlite.player.model.smartplaylist;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.ndroidlite.player.R;
import com.ndroidlite.player.model.AdlCustomPlaylist;

/**
 * Created by chiragpatel on 28-08-2017.
 */

public abstract class AdlSmartPlaylist extends AdlCustomPlaylist {
    @DrawableRes
    public final int iconRes;

    public AdlSmartPlaylist(final String name, final int iconRes) {
        super(-Math.abs(31 * name.hashCode() + (iconRes * name.hashCode() * 31 * 31)), name);
        this.iconRes = iconRes;
    }

    public AdlSmartPlaylist() {
        super();
        this.iconRes = R.drawable.ic_queue_music_white_24dp;
    }

    public abstract void clear(Context context);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + iconRes;
        return result;
    }

    @Override
    public boolean equals(@Nullable final Object obj) {
        if (super.equals(obj)) {
            if (getClass() != obj.getClass()) {
                return false;
            }
            final AdlSmartPlaylist other = (AdlSmartPlaylist) obj;
            return iconRes == other.iconRes;
        }
        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.iconRes);
    }

    protected AdlSmartPlaylist(Parcel in) {
        super(in);
        this.iconRes = in.readInt();
    }
}
