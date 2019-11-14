package com.example.discoverfreedom.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.discoverfreedom.database.history.dao.SearchHistoryDAO;
import com.example.discoverfreedom.database.history.dao.StreamHistoryDAO;
import com.example.discoverfreedom.database.history.model.SearchHistoryEntry;
import com.example.discoverfreedom.database.history.model.StreamHistoryEntity;
import com.example.discoverfreedom.database.playlist.dao.PlaylistDAO;
import com.example.discoverfreedom.database.playlist.dao.PlaylistRemoteDAO;
import com.example.discoverfreedom.database.playlist.dao.PlaylistStreamDAO;
import com.example.discoverfreedom.database.playlist.model.PlaylistEntity;
import com.example.discoverfreedom.database.playlist.model.PlaylistRemoteEntity;
import com.example.discoverfreedom.database.playlist.model.PlaylistStreamEntity;
import com.example.discoverfreedom.database.stream.dao.StreamDAO;
import com.example.discoverfreedom.database.stream.dao.StreamStateDAO;
import com.example.discoverfreedom.database.stream.model.StreamEntity;
import com.example.discoverfreedom.database.stream.model.StreamStateEntity;
import com.example.discoverfreedom.database.subscription.SubscriptionDAO;
import com.example.discoverfreedom.database.subscription.SubscriptionEntity;

import static com.example.discoverfreedom.database.Migrations.DB_VER_12_0;

@TypeConverters({Converters.class})
@Database(
        entities = {
                SubscriptionEntity.class, SearchHistoryEntry.class,
                StreamEntity.class, StreamHistoryEntity.class, StreamStateEntity.class,
                PlaylistEntity.class, PlaylistStreamEntity.class, PlaylistRemoteEntity.class
        },
        version = DB_VER_12_0,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "newpipe.db";

    public abstract SubscriptionDAO subscriptionDAO();

    public abstract SearchHistoryDAO searchHistoryDAO();

    public abstract StreamDAO streamDAO();

    public abstract StreamHistoryDAO streamHistoryDAO();

    public abstract StreamStateDAO streamStateDAO();

    public abstract PlaylistDAO playlistDAO();

    public abstract PlaylistStreamDAO playlistStreamDAO();

    public abstract PlaylistRemoteDAO playlistRemoteDAO();
}
