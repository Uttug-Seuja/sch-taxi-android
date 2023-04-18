package com.sch.data.interceptor

import com.sch.data.BuildConfig
import com.sch.data.DataApplication
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class KakaoLocalHeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        return chain.proceed(
            builder.addHeader(
                "Authorization",
                BuildConfig.KAKAO_LOCAL_REST_APP_KEY
            ).build()
        )


    }
}