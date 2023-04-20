package com.sch.data.di


import com.sch.data.interceptor.EmptyBodyInterceptor
import com.sch.data.interceptor.ErrorResponseInterceptor
import com.sch.data.interceptor.XAccessTokenInterceptor
import com.sch.data.BuildConfig
import com.sch.data.api.ApiClient.BASE_URL
import com.sch.data.api.MainAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("Main")
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .addInterceptor(EmptyBodyInterceptor())
//            .addInterceptor(BearerInterceptor()) // Refresh Token
            .addInterceptor(ErrorResponseInterceptor()) // Error Response
            .build()
    } else {
        OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .addInterceptor(EmptyBodyInterceptor())
//            .addInterceptor(BearerInterceptor()) // Refresh Token
            .addInterceptor(ErrorResponseInterceptor()) // Error Response
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Singleton
    @Provides
    @Named("Main")
    fun provideRetrofit(@Named("Main") okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
//        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("Main")
    fun provideMainAPIService(@Named("Main") retrofit: Retrofit): MainAPIService =
        retrofit.create(MainAPIService::class.java)
}