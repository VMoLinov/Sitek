package ru.test.sitek.ui.auth;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.util.List;

import ru.test.sitek.local.AppDatabase;
import ru.test.sitek.local.HistoryEntity;
import ru.test.sitek.model.Authentication;

public class AuthViewModel extends ViewModel {

    public MutableLiveData<List<HistoryEntity>> history = new MutableLiveData<>();
    private AppDatabase db;

    public void setDb(Application application) {
        db = Room.databaseBuilder(application, AppDatabase.class, HistoryEntity.class.getSimpleName())
                .allowMainThreadQueries()
                .build();
    }

    public void insert(Authentication authentication) {
        db.historyDao().insert(new HistoryEntity(authentication));
        updateData();
    }

    public void updateData() {
        history.postValue(db.historyDao().getAll());
    }
}
