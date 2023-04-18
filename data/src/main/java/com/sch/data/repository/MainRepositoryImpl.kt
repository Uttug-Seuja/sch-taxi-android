package com.sch.data.repository



import com.sch.data.api.MainAPIService
import com.sch.domain.repository.MainRepository
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val mainAPIService: MainAPIService
) : MainRepository {


}