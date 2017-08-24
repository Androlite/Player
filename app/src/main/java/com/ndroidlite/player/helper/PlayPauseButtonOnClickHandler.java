package com.ndroidlite.player.helper;

import android.view.View;

/**
 * Created by chiragpatel on 25-07-2017.
 */

public class PlayPauseButtonOnClickHandler implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if (MusicPlayerRemote.isPlaying()) {
            MusicPlayerRemote.pauseSong();
        } else {
            MusicPlayerRemote.resumePlaying();
        }
    }
}
