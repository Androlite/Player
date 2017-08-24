package com.ndroidlite.player.interfaces;

import android.support.annotation.NonNull;

import com.afollestad.materialcab.MaterialCab;

/**
 * Created by chiragpatel on 26-07-2017.
 */

public interface CabHolder {
    @NonNull
    MaterialCab openCab(final int menuRes, final MaterialCab.Callback callback);
}
