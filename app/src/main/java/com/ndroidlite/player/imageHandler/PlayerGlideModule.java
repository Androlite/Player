package com.ndroidlite.player.imageHandler;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;
import com.ndroidlite.player.imageHandler.artistimage.ArtistImage;
import com.ndroidlite.player.imageHandler.artistimage.ArtistImageLoader;
import com.ndroidlite.player.imageHandler.audioCover.AudioFileCover;
import com.ndroidlite.player.imageHandler.audioCover.AudioFileCoverLoader;

import java.io.InputStream;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class PlayerGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(AudioFileCover.class, InputStream.class, new AudioFileCoverLoader.Factory());
        glide.register(ArtistImage.class, InputStream.class,  new ArtistImageLoader.Factory(context));
    }
}
