package com.exmple.book_keeping.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.exmple.book_keeping.data.database.model.Record;

import java.util.List;

// RecordDao.java
@Dao
public interface RecordDao {
    @Insert
    void insert(Record record);

    @Update
    void update(Record record);

    @Delete
    void delete(Record record);

    @Query("SELECT * FROM records")
    LiveData<List<Record>> getAllRecords();

    @Query("SELECT * FROM records WHERE type = :type")
    LiveData<List<Record>> getRecordsByType(String type);
}

// CategoryDao.java

