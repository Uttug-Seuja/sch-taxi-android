package com.sch.sch_taxi.ui.taxisearch

import android.database.sqlite.SQLiteException
import android.util.Log
import com.sch.domain.model.SearchHistory
import com.sch.domain.model.SearchHistoryList
import com.sch.domain.model.Taxis
import com.sch.domain.onError
import com.sch.domain.onSuccess
import com.sch.domain.usecase.local.CreateSearchHistoryUseCase
import com.sch.domain.usecase.local.DeleteSearchHistoryListUseCase
import com.sch.domain.usecase.local.DeleteSearchHistoryUseCase
import com.sch.domain.usecase.local.GetSearchHistoryUseCase
import com.sch.sch_taxi.base.BaseViewModel
import com.sch.sch_taxi.ui.home.HomeNavigationAction
import com.sch.sch_taxi.ui.notifications.NotificationsNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaxiSearchViewModel @Inject constructor(
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val createSearchHistoryUseCase: CreateSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase,
    private val deleteSearchHistoryListUseCase: DeleteSearchHistoryListUseCase
) : BaseViewModel(), TaxiSearchActionHandler {

    private val TAG = "TaxiSearchViewModel"
    private val _navigationHandler: MutableSharedFlow<TaxiSearchNavigationAction> =
        MutableSharedFlow<TaxiSearchNavigationAction>()
    val navigationHandler: SharedFlow<TaxiSearchNavigationAction> =
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

    override fun onClickedBack() {
        baseViewModelScope.launch {
            _navigationHandler.emit(TaxiSearchNavigationAction.NavigateToBack)
        }
    }

    override fun onClickedTaxiSearchResult() {
        baseViewModelScope.launch {
            _navigationHandler.emit(TaxiSearchNavigationAction.NavigateToTaxiSearchResult)
        }
    }

}