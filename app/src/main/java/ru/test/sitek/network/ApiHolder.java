package ru.test.sitek.network;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.test.sitek.model.AuthResponse;
import ru.test.sitek.model.MainResponse;

public class ApiHolder {

    private static final String USERNAME = "http";
    private static final String PASSWORD = "http";
    private final DataSource api;

    public ApiHolder() {
        Gson gson = new GsonBuilder().setLenient().create();
        String base = "https://dev.sitec24.ru/";
        OkHttpClient client = getUnsafeOkHttpClient().newBuilder()
                .addInterceptor(new BasicAuthInterceptor())
                .build();
        api = new Retrofit.Builder()
                .client(client)
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(DataSource.class);
    }

    public void getData(Callback<MainResponse> callback, String uid) {
        api.loadUsers(uid).enqueue(callback);
    }

    public void sendCredentials(Callback<AuthResponse> callback, String pass, String uuid, String uid) {
        api.auth(uuid, uid, pass, false, "").enqueue(callback);
    }

    private static class BasicAuthInterceptor implements Interceptor {

        private final String CREDENTIALS = Credentials.basic(USERNAME, PASSWORD);

        @NonNull
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            request = request
                    .newBuilder()
                    .header("Authorization", CREDENTIALS)
                    .build();
            return chain.proceed(request);
        }
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            X509TrustManager x509Trust;
            @SuppressLint("CustomX509TrustManager, TrustAllX509TrustManager") final TrustManager[] trustAllCerts = new TrustManager[]{
                    x509Trust = new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, x509Trust);
            builder.hostnameVerifier((hostname, session) -> true);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
