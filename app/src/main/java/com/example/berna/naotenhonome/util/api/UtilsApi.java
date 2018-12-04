package com.example.berna.naotenhonome.util.api;

public class UtilsApi {

    // 192.168.15.7 localhost.
    public static final String BASE_URL_API = "http://192.168.15.7/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
