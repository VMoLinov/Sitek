package ru.test.sitek.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import ru.test.sitek.AppViewModelFactory;
import ru.test.sitek.R;
import ru.test.sitek.databinding.FragmentMainBinding;
import ru.test.sitek.model.Account;
import ru.test.sitek.model.AuthResponse;
import ru.test.sitek.ui.auth.AuthFragment;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private MainViewModel mViewModel;
    private final String uuid = UUID.randomUUID().toString();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this, new AppViewModelFactory(uuid))
                .get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.init();
        mViewModel.usersLiveData.observe(getViewLifecycleOwner(), accounts -> {
            List<String> list = new ArrayList<>();
            for (Account item : accounts) {
                list.add(item.User);
            }
            ArrayAdapter<String> aa = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, list);
            binding.namesSpinner.setAdapter(aa);
            loadingVisibility(false);
            binding.main.setVisibility(View.VISIBLE);
        });
        btnClickListener();
    }

    private void loadingVisibility(boolean visible) {
        if (visible) binding.loading.getRoot().setVisibility(View.VISIBLE);
        else binding.loading.getRoot().setVisibility(View.GONE);
    }

    private void btnClickListener() {
        binding.enter.setOnClickListener(btn -> {
            loadingVisibility(true);
            mViewModel.sendCredentials(
                    binding.namesSpinner.getSelectedItem().toString(),
                    Objects.requireNonNull(binding.passwordField.getText()).toString());
            mViewModel.authLiveData.observe(this.getViewLifecycleOwner(), this::handleResponse);
        });
    }

    private void handleResponse(AuthResponse authResponse) {
        mViewModel.authLiveData.removeObservers(this.getViewLifecycleOwner());
        mViewModel.resetAuth();
        if (authResponse.Authentication != null) {
            getParentFragmentManager().beginTransaction()
                    .replace(
                            R.id.container,
                            AuthFragment.newInstance(
                                    authResponse.Authentication,
                                    mViewModel.getCurrentAccount()))
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        } else if (authResponse.Code != 0) {
            Toast.makeText(this.getContext(), "Code: " + authResponse.Code, Toast.LENGTH_SHORT).show();
            loadingVisibility(false);
        }
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
