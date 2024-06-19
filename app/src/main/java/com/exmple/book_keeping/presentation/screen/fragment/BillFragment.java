package com.exmple.book_keeping.presentation.screen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exmple.book_keeping.data.database.model.Record;
import com.exmple.book_keeping.databinding.FragmentBillBinding;
import com.exmple.book_keeping.presentation.screen.adapter.RecordAdapter;
import com.exmple.book_keeping.presentation.screen.dialog.AddRecordDialog;
import com.exmple.book_keeping.presentation.viewmodel.RecordViewModel;

public class BillFragment extends Fragment {
    private FragmentBillBinding binding;
    private RecordViewModel recordViewModel;
    private RecordAdapter recordAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recordViewModel = new ViewModelProvider(this).get(RecordViewModel.class);

        binding.fabAdd.setOnClickListener(v -> {
            AddRecordDialog.newInstance().show(getParentFragmentManager(), "AddRecordDialog");
        });

        recordAdapter = new RecordAdapter(new RecordAdapter.OnItemDeleteListener() {
            @Override
            public void onItemDelete(Record record) {
                recordViewModel.deleteRecord(record);
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(recordAdapter);

        recordViewModel.getAllRecords().observe(getViewLifecycleOwner(), records -> {
            recordAdapter.submitList(records);
        });
    }
}
