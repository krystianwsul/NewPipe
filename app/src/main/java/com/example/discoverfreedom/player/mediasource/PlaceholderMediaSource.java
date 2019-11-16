package com.example.discoverfreedom.player.mediasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;

import com.example.discoverfreedom.player.playqueue.PlayQueueItem;

public class PlaceholderMediaSource extends BaseMediaSource implements ManagedMediaSource {
    // Do nothing, so this will stall the playback
    @Override public void maybeThrowSourceInfoRefreshError() {}
    @Override public MediaPeriod createPeriod(MediaPeriodId id, Allocator allocator, long startPositionUs) { return null; }
    @Override public void releasePeriod(MediaPeriod mediaPeriod) {}
    @Override protected void prepareSourceInternal(@Nullable TransferListener mediaTransferListener) {}
    @Override protected void releaseSourceInternal() {}

    @Override
    public boolean shouldBeReplacedWith(@NonNull PlayQueueItem newIdentity,
                                        final boolean isInterruptable) {
        return true;
    }

    @Override
    public boolean isStreamEqual(@NonNull PlayQueueItem stream) {
        return false;
    }
}