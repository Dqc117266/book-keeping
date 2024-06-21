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

    @Query("SELECT * FROM records ORDER BY id DESC")
    LiveData<List<Record>> getAllRecords();

    @Query("SELECT * FROM records")
    LiveData<List<Record>> getAllRecordsPs();

    @Query("SELECT * FROM records WHERE type = :type")
    LiveData<List<Record>> getRecordsByType(String type);

    @Query("SELECT SUM(amount) FROM records WHERE type = '收入'")
    LiveData<Double> getTotalIncome();

    @Query("SELECT SUM(amount) FROM records WHERE type = '支出'")
    LiveData<Double> getTotalExpense();

    @Query("SELECT (SELECT SUM(amount) FROM records WHERE type = 'income') - (SELECT SUM(amount) FROM records WHERE type = 'expense')")
    LiveData<Double> getBalance();
}

// CategoryDao.java

