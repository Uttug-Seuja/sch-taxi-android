package com.sch.data.di


import com.sch.data.api.KakaoAPIService
import com.sch.data.repository.MainRepositoryImpl
import com.sch.data.api.MainAPIService
import com.sch.data.repository.KakaoRepositoryImpl
import com.sch.domain.repository.KakaoRepository
import com.sch.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMainRepository(@Named("Main") mainAPIService: MainAPIService): MainRepository {
        return MainRepositoryImpl(mainAPIService)
    }

    @Provides
    @Singleton
    fun provideKakaoRepository(@Named("Kakao") kakaoAPIService: KakaoAPIService): KakaoRepository {
        return KakaoRepositoryImpl(kakaoAPIService)
    }
}