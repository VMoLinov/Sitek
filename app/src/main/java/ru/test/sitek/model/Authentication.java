package ru.test.sitek.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Authentication implements Serializable {

    public boolean Response;
    public boolean ContinueWork;
    public String PhotoHash;
    public String CurrentDate;

    @NonNull
    @Override
    public String toString() {
        return "Response=" + Response +
                ", ContinueWork=" + ContinueWork +
                ", PhotoHash='" + PhotoHash + '\'' +
                ", CurrentDate='" + CurrentDate + '\'' +
                '}';
    }
}
