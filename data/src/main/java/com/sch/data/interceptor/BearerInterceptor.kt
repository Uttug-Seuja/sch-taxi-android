package com.sch.data.interceptor



import android.util.Log
import com.sch.data.model.remote.request.PostRefreshTokenRequest
import com.sch.data.model.remote.error.ErrorResponseImpl
import com.sch.data.DataApplication.Companion.editor
import com.sch.data.DataApplication.Companion.sSharedPreferences
import com.sch.data.api.ApiClient.BASE_URL
import com.sch.data.api.MainAPIService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/*
   * bearer 토큰 필요한 api 사용시 accessToken유효한지 검사
   * 유효하지 않다면 재발급 api 호출
   * refreshToken이 유효하다면 정상적으로 accessToken재발급 후 기존 api 동작 완료
   * 사용시 주석 풀고 사용하기
*/

class BearerInterceptor : Interceptor {
    //todo 조건 분기로 인터셉터 구조 변경
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("ttt BearerInterceptor", "BearerInterceptor 들어옴" )
        val request = chain.request()
        Log.d("ttt request", request.toString() )

        val response = chain.proceed(request)
        Log.d("ttt response", response.toString() )

        val baseResponse = response.toBaseResponse()

        var accessToken = ""
        var isRefreshable = false

        Log.d("ttt baseResponse?.status  ", baseResponse?.status.toString() )
        Log.d("ttt isRefreshable ", isRefreshable.toString() )

        if(baseResponse?.status == 401) {
            runBlocking {
                //토큰 갱신 api 호출
                sSharedPreferences.getString("refreshToken", null)?.let {
                    val result = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(MainAPIService::class.java).postRefreshToken(PostRefreshTokenRequest(it))

                    if(result.success) {
                        editor.putString("accessToken", result.data.accessToken)
                        editor.putString("refreshToken", result.data.refreshToken)
                        editor.commit()
                        accessToken = result.data.accessToken
                        isRefreshable = true
                    }
                }
            }
        }

        if(isRefreshable) {
            val newRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer $accessToken").build()
            return chain.proceed(newRequest)
        }

        return response
    }
}


fun Response.toBaseResponse(): ErrorResponseImpl? {
    return this.body?.string()?.let { createErrorResponse(it) }
}