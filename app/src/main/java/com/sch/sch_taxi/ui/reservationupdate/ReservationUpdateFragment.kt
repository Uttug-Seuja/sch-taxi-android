package com.sch.sch_taxi.ui.reservationupdate

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.databinding.FragmentReservationUpdateBinding
import com.sch.sch_taxi.ui.reservationupdate.adapter.KakaoLocalUpdateAdapter
import com.sch.sch_taxi.ui.reservationcreate.bottom.BottomSelectGander
import com.sch.sch_taxi.ui.reservationcreate.bottom.BottomSelectSeat
import com.sch.sch_taxi.ui.reservationcreate.bottom.BottomTaxiReservationPicker
import com.sch.sch_taxi.ui.reservationcreate.bottom.GanderType
import com.sch.sch_taxi.ui.reservationdetail.ReservationDetailFragmentArgs
import com.sch.sch_taxi.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class ReservationUpdateFragment :
    BaseFragment<FragmentReservationUpdateBinding, ReservationUpdateViewModel>(R.layout.fragment_reservation_update) {

    private val TAG = "ReservationUpdateFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_reservation_update

    override val viewModel: ReservationUpdateViewModel by viewModels()
    private val startPlacesAdapter by lazy { KakaoLocalUpdateAdapter(viewModel) }
    private val destinationsAdapter by lazy { KakaoLocalUpdateAdapter(viewModel) }
    private val args: ReservationUpdateFragmentArgs by navArgs()

    private val navController by lazy { findNavController() }
    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        viewModel.reservationId.value = args.reservationId
        exception = viewModel.errorEvent
        initAdapter()
        initEditText()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    is ReservationUpdateNavigationAction.NavigateToBack -> navController.popBackStack()
                    is ReservationUpdateNavigationAction.NavigateToSelectReservation -> reservationTaxiSend()
                    is ReservationUpdateNavigationAction.NavigateToTaxiDetail -> navController.popBackStack()

                    is ReservationUpdateNavigationAction.NavigateToKeywordClicked -> {
                        deleteEditTextFocus()
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {

        binding.etStartPlace.searchEditTextOnFocusChangeListener(
            binding.rvStartPlaces,
            binding.llSelectBox,
            viewModel.isStartKeyword
        )
        binding.mainTaxiCreate.setOnTouchListener { _, _ ->
            deleteEditTextFocus()
            false
        }

        binding.etDestination.searchEditTextOnFocusChangeListener(
            binding.rvDestinations,
            binding.llSelectBox,
            viewModel.isDestinationsKeyword
        )
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
}
