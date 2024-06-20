package com.exmple.book_keeping.presentation.screen.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.exmple.book_keeping.data.database.model.Record;
import com.exmple.book_keeping.databinding.ActivityBillDetailBinding;
import com.exmple.book_keeping.presentation.util.StatusBarUtil;

public class BillDetailActivity extends AppCompatActivity {

    private ActivityBillDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityBillDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("record")) {
            Record record = (Record) intent.getSerializableExtra("record");
            // 使用传递的数据进行操作
            if (record != null) {
                initViews(record);
            }
        }
    }

    private void initViews(Record record) {
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
        ConstraintLayout.MarginLayoutParams params = (ConstraintLayout.MarginLayoutParams) binding.toolbar.getLayoutParams();
        params.setMargins(0, statusBarHeight, 0, 0);
        binding.toolbar.setLayoutParams(params);

        binding.toolbar.setTitle(record.category);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        binding.amountTypeText.setText(record.type);
        binding.amountText.setText(String.valueOf(record.amount));
        binding.remarkText.setText(record.note);
        binding.dateText.setText(record.date);
    }
}