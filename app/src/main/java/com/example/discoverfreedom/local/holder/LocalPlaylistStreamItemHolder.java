package com.example.discoverfreedom.local.holder;

import androidx.core.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.discoverfreedom.R;
import com.example.discoverfreedom.database.LocalItem;
import com.example.discoverfreedom.database.playlist.PlaylistStreamEntry;
import com.example.discoverfreedom.database.stream.model.StreamStateEntity;
import org.schabi.newpipe.extractor.NewPipe;
import com.example.discoverfreedom.local.LocalItemBuilder;
import com.example.discoverfreedom.local.history.HistoryRecordManager;
import com.example.discoverfreedom.util.AnimationUtils;
import com.example.discoverfreedom.util.ImageDisplayConstants;
import com.example.discoverfreedom.util.Localization;
import com.example.discoverfreedom.views.AnimatedProgressBar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LocalPlaylistStreamItemHolder extends LocalItemHolder {

    public final ImageView itemThumbnailView;
    public final TextView itemVideoTitleView;
    public final TextView itemAdditionalDetailsView;
    public final TextView itemDurationView;
    public final View itemHandleView;
    public final AnimatedProgressBar itemProgressView;

    LocalPlaylistStreamItemHolder(LocalItemBuilder infoItemBuilder, int layoutId, ViewGroup parent) {
        super(infoItemBuilder, layoutId, parent);

        itemThumbnailView = itemView.findViewById(R.id.itemThumbnailView);
        itemVideoTitleView = itemView.findViewById(R.id.itemVideoTitleView);
        itemAdditionalDetailsView = itemView.findViewById(R.id.itemAdditionalDetails);
        itemDurationView = itemView.findViewById(R.id.itemDurationView);
        itemHandleView = itemView.findViewById(R.id.itemHandle);
        itemProgressView = itemView.findViewById(R.id.itemProgressView);
    }

    public LocalPlaylistStreamItemHolder(LocalItemBuilder infoItemBuilder, ViewGroup parent) {
        this(infoItemBuilder, R.layout.list_stream_playlist_item, parent);
    }

    @Override
    public void updateFromItem(final LocalItem localItem, HistoryRecordManager historyRecordManager, final DateFormat dateFormat) {
        if (!(localItem instanceof PlaylistStreamEntry)) return;
        final PlaylistStreamEntry item = (PlaylistStreamEntry) localItem;

        itemVideoTitleView.setText(item.title);
        itemAdditionalDetailsView.setText(Localization.concatenateStrings(item.uploader,
                NewPipe.getNameOfService(item.serviceId)));

        if (item.duration > 0) {
            itemDurationView.setText(Localization.getDurationString(item.duration));
            itemDurationView.setBackgroundColor(ContextCompat.getColor(itemBuilder.getContext(),
                    R.color.duration_background_color));
            itemDurationView.setVisibility(View.VISIBLE);

            StreamStateEntity state = historyRecordManager.loadLocalStreamStateBatch(new ArrayList<LocalItem>() {{ add(localItem); }}).blockingGet().get(0);
            if (state != null) {
                itemProgressView.setVisibility(View.VISIBLE);
                itemProgressView.setMax((int) item.duration);
                itemProgressView.setProgress((int) TimeUnit.MILLISECONDS.toSeconds(state.getProgressTime()));
            } else {
                itemProgressView.setVisibility(View.GONE);
            }
        } else {
            itemDurationView.setVisibility(View.GONE);
        }

        // Default thumbnail is shown on error, while loading and if the url is empty
        itemBuilder.displayImage(item.thumbnailUrl, itemThumbnailView,
                ImageDisplayConstants.DISPLAY_THUMBNAIL_OPTIONS);

        itemView.setOnClickListener(view -> {
            if (itemBuilder.getOnItemSelectedListener() != null) {
                itemBuilder.getOnItemSelectedListener().selected(item);
            }
        });

        itemView.setLongClickable(true);
        itemView.setOnLongClickListener(view -> {
            if (itemBuilder.getOnItemSelectedListener() != null) {
                itemBuilder.getOnItemSelectedListener().held(item);
            }
            return true;
        });

        itemThumbnailView.setOnTouchListener(getOnTouchListener(item));
        itemHandleView.setOnTouchListener(getOnTouchListener(item));
    }

    @Override
    public void updateState(LocalItem localItem, HistoryRecordManager historyRecordManager) {
        if (!(localItem instanceof PlaylistStreamEntry)) return;
        final PlaylistStreamEntry item = (PlaylistStreamEntry) localItem;

        StreamStateEntity state = historyRecordManager.loadLocalStreamStateBatch(new ArrayList<LocalItem>() {{ add(localItem); }}).blockingGet().get(0);
        if (state != null && item.duration > 0) {
            itemProgressView.setMax((int) item.duration);
            if (itemProgressView.getVisibility() == View.VISIBLE) {
                itemProgressView.setProgressAnimated((int) TimeUnit.MILLISECONDS.toSeconds(state.getProgressTime()));
            } else {
                itemProgressView.setProgress((int) TimeUnit.MILLISECONDS.toSeconds(state.getProgressTime()));
                AnimationUtils.animateView(itemProgressView, true, 500);
            }
        } else if (itemProgressView.getVisibility() == View.VISIBLE) {
            AnimationUtils.animateView(itemProgressView, false, 500);
        }
    }

    private View.OnTouchListener getOnTouchListener(final PlaylistStreamEntry item) {
        return (view, motionEvent) -> {
            view.performClick();
            if (itemBuilder != null && itemBuilder.getOnItemSelectedListener() != null &&
                    motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                itemBuilder.getOnItemSelectedListener().drag(item,
                        LocalPlaylistStreamItemHolder.this);
            }
            return false;
        };
    }
}
