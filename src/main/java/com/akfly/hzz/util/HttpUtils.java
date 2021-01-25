package com.akfly.hzz.util;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.RequestBody;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class HttpUtils {
    private HttpUtils() {
    }

    public static String get(String url) {
        return delegate.get(url);
    }

    public static String get(String url, Headers headers) {
        return delegate.get(url, headers);
    }

    public static String get(String url, Map<String, String> queryParas) {
        return delegate.get(url, queryParas);
    }

    public static String postForm(String url, Map<String, String> queryParas) {
        return delegate.postForm(url, queryParas);
    };

    public static String post(String url, String data) {
        return delegate.post(url, data);
    }

    public static String post(String url, String params, Headers headers) {
        return delegate.post(url, params, headers);
    }

    public static String postSSL(String url, String data, String certPath, String certPass) {
        return delegate.postSSL(url, data, certPath, certPass);
    }

    private interface HttpDelegate {
        String get(String url);

        String get(String url, Headers headers);

        String get(String url, Map<String, String> queryParas);

        String postForm(String url, Map<String, String> queryParas);

        String post(String url, String data);

        public String post(String url, String params, Headers headers);

        String postSSL(String url, String data, String certPath, String certPass);

    }

    // http请求工具代理对象
    private static HttpDelegate delegate;

    public static void setHttpDelegate(HttpDelegate httpDelegate) {
        delegate = httpDelegate;
    }

    static {
        delegate = new OkHttp3Delegate();
    }

    private static class OkHttp3Delegate implements HttpDelegate {
        private okhttp3.OkHttpClient httpClient;

        public OkHttp3Delegate() {
            httpClient = new okhttp3.OkHttpClient().newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
        }

        private static final okhttp3.MediaType CONTENT_TYPE_FORM =
                okhttp3.MediaType.parse("application/x-www-form-urlencoded");

        private static final okhttp3.MediaType CONTENT_TYPE_JSON =
                okhttp3.MediaType.parse("application/json;charset=utf8");

        private String exec(okhttp3.Request request) {
            try {
                okhttp3.Response response = httpClient.newCall(request).execute();

                if (!response.isSuccessful()) {
                    throw new RuntimeException("Unexpected code " + response);
                }
                return response.body().string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String get(String url) {
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).get().build();
            return exec(request);
        }

        @Override
        public String get(String url, Headers headers) {
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).headers(headers).get().build();
            return exec(request);
        }

        @Override
        public String get(String url, Map<String, String> queryParas) {
            okhttp3.HttpUrl.Builder urlBuilder = okhttp3.HttpUrl.parse(url).newBuilder();
            for (Map.Entry<String, String> entry : queryParas.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            okhttp3.HttpUrl httpUrl = urlBuilder.build();
            okhttp3.Request request = new okhttp3.Request.Builder().url(httpUrl).get().build();
            return exec(request);
        }
        @Override
        public String postForm(String url, Map<String, String> queryParas) {
            FormBody.Builder buider = new FormBody.Builder();
            for (Map.Entry<String, String> entry : queryParas.entrySet()) {
                buider.add(entry.getKey(), entry.getValue());
            }
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(buider.build())
                    .build();
            return exec(request);
        }
        @Override
        public String post(String url, String params) {
            RequestBody body = RequestBody.create(CONTENT_TYPE_JSON, params);
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            return exec(request);
        }

        @Override
        public String post(String url, String params, Headers headers) {
            RequestBody body = RequestBody.create(CONTENT_TYPE_JSON, params);
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .headers(headers)
                    .post(body)
                    .build();
            return exec(request);
        }

        @Override
        public String postSSL(String url, String data, String certPath, String certPass) {
            RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, data);
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            InputStream inputStream = null;
            try {
                KeyStore clientStore = KeyStore.getInstance("PKCS12");
                inputStream = new FileInputStream(certPath);
                char[] passArray = certPass.toCharArray();
                clientStore.load(inputStream, passArray);

                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmf.init(clientStore, passArray);
                KeyManager[] kms = kmf.getKeyManagers();
                SSLContext sslContext = SSLContext.getInstance("TLSv1");

                sslContext.init(kms, null, new SecureRandom());

                okhttp3.OkHttpClient httpsClient = new okhttp3.OkHttpClient()
                        .newBuilder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .sslSocketFactory(sslContext.getSocketFactory())
                        .build();

                okhttp3.Response response = httpsClient.newCall(request).execute();

                if (!response.isSuccessful()) {
                    throw new RuntimeException("Unexpected code " + response);
                }

                return response.body().string();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }



    }
}
