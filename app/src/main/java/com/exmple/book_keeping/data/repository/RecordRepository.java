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
    private LiveData<List<Record>> allRecordsPs;
    private LiveData<List<Category>> allCategories;
    private LiveData<Double> totalIncome;
    private LiveData<Double> totalExpense;
    private LiveData<Double> balance;


    public RecordRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        recordDao = db.recordDao();
        categoryDao = db.categoryDao();
        allRecords = recordDao.getAllRecords();
        allRecordsPs = recordDao.getAllRecordsPs();
        allCategories = categoryDao.getAllCategories();
        totalIncome = recordDao.getTotalIncome();
        totalExpense = recordDao.getTotalExpense();
        balance = recordDao.getBalance();
    }

    // Record operations
    public LiveData<List<Record>> getAllRecords() {
        return allRecords;
    }

    public LiveData<Double> getTotalIncome() {
        return totalIncome;
    }

    public LiveData<Double> getTotalExpense() {
        return totalExpense;
    }

    public LiveData<List<Record>> getAllRecordsPs() {
        return allRecordsPs;
    }

    public LiveData<Double> getBalance() {
        return balance;
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

