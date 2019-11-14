package com.example.discoverfreedom.local.holder;

import android.view.View;
import android.view.ViewGroup;

import com.example.discoverfreedom.database.LocalItem;
import com.example.discoverfreedom.database.playlist.PlaylistMetadataEntry;
import com.example.discoverfreedom.local.LocalItemBuilder;
import com.example.discoverfreedom.local.history.HistoryRecordManager;
import com.example.discoverfreedom.util.ImageDisplayConstants;

import java.text.DateFormat;

public class LocalPlaylistItemHolder extends PlaylistItemHolder {

    public LocalPlaylistItemHolder(LocalItemBuilder infoItemBuilder, ViewGroup parent) {
        super(infoItemBuilder, parent);
    }

    LocalPlaylistItemHolder(LocalItemBuilder infoItemBuilder, int layoutId, ViewGroup parent) {
        super(infoItemBuilder, layoutId, parent);
    }

    @Override
    public void updateFromItem(final LocalItem localItem, HistoryRecordManager historyRecordManager, final DateFormat dateFormat) {
        if (!(localItem instanceof PlaylistMetadataEntry)) return;
        final PlaylistMetadataEntry item = (PlaylistMetadataEntry) localItem;

        itemTitleView.setText(item.name);
        itemStreamCountView.setText(String.valueOf(item.streamCount));
        itemUploaderView.setVisibility(View.INVISIBLE);

        itemBuilder.displayImage(item.thumbnailUrl, itemThumbnailView,
                ImageDisplayConstants.DISPLAY_PLAYLIST_OPTIONS);

        super.updateFromItem(localItem, historyRecordManager, dateFormat);
    }
}
