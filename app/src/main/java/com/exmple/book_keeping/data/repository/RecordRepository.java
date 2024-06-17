package com.exmple.book_keeping.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.exmple.book_keeping.data.database.AppDatabase;
import com.exmple.book_keeping.data.database.CategoryDao;
import com.exmple.book_keeping.data.database.RecordDao;
import com.exmple.book_keeping.data.database.model.Category;
import com.exmple.book_keeping.data.database.model.Record;

import java.util.List;
import java.util.concurrent.Future;

public class RecordRepository {
    private RecordDao recordDao;
    private CategoryDao categoryDao;
    private LiveData<List<Record>> allRecords;
    private LiveData<List<Category>> allCategories;

    public RecordRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        recordDao = db.recordDao();
        allRecords = recordDao.getAllRecords();
        allCategories = categoryDao.getAllCategories();
    }

    // Record operations
    public LiveData<List<Record>> getAllRecords() {
        return allRecords;
    }

    public Future<?> insertRecord(Record record) {
        return AppDatabase.databaseWriteExecutor.submit(() -> {
            recordDao.insert(record);
        });
    }

    public Future<?> updateRecord(Record record) {
        return AppDatabase.databaseWriteExecutor.submit(() -> {
            recordDao.update(record);
        });
    }

    public Future<?> deleteRecord(Record record) {
        return AppDatabase.databaseWriteExecutor.submit(() -> {
            recordDao.delete(record);
        });
    }

    // Category operations
    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public Future<?> insertCategory(Category category) {
        return AppDatabase.databaseWriteExecutor.submit(() -> {
            categoryDao.insert(category);
        });
    }

    public Future<?> updateCategory(Category category) {
        return AppDatabase.databaseWriteExecutor.submit(() -> {
            categoryDao.update(category);
        });
    }

    public Future<?> deleteCategory(Category category) {
        return AppDatabase.databaseWriteExecutor.submit(() -> {
            categoryDao.delete(category);
        });
    }

    public LiveData<List<Category>> getCategoriesByType(String type) {
        return categoryDao.getCategoriesByType(type);
    }
}

