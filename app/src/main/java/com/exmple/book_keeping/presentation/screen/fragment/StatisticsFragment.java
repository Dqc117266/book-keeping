package com.exmple.book_keeping.presentation.screen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.exmple.book_keeping.databinding.FragmentStatisticsBinding;
import com.exmple.book_keeping.presentation.util.StatusBarUtil;

public class StatisticsFragment extends Fragment {
    private FragmentStatisticsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int statusBarHeight = StatusBarUtil.getStatusBarHeight(getContext());
        ConstraintLayout.MarginLayoutParams params = (ConstraintLayout.MarginLayoutParams) binding.toolbar.getLayoutParams();
        params.setMargins(0, statusBarHeight, 0, 0);
        binding.toolbar.setLayoutParams(params);
    }
}
