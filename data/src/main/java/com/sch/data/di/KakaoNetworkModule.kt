package com.sch.data.di

import com.sch.data.api.ApiClient.KAKAO_BASE_URL
import com.sch.data.api.KakaoAPIService
import com.sch.data.interceptor.KakaoLocalHeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoNetworkModule {

    @Singleton
    @Provides
    @Named("Kakao")
    fun provideKakaoOkHttpClient(): OkHttpClient  {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
//            .addNetworkInterceptor(KakaoLocalHeaderInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("Kakao")
    fun provideKakaoRetrofit(@Named("Kakao") okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(KAKAO_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("Kakao")
    fun provideKakaoAPIService(@Named("Kakao") retrofit: Retrofit) : KakaoAPIService =
        retrofit.create(KakaoAPIService::class.java)
}