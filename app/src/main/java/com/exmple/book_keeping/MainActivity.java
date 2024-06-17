package com.exmple.book_keeping;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.exmple.book_keeping.databinding.ActivityMainBinding;
import com.exmple.book_keeping.presentation.screen.fragment.nav.NavManager;
import com.exmple.book_keeping.presentation.util.NavigationUtil;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {
    private ActivityMainBinding binding;
    private NavManager navManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initNavManager();
        initBottomNavigation();
    }

    private void initNavManager() {
        navManager = new NavManager();

        navManager.setOnNavEvent(it -> {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
            assert navHostFragment != null;
            assert navHostFragment.getChildFragmentManager().getFragments().size() > 0;

            NavController navController = navHostFragment.getNavController();
            NavigationUtil.navigateSafely(navController, it);
        });
    }

    private void initBottomNavigation() {
        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment)).getNavController();
        navController.addOnDestinationChangedListener(this);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);
    }

    @Override
    public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {

    }
}