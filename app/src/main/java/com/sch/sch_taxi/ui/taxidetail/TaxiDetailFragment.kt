package com.sch.sch_taxi.ui.taxidetail

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sch.sch_taxi.R
import com.sch.sch_taxi.base.AlertDialogModel
import com.sch.sch_taxi.base.BaseFragment
import com.sch.sch_taxi.base.DefaultRedAlertDialog
import com.sch.sch_taxi.databinding.FragmentTaxiDetailBinding
import com.sch.sch_taxi.ui.taxidetail.adapter.CustomBalloonAdapter
import com.sch.sch_taxi.ui.taxidetail.bottom.BottomTaxiMore
import com.sch.sch_taxi.ui.taxidetail.bottom.BottomTaxiReport
import com.sch.sch_taxi.ui.taxidetail.bottom.TaxiMoreType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.roundToLong
import kotlin.math.sin


@AndroidEntryPoint
class TaxiDetailFragment :
    BaseFragment<FragmentTaxiDetailBinding, TaxiDetailViewModel>(R.layout.fragment_taxi_detail) {

    private val TAG = "TaxiDetailFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_taxi_detail

    override val viewModel: TaxiDetailViewModel by viewModels()
    private val navController by lazy { findNavController() }
//    private val eventListener = MarkerEventListener(requireActivity())   // 마커 클릭 이벤트 리스너
    lateinit var mapView : MapView
    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    is TaxiDetailNavigationAction.NavigateToBack -> navController.popBackStack()
                    is TaxiDetailNavigationAction.NavigateToTaxiMoreBottomDialog -> taxiMoreBottomDialog(
                        taxiId = it.taxiId,
                        sendUserId = it.sendUserId
                    )
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initAdapter() {
        mapView = MapView(activity)
        binding.mapView.addView(mapView)
        val customBalloonAdapter = CustomBalloonAdapter(layoutInflater)

//        mapView.setPOIItemEventListener(eventListener)  // 마커 클릭 이벤트 리스너 등록
        mapView.setCalloutBalloonAdapter(customBalloonAdapter)  // 커스텀 말풍선 등록


        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOff
        val mapPoint = MapPoint.mapPointWithGeoCoord(
            (36.77319581029296 + 36.77319581029296) / 2,
            (126.93359085592283 + 126.951393082675) / 2
        )
        mapView.setMapCenterPointAndZoomLevel(mapPoint, 6, true)
        addItemsAndMarkers()
        mapView.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.scrollView.requestDisallowInterceptTouchEvent(true)
                    binding.scrollView.requestDisallowInterceptTouchEvent(true)

                }
                MotionEvent.ACTION_UP -> binding.scrollView.requestDisallowInterceptTouchEvent(true)
                MotionEvent.ACTION_MOVE -> binding.scrollView.requestDisallowInterceptTouchEvent(
                    true
                )
            }
            false
        })
    }

    override fun initAfterBinding() {
    }

    private fun taxiMoreBottomDialog(
        taxiId: Int,
        sendUserId: Int
    ) {
        val dialog: BottomTaxiMore = BottomTaxiMore {
            when (it) {
                is TaxiMoreType.Update -> {
                    viewModel.onClickedTaxiUpdateClicked()
                }
                is TaxiMoreType.Delete -> taxiDeleteDialog(taxiId = taxiId)
                TaxiMoreType.UserDeclare -> usersBlockDialog(sendUserId = sendUserId)
                TaxiMoreType.Report -> reportDialog(taxiId = taxiId)
            }
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun taxiDeleteDialog(taxiId: Int) {
        val res = AlertDialogModel(
            title = "이 게시글를 삭제할까요?",
            description = "게시글를 삭제하면 볼 수 없어요",
            positiveContents = "삭제하기",
            negativeContents = "취소"
        )
        val dialog: DefaultRedAlertDialog = DefaultRedAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                toastMessage("게시글를 삭제했습니다")
                // api
                viewModel.onTaxiDeleteClicked(taxiId)
            },
            clickToNegative = {
                toastMessage("아니요")
            }
        )
        dialog.show(childFragmentManager, TAG)
    }

    private fun usersBlockDialog(sendUserId: Int) {
        val res = AlertDialogModel(
            title = "이 유저를 차단할까요?",
            description = "앞으로 이 유저의 글을 볼 수 없어요",
            positiveContents = "차단하기",
            negativeContents = getString(R.string.no)
        )
        val dialog: DefaultRedAlertDialog = DefaultRedAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                viewModel.onClickedUserReport(sendUserId = sendUserId)
                toastMessage("유저를 차단했습니다")
            },
            clickToNegative = {
                toastMessage("아니요")
            }
        )
        dialog.show(childFragmentManager, TAG)
    }

    private fun reportDialog(taxiId: Int) {
        val bottomSheet = BottomTaxiReport(
        ) { reportReason ->
            toastMessage("게시글를 신고했습니다")
            viewModel.onClickedReport(taxiId = taxiId, reportReason = reportReason)
        }
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }

    // 검색 결과 처리 함수
    @SuppressLint("NotifyDataSetChanged")
    fun addItemsAndMarkers() {

        // 검색 결과 있음
        mapView.removeAllPOIItems() // 지도의 마커 모두 제거

        // 지도에 마커 추가
        var point = MapPOIItem()
        point.apply {
            itemName = "순천향대학교 후문"// 마커 이름
            mapPoint = MapPoint.mapPointWithGeoCoord( // 좌표
                36.77319581029296,
                126.93359085592283

            )
            markerType = MapPOIItem.MarkerType.BluePin // 마커 모양
            selectedMarkerType = MapPOIItem.MarkerType.RedPin // 클릭 시 마커 모양
        }

        mapView.addPOIItem(point)

        point = MapPOIItem()
        point.apply {
            itemName = "신창역"// 마커 이름
            mapPoint = MapPoint.mapPointWithGeoCoord( // 좌표
                36.7696422998843,
                126.951393082675

            )
            markerType = MapPOIItem.MarkerType.BluePin // 마커 모양
            selectedMarkerType = MapPOIItem.MarkerType.RedPin // 클릭 시 마커 모양
        }

        var distance =
            calDist(36.77319581029296, 126.93359085592283, 36.7696422998843, 126.951393082675)


        // http://www.taxi.or.kr/02/01.php <= 기역별 택시 요금안내
        var fee = 0
        if (distance - 2000 <= 0) {
            binding.feeText.text = "약 3300원"
            fee = 3300
        } else {
            distance -= 2000

            val drivingFee = distance / 131
            fee = (3300 + drivingFee * 100).toInt()
            binding.feeText.text = "약 ${3300 + drivingFee * 100}원"

        }

        binding.paymentFeeText.text = "약 ${fee / 3}원"

        Log.d(
            "ttt",
            calDist(
                36.77319581029296,
                126.93359085592283,
                36.7696422998843,
                126.951393082675
            ).toString()
        )
        mapView.addPOIItem(point)


    }

    // 좌표로 거리구하기
    private fun calDist(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Long {
        val EARTH_R = 6371000.0
        val rad = Math.PI / 180
        val radLat1 = rad * lat1
        val radLat2 = rad * lat2
        val radDist = rad * (lon1 - lon2)

        var distance = sin(radLat1) * sin(radLat2)
        distance += cos(radLat1) * cos(radLat2) * cos(radDist)
        val ret = EARTH_R * acos(distance)

        return ret.roundToLong() // 미터 단위
    }
}
