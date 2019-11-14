package com.example.discoverfreedom.database.stream.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.discoverfreedom.database.BasicDAO;
import com.example.discoverfreedom.database.playlist.model.PlaylistStreamEntity;
import com.example.discoverfreedom.database.stream.model.StreamEntity;
import com.example.discoverfreedom.database.history.model.StreamHistoryEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static com.example.discoverfreedom.database.playlist.model.PlaylistStreamEntity.PLAYLIST_STREAM_JOIN_TABLE;
import static com.example.discoverfreedom.database.stream.model.StreamEntity.STREAM_ID;
import static com.example.discoverfreedom.database.stream.model.StreamEntity.STREAM_SERVICE_ID;
import static com.example.discoverfreedom.database.stream.model.StreamEntity.STREAM_TABLE;
import static com.example.discoverfreedom.database.stream.model.StreamEntity.STREAM_URL;
import static com.example.discoverfreedom.database.history.model.StreamHistoryEntity.STREAM_HISTORY_TABLE;

@Dao
public abstract class StreamDAO implements BasicDAO<StreamEntity> {
    @Override
    @Query("SELECT * FROM " + STREAM_TABLE)
    public abstract Flowable<List<StreamEntity>> getAll();

    @Override
    @Query("DELETE FROM " + STREAM_TABLE)
    public abstract int deleteAll();

    @Override
    @Query("SELECT * FROM " + STREAM_TABLE + " WHERE " + STREAM_SERVICE_ID + " = :serviceId")
    public abstract Flowable<List<StreamEntity>> listByService(int serviceId);

    @Query("SELECT * FROM " + STREAM_TABLE + " WHERE " +
            STREAM_URL + " = :url AND " +
            STREAM_SERVICE_ID + " = :serviceId")
    public abstract Flowable<List<StreamEntity>> getStream(long serviceId, String url);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void silentInsertAllInternal(final List<StreamEntity> streams);

    @Query("SELECT " + STREAM_ID + " FROM " + STREAM_TABLE + " WHERE " +
            STREAM_URL + " = :url AND " +
            STREAM_SERVICE_ID + " = :serviceId")
    abstract Long getStreamIdInternal(long serviceId, String url);

    @Transaction
    public long upsert(StreamEntity stream) {
        final Long streamIdCandidate = getStreamIdInternal(stream.getServiceId(), stream.getUrl());

        if (streamIdCandidate == null) {
            return insert(stream);
        } else {
            stream.setUid(streamIdCandidate);
            update(stream);
            return streamIdCandidate;
        }
    }

    @Transaction
    public List<Long> upsertAll(List<StreamEntity> streams) {
        silentInsertAllInternal(streams);

        final List<Long> streamIds = new ArrayList<>(streams.size());
        for (StreamEntity stream : streams) {
            final Long streamId = getStreamIdInternal(stream.getServiceId(), stream.getUrl());
            if (streamId == null) {
                throw new IllegalStateException("StreamID cannot be null just after insertion.");
            }

            streamIds.add(streamId);
            stream.setUid(streamId);
        }

        update(streams);
        return streamIds;
    }

    @Query("DELETE FROM " + STREAM_TABLE + " WHERE " + STREAM_ID +
            " NOT IN " +
            "(SELECT DISTINCT " + STREAM_ID + " FROM " + STREAM_TABLE +

            " LEFT JOIN " + STREAM_HISTORY_TABLE +
            " ON " + STREAM_ID + " = " +
            StreamHistoryEntity.STREAM_HISTORY_TABLE + "." + StreamHistoryEntity.JOIN_STREAM_ID +

            " LEFT JOIN " + PLAYLIST_STREAM_JOIN_TABLE +
            " ON " + STREAM_ID + " = " +
            PlaylistStreamEntity.PLAYLIST_STREAM_JOIN_TABLE + "." + PlaylistStreamEntity.JOIN_STREAM_ID +
            ")")
    public abstract int deleteOrphans();
}
