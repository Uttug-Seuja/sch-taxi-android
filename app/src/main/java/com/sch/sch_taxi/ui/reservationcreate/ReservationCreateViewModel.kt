package com.sch.sch_taxi.ui.reservationcreate

import android.database.sqlite.SQLiteException
import android.util.Log
import com.sch.domain.model.KakaoLocal
import com.sch.domain.model.KakaoLocals
import com.sch.domain.model.Reservation
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.runCatching
import com.sch.domain.usecase.kakao.GetResultKeywordUseCase
import com.sch.domain.usecase.main.PostReservationUseCase
import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationCreateViewModel @Inject constructor(
    private val getResultKeywordUseCase: GetResultKeywordUseCase,
    private val postReservationUseCase: PostReservationUseCase
) : BaseViewModel(), ReservationCreateActionHandler {

    private val TAG = "TaxiCreateViewModel"

    private val _navigationHandler: MutableSharedFlow<ReservationCreateNavigationAction> =
        MutableSharedFlow()
    val navigationHandler: SharedFlow<ReservationCreateNavigationAction> = _navigationHandler

    lateinit var titleEvent: MutableStateFlow<String>

    var startPlaceTitleEvent: MutableStateFlow<String> = MutableStateFlow<String>("")
    var startPlaceEvent: MutableStateFlow<KakaoLocal?> = MutableStateFlow(null)
    var startPlacesEvent: MutableStateFlow<KakaoLocals> = MutableStateFlow(KakaoLocals(emptyList()))

    var destinationTitleEvent: MutableStateFlow<String> = MutableStateFlow<String>("")
    var destinationEvent: MutableStateFlow<KakaoLocal?> = MutableStateFlow(null)
    var destinationsEvent: MutableStateFlow<KakaoLocals> =
        MutableStateFlow(KakaoLocals(emptyList()))

    val isStartKeyword: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val isDestinationsKeyword: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    var startLatitude: MutableStateFlow<Double> = MutableStateFlow<Double>(0.0)
    var startLongitude: MutableStateFlow<Double> = MutableStateFlow<Double>(0.0)

    var destinationLatitude: MutableStateFlow<Double> = MutableStateFlow<Double>(0.0)
    var destinationLongitude: MutableStateFlow<Double> = MutableStateFlow<Double>(0.0)


    lateinit var seatEvent: MutableStateFlow<String>
    lateinit var genderEvent: MutableStateFlow<String>
    lateinit var dateEvent: MutableStateFlow<String>


    init {
        baseViewModelScope.launch {
            titleEvent = MutableStateFlow("")
            seatEvent = MutableStateFlow("좌석 선택")
            genderEvent = MutableStateFlow("모집 성별")
            dateEvent = MutableStateFlow("약속 시간")

        }

        // 카카오 로컬 검색
        onClickedSearchKeyword(startPlaceTitleEvent)
        onClickedSearchKeyword(destinationTitleEvent)

    }

    private fun onClickedSearchKeyword(placeEvent: MutableStateFlow<String>) {
        baseViewModelScope.launch {
            showLoading()
            placeEvent.debounce(200).collectLatest { keyword ->
                getResultKeywordUseCase(keyword = keyword, page = 1)
                    .runCatching {
                        if (!it.body()?.documents.isNullOrEmpty()) {
                            val item = mutableListOf<KakaoLocal>()
                            it.body()!!.documents.forEach { Place ->
                                item.add(
                                    KakaoLocal(
                                        name = Place.place_name,
                                        road = Place.road_address_name,
                                        address = Place.address_name,
                                        x = Place.x.toDouble(),
                                        y = Place.y.toDouble()
                                    )
                                )
                            }
                            baseViewModelScope.launch {
                                if (placeEvent == startPlaceTitleEvent) startPlacesEvent.value =
                                    KakaoLocals(item)
                                else destinationsEvent.value = KakaoLocals(item)
                            }
                        }
                    }
            }
            dismissLoading()
        }
    }

    override fun onClickedKeyword(kakaoLocal: KakaoLocal) {

        baseViewModelScope.launch {
            if (isStartKeyword.value) {
                startPlaceTitleEvent.value = kakaoLocal.name
                startLatitude.value = kakaoLocal.x
                startLongitude.value = kakaoLocal.y
                startPlaceEvent.value = kakaoLocal
                startPlacesEvent.value = KakaoLocals(emptyList())
            }
            if (isDestinationsKeyword.value) {
                destinationTitleEvent.value = kakaoLocal.name
                destinationLatitude.value = kakaoLocal.x
                destinationLongitude.value = kakaoLocal.y
                destinationEvent.value = kakaoLocal
                destinationsEvent.value = KakaoLocals(emptyList())
            }
        }

        baseViewModelScope.launch {
            _navigationHandler.emit(ReservationCreateNavigationAction.NavigateToKeywordClicked)

        }
    }

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(ReservationCreateNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedTaxiCreate() {
        baseViewModelScope.launch {
            Log.d("ttt", titleEvent.value.toString())
            Log.d("ttt", genderEvent.value.toString())

            if (titleEvent.value.isNotEmpty() && startPlaceTitleEvent.value.isNotEmpty() && destinationTitleEvent.value.isNotEmpty() && seatEvent.value.isNotEmpty()
                && genderEvent.value != "모집 성별" && dateEvent.value != "탑승 날짜"
            ) {

                baseViewModelScope.launch {
                    postReservationUseCase(
                        title = titleEvent.value,
                        startPoint = startPlaceTitleEvent.value,
                        destination = destinationTitleEvent.value,
                        departureDate = dateEvent.value,
                        gender = genderEvent.value,
                        startLatitude = startLatitude.value,
                        startLongitude = startLongitude.value,
                        destinationLatitude = destinationLatitude.value,
                        destinationLongitude = destinationLongitude.value

                    ).onSuccess {
                        _navigationHandler.emit(ReservationCreateNavigationAction.NavigateToTaxiDetail)
                    }
                        .onError { e ->
                            Log.d("ttt", e.toString())
                            when (e) {
                                is SQLiteException -> _toastMessage.emit("데이터 베이스 에러가 발생하였습니다.")
                                else -> _toastMessage.emit("시스템 에러가 발생 하였습니다.")
                            }
                        }
                }
            } else {
                baseViewModelScope.launch {
                    _toastMessage.emit("빈 곳 없이 작성해주세요")
                }
            }
        }
    }

    override fun onClickedSelectGander() {
        baseViewModelScope.launch {
            _navigationHandler.emit(ReservationCreateNavigationAction.NavigateToSelectGender)
        }
    }

    override fun onClickedSelectReservation() {
        baseViewModelScope.launch {
            _navigationHandler.emit(ReservationCreateNavigationAction.NavigateToSelectReservation)
        }
    }

    override fun onClickedSelectSeat() {
        baseViewModelScope.launch {
            _navigationHandler.emit(ReservationCreateNavigationAction.NavigateToSelectSeat)
        }
    }
}