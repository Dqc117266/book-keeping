package com.exmple.book_keeping.presentation.screen.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.exmple.book_keeping.databinding.ItemRecordBinding;
import com.exmple.book_keeping.data.database.model.Record;

public class RecordAdapter extends ListAdapter<Record, RecordAdapter.RecordViewHolder> {

    public interface OnItemDeleteListener {
        void onItemDelete(Record record);
    }

    public interface OnItemClickListener {
        void onItemClick(Record record);
    }

    private static final int VIEW_TYPE_YEAR = 1;
    private static final int VIEW_TYPE_MONTH = 2;
    private static final int VIEW_TYPE_RECORD = 3;

    private OnItemDeleteListener onItemDeleteListener;
    private OnItemClickListener onItemClickListener;

    public RecordAdapter(OnItemDeleteListener onItemDeleteListener, OnItemClickListener onItemClickListener) {
        super(DIFF_CALLBACK);

        this.onItemDeleteListener = onItemDeleteListener;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRecordBinding binding = ItemRecordBinding.inflate(layoutInflater, parent, false);
        return new RecordViewHolder(binding, onItemDeleteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = getItem(position);
        holder.bind(record);
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(record);
            }
        });
    }

    class RecordViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecordBinding binding;
        private final OnItemDeleteListener onItemDeleteListener;

        public RecordViewHolder(ItemRecordBinding binding, OnItemDeleteListener onItemDeleteListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemDeleteListener = onItemDeleteListener;

            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Record record = getItem(position);
                        new AlertDialog.Builder(binding.getRoot().getContext())
                                .setTitle("删除记录")
                                .setMessage("确定要删除这条记录吗？")
                                .setPositiveButton("删除", (dialog, which) -> {
                                    if (onItemDeleteListener != null) {
                                        onItemDeleteListener.onItemDelete(record);
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                        return true;
                    }
                    return false;
                }
            });
        }

        public void bind(Record record) {
            binding.setRecord(record);
            binding.executePendingBindings();
        }
    }

    private static final DiffUtil.ItemCallback<Record> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Record>() {
                @Override
                public boolean areItemsTheSame(@NonNull Record oldItem, @NonNull Record newItem) {
                    return oldItem.id == newItem.id;
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Record oldItem, @NonNull Record newItem) {
                    return oldItem.equals(newItem);
                }
            };
}

