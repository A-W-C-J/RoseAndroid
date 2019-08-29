package com.rose.android.network;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiaohuabu on 17/8/29.
 */
@SuppressLint("TrustAllX509TrustManager")
@SuppressFBWarnings({"DC_DOUBLECHECK", "WEAK_TRUST_MANAGER"})
public class HttpUtils {
    private Gson gson;
    private static volatile HttpUtils instance;
    private Object userHttps;
    private Object hostHttps;
    private static Context mContext;
    private HttpLoggingInterceptor logging;

    public static HttpUtils getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    @SuppressFBWarnings("DC_DOUBLECHECK")
    @SuppressWarnings("unchecked")
    public <T> T getUserServer(Class<T> a, String host) {
        if (userHttps == null) {
            synchronized (HttpUtils.class) {
                if (userHttps == null) {
                    userHttps = getBuilder(host).build().create(a);
                }
            }
        }
        return (T) userHttps;
    }

    @SuppressFBWarnings("DC_DOUBLECHECK")
    @SuppressWarnings("unchecked")
    public <T> T getHostServer(Class<T> a, String host) {
        if (hostHttps == null) {
            synchronized (HttpUtils.class) {
                if (hostHttps == null) {
                    hostHttps = getBuilder(host).build().create(a);
                }
            }
        }
        return (T) hostHttps;
    }

    private Retrofit.Builder getBuilder(String apiUrl) {
        logging = new HttpLoggingInterceptor(mContext);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkClient());
        builder.baseUrl(apiUrl);
        builder.addConverterFactory(GsonConverterFactory.create(getGson()));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder;
    }

    private Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setLenient();
            builder.serializeNulls();
            gson = builder.create();
        }
        return gson;
    }

    public OkHttpClient getOkClient() {
        OkHttpClient client1;
        client1 = getUnSafeOkHttpClient();
        return client1;
    }

    private OkHttpClient getUnSafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            okBuilder.addInterceptor(logging);
            okBuilder.sslSocketFactory(sslSocketFactory, new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            });
            okBuilder.hostnameVerifier((hostname, session) -> true);

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
