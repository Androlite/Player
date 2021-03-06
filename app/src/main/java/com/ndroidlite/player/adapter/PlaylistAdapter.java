package com.ndroidlite.player.adapter;

import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.kabouzeid.appthemehelper.util.ATHUtil;
import com.ndroidlite.player.R;
import com.ndroidlite.player.adapter.base.AdlMultiSelectAdapter;
import com.ndroidlite.player.adapter.base.MediaEntryViewHolder;
import com.ndroidlite.player.dialogs.ClearSmartPlaylistDialog;
import com.ndroidlite.player.dialogs.DeletePlaylistDialog;
import com.ndroidlite.player.helper.menu.PlaylistMenuHelper;
import com.ndroidlite.player.helper.menu.SongsMenuHelper;
import com.ndroidlite.player.interfaces.CabHolder;
import com.ndroidlite.player.loader.PlaylistSongLoader;
import com.ndroidlite.player.model.AdlCustomPlaylist;
import com.ndroidlite.player.model.Playlist;
import com.ndroidlite.player.model.Song;
import com.ndroidlite.player.model.smartplaylist.AdlSmartPlaylist;
import com.ndroidlite.player.model.smartplaylist.LastAddedPlaylist;
import com.ndroidlite.player.utils.MusicUtil;
import com.ndroidlite.player.utils.NavigationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chiragpatel on 28-08-2017.
 */

public class PlaylistAdapter extends AdlMultiSelectAdapter<PlaylistAdapter.ViewHolder, Playlist> {
    public static final String TAG = PlaylistAdapter.class.getSimpleName();

    private static final int SMART_PLAYLIST = 0;
    private static final int DEFAULT_PLAYLIST = 1;

    protected final AppCompatActivity activity;
    protected ArrayList<Playlist> dataSet;
    protected int itemLayoutRes;

    public PlaylistAdapter(AppCompatActivity activity, ArrayList<Playlist> dataSet, @LayoutRes int itemLayoutRes, @Nullable CabHolder cabHolder) {
        super(activity, cabHolder, R.menu.menu_playlists_selection);
        this.activity = activity;
        this.dataSet = dataSet;
        this.itemLayoutRes = itemLayoutRes;
        setHasStableIds(true);
    }

    public ArrayList<Playlist> getDataSet() {
        return dataSet;
    }

    public void swapDataSet(ArrayList<Playlist> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return dataSet.get(position).id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(itemLayoutRes, parent, false);
        return createViewHolder(view, viewType);
    }

    protected ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Playlist playlist = dataSet.get(position);

        holder.itemView.setActivated(isChecked(playlist));

        if (holder.title != null) {
            holder.title.setText(playlist.name);
        }

        if (holder.getAdapterPosition() == getItemCount() - 1) {
            if (holder.shortSeparator != null) {
                holder.shortSeparator.setVisibility(View.GONE);
            }
        } else {
            if (holder.shortSeparator != null && !(dataSet.get(position) instanceof AdlSmartPlaylist)) {
                holder.shortSeparator.setVisibility(View.VISIBLE);
            }
        }

        if (holder.image != null) {
            holder.image.setImageResource(getIconRes(playlist));
        }
    }

    private int getIconRes(Playlist playlist) {
        if (playlist instanceof AdlSmartPlaylist) {
            return ((AdlSmartPlaylist) playlist).iconRes;
        }
        return MusicUtil.isFavoritePlaylist(activity, playlist) ? R.drawable.ic_favorite_white_24dp : R.drawable.ic_queue_music_white_24dp;
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position) instanceof AdlSmartPlaylist ? SMART_PLAYLIST : DEFAULT_PLAYLIST;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    protected Playlist getIdentifier(int position) {
        return dataSet.get(position);
    }

    @Override
    protected String getName(Playlist playlist) {
        return playlist.name;
    }

    @Override
    protected void onMultipleItemAction(@NonNull MenuItem menuItem, @NonNull ArrayList<Playlist> selection) {
        switch (menuItem.getItemId()) {
            case R.id.action_delete_playlist:
                for (int i = 0; i < selection.size(); i++) {
                    Playlist playlist = selection.get(i);
                    if (playlist instanceof AdlSmartPlaylist) {
                        AdlSmartPlaylist absSmartPlaylist = (AdlSmartPlaylist) playlist;
                        ClearSmartPlaylistDialog.create(absSmartPlaylist).show(activity.getSupportFragmentManager(), "CLEAR_PLAYLIST_" + absSmartPlaylist.name);
                        selection.remove(playlist);
                        i--;
                    }
                }
                if (selection.size() > 0) {
                    DeletePlaylistDialog.create(selection).show(activity.getSupportFragmentManager(), "DELETE_PLAYLIST");
                }
                break;
            default:
                SongsMenuHelper.handleMenuClick(activity, getSongList(selection), menuItem.getItemId());
                break;
        }
    }

    @NonNull
    private ArrayList<Song> getSongList(@NonNull List<Playlist> playlists) {
        final ArrayList<Song> songs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            if (playlist instanceof AdlCustomPlaylist) {
                songs.addAll(((AdlCustomPlaylist) playlist).getSongs(activity));
            } else {
                songs.addAll(PlaylistSongLoader.getPlaylistSongList(activity, playlist.id));
            }
        }
        return songs;
    }

    public class ViewHolder extends MediaEntryViewHolder {

        public ViewHolder(@NonNull View itemView, int itemViewType) {
            super(itemView);

            if (itemViewType == SMART_PLAYLIST) {
                if (shortSeparator != null) {
                    shortSeparator.setVisibility(View.GONE);
                }
                itemView.setBackgroundColor(ATHUtil.resolveColor(activity, R.attr.cardBackgroundColor));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    itemView.setElevation(activity.getResources().getDimensionPixelSize(R.dimen.dimen_2dp));
                }
            }

            if (image != null) {
                int iconPadding = activity.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
                image.setPadding(iconPadding, iconPadding, iconPadding, iconPadding);
                image.setColorFilter(ATHUtil.resolveColor(activity, R.attr.iconColor), PorterDuff.Mode.SRC_IN);
            }

            if (menu != null) {
                menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Playlist playlist = dataSet.get(getAdapterPosition());
                        final PopupMenu popupMenu = new PopupMenu(activity, view);
                        popupMenu.inflate(getItemViewType() == SMART_PLAYLIST ? R.menu.menu_item_smart_playlist : R.menu.menu_item_playlist);
                        if (playlist instanceof LastAddedPlaylist) {
                            popupMenu.getMenu().findItem(R.id.action_clear_playlist).setVisible(false);
                        }
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(@NonNull MenuItem item) {
                                if (item.getItemId() == R.id.action_clear_playlist) {
                                    if (playlist instanceof AdlSmartPlaylist) {
                                        ClearSmartPlaylistDialog.create((AdlSmartPlaylist) playlist).show(activity.getSupportFragmentManager(), "CLEAR_SMART_PLAYLIST_" + playlist.name);
                                        return true;
                                    }
                                }
                                return PlaylistMenuHelper.handleMenuClick(
                                        activity, dataSet.get(getAdapterPosition()), item);
                            }
                        });
                        popupMenu.show();
                    }
                });
            }
        }

        @Override
        public void onClick(View view) {
            if (isInQuickSelectMode()) {
                toggleChecked(getAdapterPosition());
            } else {
                Playlist playlist = dataSet.get(getAdapterPosition());
                Log.e("list Adapter", playlist.toString());
                NavigationUtil.goToPlaylist(activity, playlist);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            toggleChecked(getAdapterPosition());
            return true;
        }
    }
}
