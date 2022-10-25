package ru.test.sitek.ui.main;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.test.sitek.model.Account;
import ru.test.sitek.model.AuthResponse;
import ru.test.sitek.model.MainResponse;
import ru.test.sitek.network.ApiHolder;

public class MainViewModel extends ViewModel {

    private final String uuid;

    public MainViewModel(String uuid) {
        this.uuid = uuid;
    }

    MutableLiveData<List<Account>> usersLiveData = new MutableLiveData<>();
    MutableLiveData<AuthResponse> authLiveData = new MutableLiveData<>();
    private Account currentAccount;
    private final ApiHolder apiHolder = new ApiHolder();
    private static final String TAG = "MainViewModelTAG";

    private final Callback<MainResponse> usersCallback = new Callback<MainResponse>() {
        @Override
        public void onResponse(@NonNull Call<MainResponse> call, Response<MainResponse> response) {
            MainResponse mainResponse = response.body();
            if (response.isSuccessful() && mainResponse != null) {
                usersLiveData.postValue(mainResponse.Users.ListUsers);
            } else {
                Log.d(TAG, "error code: " + response.code());
            }
        }

        @Override
        public void onFailure(@NonNull Call<MainResponse> call, Throwable t) {
            Log.d(TAG, "empty data " + t.getMessage());
        }
    };

    private final Callback<AuthResponse> authCallback = new Callback<AuthResponse>() {
        @Override
        public void onResponse(@NonNull Call<AuthResponse> call, Response<AuthResponse> response) {
            AuthResponse mainResponse = response.body();
            if (response.isSuccessful() && mainResponse != null) {
                authLiveData.postValue(mainResponse);
            } else {
                Log.d(TAG, "error code: " + response.code());
            }
        }

        @Override
        public void onFailure(@NonNull Call<AuthResponse> call, Throwable t) {
            Log.d(TAG, "empty data " + t.getMessage());
        }
    };

    public void init() {
        apiHolder.getData(usersCallback, uuid);
    }

    public void sendCredentials(String userName, String password) {
        for (Account account : Objects.requireNonNull(usersLiveData.getValue())) {
            if (account.User.equals(userName)) {
                currentAccount = usersLiveData.getValue().get(usersLiveData.getValue().indexOf(account));
                apiHolder.sendCredentials(authCallback, password, uuid, account.Uid);
            }
        }
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void resetAuth() {
        authLiveData = new MutableLiveData<>();
    }
}
