package com.sch.sch_taxi.ui.home

import com.sch.sch_taxi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel(), HomeActionHandler {

    private val TAG = "HomeViewModel"


//    private val _bookmarkStackEvent: MutableStateFlow<BookCoverStacks> =
//        MutableStateFlow(BookCoverStacks(emptyList()))
//    val bookmarkStackEvent: StateFlow<BookCoverStacks> = _bookmarkStackEvent

    init {
        getTempList()
    }

    private fun getTempList() {
//        val test1 = BookCoverStack(
//            listOf(
//                BookCover("어린왕자(생택취페리 탄생 120주년 블라블라)", 1),
//                BookCover("붕대 감기(윤이형 소설)", 1),
//                BookCover("초록빛 힐링의 섬 아일랜드에 멈추다 하하하하", 1),
//                BookCover("호모데우스(미래의 역사)", 1)
//            )
//        )
//        val test2 = BookCoverStack(
//            listOf(
//                BookCover("어린왕자(생택취페리 탄생 120주년 블라블라)", 1),
//                BookCover("붕대 감기(윤이형 소설)", 1),
//                BookCover("초록빛 힐링의 섬 아일랜드에 멈추다 하하하하", 1),
//                BookCover("호모데우스(미래의 역사)", 1)
//            )
//        )
//        val test3 = BookCoverStack(
//            listOf(
//                BookCover("어린왕자(생택취페리 탄생 120주년 블라블라)", 1),
//                BookCover("붕대 감기(윤이형 소설)", 1),
//                BookCover("초록빛 힐링의 섬 아일랜드에 멈추다 하하하하", 1),
//                BookCover("호모데우스(미래의 역사)", 1)
//            )
//        )
//        val test4 = BookCoverStack(
//            listOf(
//                BookCover("어린왕자(생택취페리 탄생 120주년 블라블라)", 1),
//                BookCover("붕대 감기(윤이형 소설)", 1),
//                BookCover("초록빛 힐링의 섬 아일랜드에 멈추다 하하하하", 1),
//                BookCover("호모데우스(미래의 역사)", 1)
//            )
//        )
//        val test5 = BookCoverStack(
//            listOf(
//                BookCover("어린왕자(생택취페리 탄생 120주년 블라블라)", 1),
//                BookCover("붕대 감기(윤이형 소설)", 1),
//                BookCover("초록빛 힐링의 섬 아일랜드에 멈추다 하하하하", 1),
//                BookCover("호모데우스(미래의 역사)", 1)
//            )
//        )
//        val testList = BookCoverStacks(listOf(test1, test2, test3, test4, test5))
//        baseViewModelScope.launch {
//            _bookmarkStackEvent.value = testList
//        }
    }
}