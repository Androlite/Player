package com.ndroidlite.player.appshortcuts.shortcuttype;

import android.content.Context;
import android.content.pm.ShortcutInfo;

import com.ndroidlite.player.R;
import com.ndroidlite.player.appshortcuts.AppShortcutIconGenerator;
import com.ndroidlite.player.appshortcuts.AppShortcutLauncherActivity;

/**
 * Created by chiragpatel on 28-08-2017.
 */

public final class TopTracksShortcutType extends BaseShortcutType {
    public TopTracksShortcutType(Context context) {
        super(context);
    }

    public static String getId() {
        return ID_PREFIX + "top_tracks";
    }

    public ShortcutInfo getShortcutInfo() {
        return new ShortcutInfo.Builder(context, getId())
                .setShortLabel(context.getString(R.string.app_shortcut_top_tracks_short))
                .setLongLabel(context.getString(R.string.app_shortcut_top_tracks_long))
                .setIcon(AppShortcutIconGenerator.generateThemedIcon(context, R.drawable.ic_app_shortcut_top_tracks))
                .setIntent(getPlaySongsIntent(AppShortcutLauncherActivity.SHORTCUT_TYPE_TOP_TRACKS))
                .build();
    }
}
