package com.ndroidlite.player.adapter.base;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ndroidlite.player.R;

/**
 * Created by chiragpatel on 26-07-2017.
 */

public class MediaEntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView text;
    public ImageView image;
    public TextView imageText;
    public TextView title;
    public View menu;
    public View separator;
    public View shortSeparator;
    public View dragView;
    public View paletteColorContainer;

    public MediaEntryViewHolder(View itemView) {
        super(itemView);

        image = (ImageView) itemView.findViewById(R.id.image);
        imageText = (TextView) itemView.findViewById(R.id.image_text);
        title = (TextView) itemView.findViewById(R.id.title);
        text = (TextView) itemView.findViewById(R.id.text);
        menu = (View) itemView.findViewById(R.id.menu);
        separator = (View) itemView.findViewById(R.id.separator);
        shortSeparator = (View) itemView.findViewById(R.id.short_separator);
        dragView = (View) itemView.findViewById(R.id.drag_view);
        paletteColorContainer = (View) itemView.findViewById(R.id.palette_color_container);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    protected void setImageTransitionName(@NonNull String transitionName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && image != null) {
            image.setTransitionName(transitionName);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
