package com.sch.sch_taxi.ui.reservationsearch

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sch.domain.model.Keyword
import com.sch.domain.model.SearchHistory
import com.sch.domain.model.SearchHistoryList
import com.sch.domain.model.Taxis
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.main.CreateSearchHistoryUseCase
import com.sch.domain.usecase.main.DeleteSearchHistoryListUseCase
import com.sch.domain.usecase.main.DeleteSearchHistoryUseCase
import com.sch.domain.usecase.main.GetRecommendKeywordUseCase
import com.sch.domain.usecase.main.GetReservationKeywordUseCase
import com.sch.domain.usecase.main.GetSearchHistoryUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.ui.reservationsearch.adapter.createReservationKeywordPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationSearchViewModel @Inject constructor(
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val createSearchHistoryUseCase: CreateSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase,
    private val deleteSearchHistoryListUseCase: DeleteSearchHistoryListUseCase,
    private val getReservationKeywordUseCase: GetReservationKeywordUseCase,
    private val getRecommendKeywordUseCase: GetRecommendKeywordUseCase
) : BaseViewModel(), ReservationSearchActionHandler {

    private val TAG = "ReservationSearchViewModel"
    private val _navigationHandler: MutableSharedFlow<ReservationSearchNavigationAction> =
        MutableSharedFlow<ReservationSearchNavigationAction>()
    val navigationHandler: SharedFlow<ReservationSearchNavigationAction> =
        _navigationHandler.asSharedFlow()

    var searchTitleEvent: MutableStateFlow<String> = MutableStateFlow("")

    private val _taxiSearchEvent: MutableStateFlow<Taxis> =
        MutableStateFlow(Taxis(emptyList()))
    val taxiSearchEvent: StateFlow<Taxis> = _taxiSearchEvent

    private val _taxiSearchHistoryEvent: MutableStateFlow<SearchHistoryList> = MutableStateFlow(
        SearchHistoryList(
            emptyList()
        )
    )
    val taxiSearchHistoryEvent: StateFlow<SearchHistoryList> = _taxiSearchHistoryEvent
    val taxiSearchHistoryIdxEvent = MutableStateFlow<Int>(0)

    var reservationSearchEvent: Flow<PagingData<Keyword>> = emptyFlow()
    var recommendKeywordEvent = MutableStateFlow(emptyList<Keyword>())


    init {
        getReservationKeywordList()
        getRecommendKeyword()
    }

    @OptIn(FlowPreview::class)
    fun getReservationKeywordList() {
        /**
         * 페이징은 한번 만들고 끝인 것인가?!
         *
         *
         * */
        baseViewModelScope.launch {
            searchTitleEvent.debounce(200).collectLatest { keyword ->
                reservationSearchEvent = createReservationKeywordPager(
                    getReservationKeywordUseCase = getReservationKeywordUseCase,
                    keyword = keyword
                ).flow.cachedIn(
                    baseViewModelScope
                )
            }
            dismissLoading()
        }
    }

    fun getRecommendKeyword(){
        showLoading()
        baseViewModelScope.launch {
            getRecommendKeywordUseCase()
                .onSuccess {
                    recommendKeywordEvent.value = it
                    Log.d("ttt recommendKeywordEvent", it.toString())
                }
                .onError {
                    Log.d("ttt recommendKeywordEvent", it.toString())

                }
        }
        dismissLoading()
    }

    fun getSearchHistoryList() {
        showLoading()
        baseViewModelScope.launch {

            getSearchHistoryUseCase()
                .onSuccess {
                    _taxiSearchHistoryEvent.value = it
                    Log.d("ttt 최근 검색어", "최근 검색어 $it")
                }
                .onError { e ->
                    when (e) {
                        is SQLiteException -> _toastMessage.emit("데이터 베이스 에러가 발생하였습니다.")
                        else -> _toastMessage.emit("시스템 에러가 발생 하였습니다.")
                    }
                }

        }
        dismissLoading()
    }

    fun createSearchHistory() {
        showLoading()
        baseViewModelScope.launch {
            Log.d("Ttt searchTitleEvent", searchTitleEvent.value.toString())
            val searchHistory = SearchHistory(
                searchHistoryIdx = null,
                title = searchTitleEvent.value
            )

            createSearchHistoryUseCase(searchHistory)
                .onSuccess {
                    Log.d("ttt 최근 검색어", "최근 검색어")
                }
                .onError { e ->
                    when (e) {
                        is SQLiteException -> _toastMessage.emit("데이터 베이스 에러가 발생하였습니다.")
                        else -> _toastMessage.emit("시스템 에러가 발생 하였습니다.")
                    }
                }
        }
        dismissLoading()

    }

    override fun onClickedDeleteSearchHistory(searchHistoryIdx: Int) {
        baseViewModelScope.launch {
            deleteSearchHistoryUseCase(searchHistoryIdx = searchHistoryIdx)
                .onSuccess {
                    getSearchHistoryList()
                }
                .onError {
                    _toastMessage.emit("삭제가 정상적으로 처리되지 않았습니다. ${it.message}")

                }
        }
    }

    override fun onClickedDeleteSearchHistoryList() {
        baseViewModelScope.launch {
            deleteSearchHistoryListUseCase()
                .onSuccess {
                    _taxiSearchHistoryEvent.value = SearchHistoryList(emptyList())
                }
                .onError {
                    _toastMessage.emit("삭제가 정상적으로 처리되지 않았습니다. ${it.message}")
                }
        }
    }

    override fun onClickedDeleteSearchTitle() {
        Log.d("ttt", "??")
        baseViewModelScope.launch {
            searchTitleEvent.value = ""
        }
    }

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(ReservationSearchNavigationAction.NavigateToBack)
        }
    }

    fun onTaxiSearchResult(){
        baseViewModelScope.launch {
            _navigationHandler.emit(
                ReservationSearchNavigationAction.NavigateToTaxiSearchResult(
                    searchTitle = searchTitleEvent.value
                )
            )
        }
    }

    override fun onClickedTaxiSearchResult(searchTitle: String) {
        baseViewModelScope.launch {
            _navigationHandler.emit(
                ReservationSearchNavigationAction.NavigateToTaxiSearchResult(
                    searchTitle = searchTitle
                )
            )
        }
    }

}