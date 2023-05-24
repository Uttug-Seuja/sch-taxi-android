package com.sch.domain.usecase.main

import com.sch.domain.NetworkResult
import com.sch.domain.model.Reservation
import com.sch.domain.model.ResultSearchKeyword
import com.sch.domain.model.Token
import com.sch.domain.repository.KakaoRepository
import com.sch.domain.repository.MainRepository
import retrofit2.Response
import javax.inject.Inject

class PatchReservationUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(
        reservationId: Int,
        title: String,
        startPoint: String,
        destination: String,
        departureDate: String,
        startLatitude: Double,
        startLongitude: Double,
        destinationLatitude: Double,
        destinationLongitude: Double
    ): NetworkResult<Reservation> =
        repository.patchReservation(
            reservationId = reservationId,
            title = title,
            startPoint = startPoint,
            destination = destination,
            departureDate = departureDate,
            startLatitude = startLatitude,
            startLongitude = startLongitude,
            destinationLatitude = destinationLatitude,
            destinationLongitude = destinationLongitude
        )
}