package com.sch.sch_taxi.ui.reservationcreate

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentReservationCreateBinding
import com.sch.sch_taxi.ui.reservationcreate.adapter.KakaoLocalAdapter
import com.sch.sch_taxi.ui.reservationcreate.bottom.BottomSelectGander
import com.sch.sch_taxi.ui.reservationcreate.bottom.BottomSelectSeat
import com.sch.sch_taxi.ui.reservationcreate.bottom.BottomTaxiReservationPicker
import com.sch.sch_taxi.ui.reservationcreate.bottom.GanderType
import com.sch.sch_taxi.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class ReservationCreateFragment :
    BaseFragment<FragmentReservationCreateBinding, ReservationCreateViewModel>(R.layout.fragment_reservation_create) {

    private val TAG = "TaxiCreateFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_reservation_create

    override val viewModel: ReservationCreateViewModel by viewModels()
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
                    is ReservationCreateNavigationAction.NavigateToBack -> navController.popBackStack()
                    is ReservationCreateNavigationAction.NavigateToSelectReservation -> reservationTaxiSend()
                    is ReservationCreateNavigationAction.NavigateToSelectGender -> selectGanderBottomDialog()
                    is ReservationCreateNavigationAction.NavigateToSelectSeat -> selectSeatBottomDialog()
                    is ReservationCreateNavigationAction.NavigateToTaxiDetail -> navigate(ReservationCreateFragmentDirections.actionTaxiCreateFragmentToTaxiDetailFragment(it.id))
                    is ReservationCreateNavigationAction.NavigateToKeywordClicked -> { deleteEditTextFocus() }
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
            viewModel.departureDate.value = it.toString()
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
        val dialog: BottomSelectSeat = BottomSelectSeat(emptyList()) {
            lifecycleScope.launchWhenStarted {
                viewModel.seatEvent.emit("${it}번 자리")

                viewModel.seatPosition.value = when (it) {
                    1 -> "SEAT_1"
                    2 -> "SEAT_2"
                    3 -> "SEAT_3"
                    4 -> "SEAT_4"
                    else -> "SEAT_1"

                }
            }
        }
        dialog.show(childFragmentManager, TAG)
    }
}
