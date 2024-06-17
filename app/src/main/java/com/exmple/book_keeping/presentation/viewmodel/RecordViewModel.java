package com.exmple.book_keeping.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.exmple.book_keeping.data.database.model.Category;
import com.exmple.book_keeping.data.database.model.Record;
import com.exmple.book_keeping.data.repository.RecordRepository;

import java.util.List;
import java.util.concurrent.Future;

public class RecordViewModel extends AndroidViewModel {
    private RecordRepository repository;
    private LiveData<List<Record>> allRecords;
    private LiveData<List<Category>> allCategories;

    public RecordViewModel(@NonNull Application application) {
        super(application);
        repository = new RecordRepository(application);
        allRecords = repository.getAllRecords();
        allCategories = repository.getAllCategories();
    }

    // Record operations
    public LiveData<List<Record>> getAllRecords() {
        return allRecords;
    }

    public Future<?> insertRecord(Record record) {
        return repository.insertRecord(record);
    }

    public Future<?> updateRecord(Record record) {
        return repository.updateRecord(record);
    }

    public Future<?> deleteRecord(Record record) {
        return repository.deleteRecord(record);
    }

    // Category operations
    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public Future<?> insertCategory(Category category) {
        return repository.insertCategory(category);
    }

    public Future<?> updateCategory(Category category) {
        return repository.updateCategory(category);
    }

    public Future<?> deleteCategory(Category category) {
        return repository.deleteCategory(category);
    }

    public LiveData<List<Category>> getCategoriesByType(String type) {
        return repository.getCategoriesByType(type);
    }
}

