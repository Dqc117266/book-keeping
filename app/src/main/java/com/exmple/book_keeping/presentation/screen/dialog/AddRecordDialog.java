package com.exmple.book_keeping.presentation.screen.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.exmple.book_keeping.R;
import com.exmple.book_keeping.data.database.model.Record;
import com.exmple.book_keeping.presentation.util.DateTimeUtil;
import com.exmple.book_keeping.presentation.viewmodel.RecordViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddRecordDialog extends DialogFragment {

    private RecordViewModel recordViewModel;
    private List<String> categoryList;
    private List<String> typeList;

    private Spinner spinnerCategory;
    private Spinner spinnerType;
    private EditText editTextAmount;
    private TextView textViewDate;
    private EditText editTextNote;

    public static AddRecordDialog newInstance() {
        return new AddRecordDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordViewModel = new ViewModelProvider(requireActivity()).get(RecordViewModel.class);
        recordViewModel.getAllCategories().observe(this, categories -> {
            categoryList = new ArrayList<>();
            typeList = new ArrayList<>();
            String[] categorieArray = getResources().getStringArray(R.array.category_array);
            String[] typeArray = getResources().getStringArray(R.array.category_type_array);

            for (String category : categorieArray) {
                categoryList.add(category);
            }

            for (String type : typeArray) {
                typeList.add(type);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categoryList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter);

            ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, typeList);
            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerType.setAdapter(typeAdapter);
        });
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_record, container, false);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerType = view.findViewById(R.id.spinnerType);
        editTextAmount = view.findViewById(R.id.editTextAmount);
        textViewDate = view.findViewById(R.id.textViewDate);
        editTextNote = view.findViewById(R.id.editTextNote);

        textViewDate.setText("");

        view.findViewById(R.id.buttonSave).setOnClickListener(v -> saveRecord());
        view.findViewById(R.id.buttonCancel).setOnClickListener(v -> dismiss());

        return view;
    }

    private void saveRecord() {
        String category = spinnerCategory.getSelectedItem().toString();
        String type = spinnerType.getSelectedItem().toString();
        String amountStr = editTextAmount.getText().toString();
        String note = editTextNote.getText().toString();

        if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(amountStr)) {
            double amount = Double.parseDouble(amountStr);

            Record record = new Record();
            record.type = type;
            record.category = category;
            record.amount = amount;
            record.date = DateTimeUtil.getCurrentDateTime();
            record.note = note;

            recordViewModel.insertRecord(record);
            dismiss();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}

