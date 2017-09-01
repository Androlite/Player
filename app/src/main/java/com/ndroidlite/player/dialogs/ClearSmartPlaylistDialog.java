package com.ndroidlite.player.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Html;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ndroidlite.player.R;
import com.ndroidlite.player.model.smartplaylist.AdlSmartPlaylist;

/**
 * Created by chiragpatel on 28-08-2017.
 */

public class ClearSmartPlaylistDialog extends DialogFragment {
    @NonNull
    public static ClearSmartPlaylistDialog create(AdlSmartPlaylist playlist) {
        ClearSmartPlaylistDialog dialog = new ClearSmartPlaylistDialog();
        Bundle args = new Bundle();
        args.putParcelable("playlist", playlist);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //noinspection unchecked
        final AdlSmartPlaylist playlist = getArguments().getParcelable("playlist");
        int title = R.string.clear_playlist_title;
        //noinspection ConstantConditions
        CharSequence content = Html.fromHtml(getString(R.string.clear_playlist_x, playlist.name));

        return new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(content)
                .positiveText(R.string.clear_action)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (getActivity() == null) {
                            return;
                        }
                        playlist.clear(getActivity());
                    }
                })
                .build();
    }
}
