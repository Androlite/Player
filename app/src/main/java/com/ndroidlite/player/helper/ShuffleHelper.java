package com.ndroidlite.player.helper;

import android.support.annotation.NonNull;

import com.ndroidlite.player.model.Song;

import java.util.Collections;
import java.util.List;

/**
 * Created by chiragpatel on 25-07-2017.
 */

public class ShuffleHelper {
    public static void makeShuffleList(@NonNull List<Song> listToShuffle, final int current) {
        if (listToShuffle.isEmpty()) return;
        if (current >= 0) {
            Song song = listToShuffle.remove(current);
            Collections.shuffle(listToShuffle);
            listToShuffle.add(0, song);
        } else {
            Collections.shuffle(listToShuffle);
        }
    }
}
