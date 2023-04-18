package com.sch.sch_taxi.ui.taxicreate

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.KakaoLocals
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentNotificationsBinding
import com.sch.sch_taxi.databinding.FragmentTaxiCreateBinding
import com.sch.sch_taxi.ui.notifications.NotificationsNavigationAction
import com.sch.sch_taxi.ui.notifications.adapter.NotificationsAdapter
import com.sch.sch_taxi.ui.taxicreate.adapter.KakaoLocalAdapter
import com.sch.sch_taxi.ui.taxicreate.bottom.BottomSelectGander
import com.sch.sch_taxi.ui.taxicreate.bottom.BottomSelectSeat
import com.sch.sch_taxi.ui.taxicreate.bottom.BottomTaxiReservationPicker
import com.sch.sch_taxi.ui.taxicreate.bottom.GanderType
import com.sch.sch_taxi.ui.taxidetail.bottom.BottomTaxiMore
import com.sch.sch_taxi.ui.taxidetail.bottom.TaxiMoreType
import com.sch.sch_taxi.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class TaxiCreateFragment :
    BaseFragment<FragmentTaxiCreateBinding, TaxiCreateViewModel>(R.layout.fragment_taxi_create) {

    private val TAG = "TaxiCreateFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_taxi_create

    override val viewModel: TaxiCreateViewModel by viewModels()
    private val startPlacesAdapter by lazy { KakaoLocalAdapter(viewModel) }
    private val destinationsAdapter by lazy { KakaoLocalAdapter(viewModel) }

    private val navController by lazy { findNavController() }
    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initAdapter()
        initEditText()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    is TaxiCreateNavigationAction.NavigateToBack -> navController.popBackStack()
                    is TaxiCreateNavigationAction.NavigateToSelectReservation -> reservationTaxiSend()
                    is TaxiCreateNavigationAction.NavigateToSelectGender -> selectGanderBottomDialog()
                    is TaxiCreateNavigationAction.NavigateToSelectSeat -> selectSeatBottomDialog()
                    is TaxiCreateNavigationAction.NavigateToTaxiDetail -> TODO()
                    is TaxiCreateNavigationAction.NavigateToKeywordClicked -> { deleteEditTextFocus() }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {

        binding.etStartPlace.searchEditTextOnFocusChangeListener(binding.rvStartPlaces, binding.llSelectBox, viewModel.isStartKeyword)
        binding.mainTaxiCreate.setOnTouchListener { _, _ ->
            deleteEditTextFocus()
            false
        }

        binding.etDestination.searchEditTextOnFocusChangeListener(binding.rvDestinations, binding.llSelectBox, viewModel.isDestinationsKeyword)
        binding.mainTaxiCreate.setOnTouchListener { _, _ ->
            deleteEditTextFocus()
            false
        }

    }

    private fun deleteEditTextFocus() {
        requireActivity().hideKeyboard()
        binding.etStartPlace.clearFocus()
        binding.etDestination.clearFocus()
    }


    private fun initAdapter() {
        binding.rvStartPlaces.adapter = startPlacesAdapter
        binding.rvDestinations.adapter = destinationsAdapter
    }

    override fun initAfterBinding() {
    }

    private fun reservationTaxiSend() {
        val bottomSheet = BottomTaxiReservationPicker(callback = {

            Log.d("ttt send at", it.toString())
            viewModel.dateEvent.value =
                it.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"))
        })
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun selectGanderBottomDialog() {
        val dialog: BottomSelectGander = BottomSelectGander {
            when (it) {
                is GanderType.AllGander -> viewModel.genderEvent.value = "남녀 모두"
                is GanderType.ManGander -> viewModel.genderEvent.value = "남자만"
                is GanderType.WomanGander -> viewModel.genderEvent.value = "여자만"
            }
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun selectSeatBottomDialog() {
        val dialog: BottomSelectSeat = BottomSelectSeat(listOf(1)) {
            lifecycleScope.launchWhenStarted {
                viewModel.seatEvent.emit("${it}번 자리")
            }
        }
        dialog.show(childFragmentManager, TAG)
    }
}
