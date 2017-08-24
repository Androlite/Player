package com.ndroidlite.player.fragments.player;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ndroidlite.player.activity.base.AdlMusicServiceActivity;
import com.ndroidlite.player.interfaces.MusicServiceEventListener;

/**
 * Created by chiragpatel on 25-07-2017.
 */

public class AdlMusicServiceFragment extends Fragment implements MusicServiceEventListener {
    private AdlMusicServiceActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activity = (AdlMusicServiceActivity) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(context.getClass().getSimpleName() + " must be an instance of " + AdlMusicServiceActivity.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.addMusicServiceEventListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity.removeMusicServiceEventListener(this);
    }

    @Override
    public void onPlayingMetaChanged() {

    }

    @Override
    public void onServiceConnected() {

    }

    @Override
    public void onServiceDisconnected() {

    }

    @Override
    public void onQueueChanged() {

    }

    @Override
    public void onPlayStateChanged() {

    }

    @Override
    public void onRepeatModeChanged() {

    }

    @Override
    public void onShuffleModeChanged() {

    }

    @Override
    public void onMediaStoreChanged() {

    }
}
