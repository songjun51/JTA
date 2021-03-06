//package com.songjun51.jta;
//
//
//import android.app.DownloadManager;
//
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
//import okhttp3.Interceptor;
//import okhttp3.Response;
//
///**
// * Created by swj85 on 2016-10-31.
// */
//
//public class AddCookiesInterceptor implements Interceptor {
//
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        DownloadManager.Request.Builder builder = chain.request().newBuilder();
//
//        // Preference에서 cookies를 가져오는 작업을 수행
//        Set<String> preferences =  SharedPreferenceBase.getSharedPreference(APIPreferences.SHARED_PREFERENCE_NAME_COOKIE, new HashSet<String>());
//
//        for (String cookie : preferences) {
//            builder.addHeader("Cookie", cookie);
//        }
//
//        // Web,Android,iOS 구분을 위해 User-Agent세팅
//        builder.removeHeader("User-Agent").addHeader("User-Agent", "Android");
//
//
//        return chain.proceed(builder.build());
//    }
//}