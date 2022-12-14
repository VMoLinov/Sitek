package ru.test.sitek.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {HistoryEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HistoryDao historyDao();
}
