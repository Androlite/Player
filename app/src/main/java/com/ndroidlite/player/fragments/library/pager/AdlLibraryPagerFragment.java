package com.ndroidlite.player.fragments.library.pager;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;

import com.ndroidlite.player.fragments.LibraryFragment;
import com.ndroidlite.player.fragments.player.AdlMusicServiceFragment;

/**
 * Created by chiragpatel on 25-08-2017.
 */

public class AdlLibraryPagerFragment extends AdlMusicServiceFragment {
    /* http://stackoverflow.com/a/2888433 */
    @Override
    public LoaderManager getLoaderManager() {
        return getParentFragment().getLoaderManager();
    }

    public LibraryFragment getLibraryFragment() {
        return (LibraryFragment) getParentFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
}
