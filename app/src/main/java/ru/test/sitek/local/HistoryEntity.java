package ru.test.sitek.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.test.sitek.model.Authentication;

@Entity
public class HistoryEntity {

    @PrimaryKey(autoGenerate = true)
    public int id = 0;
    public boolean response;
    public boolean continueWork;
    public String photoHash;
    public String currentDate;
    public String currentTime;

    public HistoryEntity(int id, boolean response, boolean continueWork, String photoHash, String currentDate) {
        this.id = id;
        this.response = response;
        this.continueWork = continueWork;
        this.photoHash = photoHash;
        this.currentDate = currentDate;
        currentTime = getCurrentTime();
    }

    public HistoryEntity(Authentication authentication) {
        response = authentication.Response;
        continueWork = authentication.ContinueWork;
        photoHash = authentication.PhotoHash;
        currentDate = authentication.CurrentDate;
        currentTime = getCurrentTime();
    }

    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return format.format(new Date());
    }
}
