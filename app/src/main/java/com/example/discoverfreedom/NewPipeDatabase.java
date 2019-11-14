package com.example.discoverfreedom;

import androidx.room.Room;
import android.content.Context;
import androidx.annotation.NonNull;

import com.example.discoverfreedom.database.AppDatabase;

import static com.example.discoverfreedom.database.AppDatabase.DATABASE_NAME;
import static com.example.discoverfreedom.database.Migrations.MIGRATION_11_12;

public final class NewPipeDatabase {

    private static volatile AppDatabase databaseInstance;

    private NewPipeDatabase() {
        //no instance
    }

    private static AppDatabase getDatabase(Context context) {
        return Room
                .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .addMigrations(MIGRATION_11_12)
                .fallbackToDestructiveMigration()
                .build();
    }

    @NonNull
    public static AppDatabase getInstance(@NonNull Context context) {
        AppDatabase result = databaseInstance;
        if (result == null) {
            synchronized (NewPipeDatabase.class) {
                result = databaseInstance;
                if (result == null) {
                    databaseInstance = (result = getDatabase(context));
                }
            }
        }

        return result;
    }
}
