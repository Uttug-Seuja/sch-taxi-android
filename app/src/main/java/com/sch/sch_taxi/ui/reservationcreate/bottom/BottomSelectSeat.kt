package com.sch.sch_taxi.ui.reservationcreate.bottom

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sch.domain.model.ParticipationInfo
import com.sch.domain.model.ParticipationInfoList
import com.sch.sch_taxi.R


class BottomSelectSeat(
    private val isCheckedSeat: List<ParticipationInfo>,
    val callback: (clickId: Int) -> Unit
) : BottomSheetDialogFragment(

) {
    private lateinit var dlg: BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 이 코드를 실행하지 않으면 XML에서 round 처리를 했어도 적용되지 않는다.
        dlg = (super.onCreateDialog(savedInstanceState).apply {
            // window?.setDimAmount(0.2f) // Set dim amount here
            setOnShowListener {
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)

                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.isDraggable = true
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }) as BottomSheetDialog
        return dlg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.dialog_bottom_select_seat,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var clicked = -1
        val seat1 = requireView().findViewById<TextView>(R.id.seat1_select_btn)
        val seat2 = requireView().findViewById<TextView>(R.id.seat2_select_btn)
        val seat3 = requireView().findViewById<TextView>(R.id.seat3_select_btn)
        val seat4 = requireView().findViewById<TextView>(R.id.seat4_select_btn)
        val seatAnswer = requireView().findViewById<TextView>(R.id.seat_answer_btn)
        val seatList = listOf<TextView>(seat1, seat2, seat3, seat4)
        val seatHash =
            hashMapOf<String, Int>("SEAT_1" to 0, "SEAT_2" to 1, "SEAT_3" to 2, "SEAT_4" to 3)
        val sex =
            hashMapOf<String, String>("MAN" to "남자", "WOMAN" to "여자")


        isCheckedSeat.forEach {
            when (it.seatPosition) {
                "SEAT_1" -> {
                    seatList[0].isSelected = true
                    seatList[0].isEnabled = false
                    seat1.text = "${it.userInfo.name}\n(${sex[it.userInfo.gender]})"
                }

                "SEAT_2" -> {
                    seatList[1].isSelected = true
                    seatList[1].isEnabled = false
                    seat2.text = "${it.userInfo.name}\n(${sex[it.userInfo.gender]})"
                }

                "SEAT_3" -> {
                    seatList[2].isSelected = true
                    seatList[2].isEnabled = false
                    seat3.text = "${it.userInfo.name}\n(${sex[it.userInfo.gender]})"
                }

                "SEAT_4" -> {
                    seatList[3].isSelected = true
                    seatList[3].isEnabled = false
                    seat4.text = "${it.userInfo.name}\n(${sex[it.userInfo.gender]})"
                }

            }
        }

        seat1.setOnClickListener {
            seatList.isSelected()

            if (clicked != 1) {
                seat1.isSelected = true
                clicked = 1

            } else {
                clicked = -1
            }

            isCheckedSeat.forEach {
                seatList[seatHash[it.seatPosition]!!].isSelected = true
            }
        }

        seat2.setOnClickListener {
            seatList.isSelected()

            if (clicked != 2) {
                seat2.isSelected = true
                clicked = 2

            } else {
                clicked = -1
            }

            isCheckedSeat.forEach {
                seatList[seatHash[it.seatPosition]!!].isSelected = true
            }
        }

        seat3.setOnClickListener {
            seatList.isSelected()

            if (clicked != 3) {
                seat3.isSelected = true
                clicked = 3

            } else {
                clicked = -1
            }

            isCheckedSeat.forEach {
                seatList[seatHash[it.seatPosition]!!].isSelected = true
            }
        }

        seat4.setOnClickListener {
            seatList.isSelected()

            if (clicked != 4) {
                seat4.isSelected = true
                clicked = 4

            } else {
                clicked = -1
            }

            isCheckedSeat.forEach {
                seatList[seatHash[it.seatPosition]!!].isSelected = true
            }
        }


        seatAnswer.setOnClickListener {
            if (clicked != -1) {

                callback.invoke(
                    clicked
                )
                dismiss()
            }
        }


    }
}

fun List<TextView>.isSelected() {
    this.forEach {
        it.isSelected = false
    }

}
