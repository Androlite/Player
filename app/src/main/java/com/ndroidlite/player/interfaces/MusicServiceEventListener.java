package com.ndroidlite.player.interfaces;

/**
 * Created by chiragpatel on 24-07-2017.
 */

public interface MusicServiceEventListener {
    void onServiceConnected();

    void onServiceDisconnected();

    void onQueueChanged();

    void onPlayingMetaChanged();

    void onPlayStateChanged();

    void onRepeatModeChanged();

    void onShuffleModeChanged();

    void onMediaStoreChanged();
}
