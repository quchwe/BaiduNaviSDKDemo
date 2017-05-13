package com.quchwe.cms.util.net;


import android.text.TextUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quchwe.cms.util.base.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by quchwe on 2016/11/11 0011.
 * Retrofit
 */

public class RetrofitUtil {
    //public static final String BASE_URL = "http://192.168.0.104:8080/";
    private static final String BASE_URL= "http://192.168.43.8:8443/app/v1/";
    private static final  File CACHE_FILE = new File(BaseActivity.CASH_FILE,"cache");
    private static final int CACHE_SIZE = 10*1024*1024;
    private static final Cache CACHE = new Cache(CACHE_FILE,CACHE_SIZE);
    private static final int DEFAULT_TIME=8;
//    WebView
    private static Retrofit retrofit;





    public static Retrofit retrofit(){

        if (retrofit==null){
        retrofit = getBuilder().baseUrl(BASE_URL)
                .build();
        }

        return retrofit;
    }
    public static Retrofit.Builder getBuilder(){
        return new Retrofit.Builder()
                .client(getClient())
                .addConverterFactory(getGsonConvertFactory())
                .addCallAdapterFactory(getCallAdapterFactory());
    }
    static Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }




    protected static RxJavaCallAdapterFactory getCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }


    private static GsonConverterFactory getGsonConvertFactory() {
        return GsonConverterFactory.create();
    }

    private static OkHttpClient getClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        BasicParamsInterceptor interceptor = new BasicParamsInterceptor.Builder()
                .build();

        return  new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
//                .addNetworkInterceptor(new CacheInterceptor())
                .cache(CACHE)
                .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .build();
    }



    static class CacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder();
//            request = requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"), bodyToString(request.body())))
//                    .build();


            Response orignResponse = chain.proceed(request);
            String cacheControl = orignResponse.cacheControl().toString();
            return orignResponse.newBuilder()
                    .removeHeader("pragma")
                    .header("Cache-Control", cacheControl)
                    .build();
        }
// process queryParams inject whatever it's GET or POST

        private  String bodyToString(final RequestBody request) {
            try {
                final RequestBody copy = request;
                final Buffer buffer = new Buffer();
                if (copy != null)
                    copy.writeTo(buffer);
                else
                    return "";
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }
    }

    public static class BasicParamsInterceptor implements Interceptor {
        Map<String, String> queryParamsMap = new HashMap<>();
        Map<String, String> paramsMap = new HashMap<>();
        Map<String, String> headerParamsMap = new HashMap<>();
        List<String> headerLinesList = new ArrayList<>();

        private BasicParamsInterceptor() {

        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder();
            request = requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"), bodyToString(request.body())))
                    .build();

            return chain.proceed(request);
        }

        private boolean canInjectIntoBody(Request request) {
            if (request == null) {
                return false;
            }
            if (!TextUtils.equals(request.method(), "POST")) {
                return false;
            }
            RequestBody body = request.body();
            if (body == null) {
                return false;
            }
            MediaType mediaType = body.contentType();
            if (mediaType == null) {
                return false;
            }
            if (!TextUtils.equals(mediaType.subtype(), "x-www-form-urlencoded")) {
                return false;
            }
            return true;
        }

        // func to inject params into url
        private Request injectParamsIntoUrl(HttpUrl.Builder httpUrlBuilder, Request.Builder requestBuilder, Map<String, String> paramsMap) {
            if (paramsMap.size() > 0) {
                Iterator iterator = paramsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
                }
                requestBuilder.url(httpUrlBuilder.build());
                return requestBuilder.build();
            }

            return null;
        }

        private  String bodyToString(final RequestBody request) {
            try {
                final RequestBody copy = request;
                final Buffer buffer = new Buffer();
                if (copy != null)
                    copy.writeTo(buffer);
                else
                    return "";
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }

        public  static class Builder {

            BasicParamsInterceptor interceptor;

            public Builder() {
                interceptor = new BasicParamsInterceptor();
            }

            public Builder addParam(String key, String value) {
                interceptor.paramsMap.put(key, value);
                return this;
            }

            public Builder addParamsMap(Map<String, String> paramsMap) {
                interceptor.paramsMap.putAll(paramsMap);
                return this;
            }

            public Builder addHeaderParam(String key, String value) {
                interceptor.headerParamsMap.put(key, value);
                return this;
            }

            public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
                interceptor.headerParamsMap.putAll(headerParamsMap);
                return this;
            }

            public Builder addHeaderLine(String headerLine) {
                int index = headerLine.indexOf(":");
                if (index == -1) {
                    throw new IllegalArgumentException("Unexpected header: " + headerLine);
                }
                interceptor.headerLinesList.add(headerLine);
                return this;
            }

            public Builder addHeaderLinesList(List<String> headerLinesList) {
                for (String headerLine : headerLinesList) {
                    int index = headerLine.indexOf(":");
                    if (index == -1) {
                        throw new IllegalArgumentException("Unexpected header: " + headerLine);
                    }
                    interceptor.headerLinesList.add(headerLine);
                }
                return this;
            }

            public Builder addQueryParam(String key, String value) {
                interceptor.queryParamsMap.put(key, value);
                return this;
            }

            public Builder addQueryParamsMap(Map<String, String> queryParamsMap) {
                interceptor.queryParamsMap.putAll(queryParamsMap);
                return this;
            }

            public BasicParamsInterceptor build() {
                return interceptor;
            }

        }
    }



}
