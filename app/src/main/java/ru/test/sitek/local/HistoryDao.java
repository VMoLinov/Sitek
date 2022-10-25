package ru.test.sitek.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    List<HistoryEntity> getAll();

    @Insert
    void insert(HistoryEntity history);
}
