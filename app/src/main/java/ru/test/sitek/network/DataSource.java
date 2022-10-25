package ru.test.sitek.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.test.sitek.model.AuthResponse;
import ru.test.sitek.model.MainResponse;

public interface DataSource {

    @GET("UKA_TRADE/hs/MobileClient/{uid}/form/users")
    Call<MainResponse> loadUsers(@Path("uid") String uid);

    @GET("UKA_TRADE/hs/MobileClient/{uid}/authentication/")
    Call<AuthResponse> auth(@Path("uid") String uuid,
                            @Query("uid") String uid,
                            @Query("pass") String password,
                            @Query("copyFromDevice") Boolean isCopy,
                            @Query("nfc") String nfc);
}
