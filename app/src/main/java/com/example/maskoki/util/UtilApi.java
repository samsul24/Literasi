package com.example.maskoki.util;

import com.example.maskoki.rest.api.ApiClient;
import com.example.maskoki.rest.services.ApiServices;

public class UtilApi {
    public static final String BASE_URL_API = "https://test.towerbarat.site/RLiterasi/api/";

    public static ApiServices getApiService(){
        return ApiClient.getClient(BASE_URL_API).create(ApiServices.class);
    }
}
