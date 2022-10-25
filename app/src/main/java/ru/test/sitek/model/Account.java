package ru.test.sitek.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {

    public String User;
    public String Uid;
    public String Language;

    public Account(String User, String Uid, String Language) {
        this.User = User;
        this.Uid = Uid;
        this.Language = Language;
    }

    protected Account(Parcel in) {
        User = in.readString();
        Uid = in.readString();
        Language = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(User);
        parcel.writeString(Uid);
        parcel.writeString(Language);
    }
}
