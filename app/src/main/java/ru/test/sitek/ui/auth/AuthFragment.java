package ru.test.sitek.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import ru.test.sitek.databinding.FragmentAuthBinding;
import ru.test.sitek.model.Account;
import ru.test.sitek.model.Authentication;

public class AuthFragment extends Fragment {

    private FragmentAuthBinding binding = null;
    private Account currentAccount;
    private Authentication currentAuthentication;
    private AuthViewModel viewModel;
    private static final String KEY_AUTH = "AUTH";
    private static final String KEY_ACC = "ACC";

    public static AuthFragment newInstance(Authentication authentication, Account account) {
        AuthFragment f = new AuthFragment();
        Bundle b = new Bundle();
        b.putSerializable(KEY_AUTH, authentication);
        b.putParcelable(KEY_ACC, account);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentAuthentication = (Authentication) getArguments().getSerializable(KEY_AUTH);
            currentAccount = getArguments().getParcelable(KEY_ACC);
        }
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        viewModel.setDb(this.requireActivity().getApplication());
        viewModel.insert(currentAuthentication);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AuthAdapter adapter = new AuthAdapter();
        binding.authRecycler.setAdapter(adapter);
        DividerItemDecoration divider = new DividerItemDecoration(this.requireContext(), LinearLayoutManager.VERTICAL);
        binding.authRecycler.addItemDecoration(divider);
        viewModel.history.observe(this.getViewLifecycleOwner(), data -> {
            adapter.setData(data);
            changeVisibility();
        });
        binding.user.setText(currentAccount.User);
    }

    void changeVisibility() {
        binding.loading.getRoot().setVisibility(View.GONE);
        binding.main.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
