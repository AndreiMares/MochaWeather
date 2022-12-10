package com.example.mochatest.api

import com.example.mochatest.BuildConfig
import net.mready.apiclient.ApiClient
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherApiClient @Inject constructor(httpClient: OkHttpClient) :
    ApiClient(
        baseUrl = BuildConfig.BASE_URL,
        httpClient = httpClient
    )