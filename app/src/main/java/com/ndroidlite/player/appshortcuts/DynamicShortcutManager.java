package com.ndroidlite.player.appshortcuts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;

import com.ndroidlite.player.appshortcuts.shortcuttype.LastAddedShortcutType;
import com.ndroidlite.player.appshortcuts.shortcuttype.ShuffleAllShortcutType;
import com.ndroidlite.player.appshortcuts.shortcuttype.TopTracksShortcutType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chiragpatel on 28-08-2017.
 */

public class DynamicShortcutManager {
    private Context context;
    private ShortcutManager shortcutManager;

    public DynamicShortcutManager(Context context) {
        this.context = context;
        shortcutManager = this.context.getSystemService(ShortcutManager.class);
    }

    public static ShortcutInfo createShortcut(Context context, String id, String shortLabel, String longLabel, Icon icon, Intent intent) {
        return new ShortcutInfo.Builder(context, id)
                .setShortLabel(shortLabel)
                .setLongLabel(longLabel)
                .setIcon(icon)
                .setIntent(intent)
                .build();
    }

    public void initDynamicShortcuts() {
        if (shortcutManager.getDynamicShortcuts().size() == 0) {
            shortcutManager.setDynamicShortcuts(getDefaultShortcuts());
        }
    }

    public void updateDynamicShortcuts() {
        shortcutManager.updateShortcuts(getDefaultShortcuts());
    }

    public List<ShortcutInfo> getDefaultShortcuts() {
        return (Arrays.asList(
                new ShuffleAllShortcutType(context).getShortcutInfo(),
                new TopTracksShortcutType(context).getShortcutInfo(),
                new LastAddedShortcutType(context).getShortcutInfo()
        ));
    }

    public static void reportShortcutUsed(Context context, String shortcutId){
        context.getSystemService(ShortcutManager.class).reportShortcutUsed(shortcutId);
    }
}
