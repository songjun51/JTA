//package com.songjun51.jta;
//
//import java.io.IOException;
//import java.util.HashSet;
//
//import okhttp3.Interceptor;
//import okhttp3.Response;
//
//
///**
// * Created by swj85 on 2016-10-31.
// */
//public class ReceivedCookiesInterceptor implements Interceptor {
//
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Response originalResponse = chain.proceed(chain.request());
//
//        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
//            HashSet<String> cookies = new HashSet<>();
//
//            for (String header : originalResponse.headers("Set-Cookie")) {
//                cookies.add(header);
//            }
//
//            // Preference에 cookies를 넣어주는 작업을 수행
//            SharedPreferenceBase.putSharedPreference(APIPreferences.SHARED_PREFERENCE_NAME_COOKIE, cookies);
//
//        }
//
//        return originalResponse;
//    }
//}