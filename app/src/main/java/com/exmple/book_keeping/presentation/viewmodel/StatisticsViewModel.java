package com.exmple.book_keeping.presentation.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.exmple.book_keeping.data.database.model.Record;
import com.exmple.book_keeping.data.repository.RecordRepository;
import com.github.mikephil.charting.data.Entry;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StatisticsViewModel extends AndroidViewModel {
    private RecordRepository repository;
    private LiveData<Double> totalIncome;
    private LiveData<Double> totalExpense;
    private LiveData<Double> balance;
    private final LiveData<List<Entry>> entries;


    public StatisticsViewModel(Application application) {
        super(application);
        repository = new RecordRepository(application);
        totalIncome = repository.getTotalIncome();
        totalExpense = repository.getTotalExpense();
        balance = new MutableLiveData<>();

        entries = Transformations.map(repository.getAllRecordsPs(), this::mapRecordsToEntries);

        calculateBalance();
    }

    public List<Entry> mapRecordsToEntries(List<Record> records) {
        return IntStream.range(0, records.size())
                .mapToObj(index -> {
                    Record record = records.get(index);
                    return new Entry(index, (float) record.amount);
                })
                .collect(Collectors.toList());
    }

    public LiveData<List<Entry>> getEntries() {
        return entries;
    }

    public LiveData<Double> getTotalIncome() {
        return totalIncome;
    }

    public LiveData<Double> getTotalExpense() {
        return totalExpense;
    }

    public LiveData<Double> getBalance() {
        return balance;
    }

    private void calculateBalance() {
        totalIncome.observeForever(income -> {
            updateBalance();
        });

        totalExpense.observeForever(expense -> {
            updateBalance();
        });
    }

    private void updateBalance() {
        double income = totalIncome.getValue() != null ? totalIncome.getValue() : 0.0;
        double expense = totalExpense.getValue() != null ? totalExpense.getValue() : 0.0;
        ((MutableLiveData<Double>) balance).setValue(income - expense);
    }
}


