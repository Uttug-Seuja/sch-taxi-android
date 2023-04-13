package com.sch.sch_taxi.ui.taxicreate.bottom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sch.sch_taxi.R
import com.sch.sch_taxi.ui.taxidetail.bottom.TaxiMoreType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class BottomSelectGander(
    val callback: (type: GanderType) -> Unit
) : BottomSheetDialogFragment(

) {
    private lateinit var dlg: BottomSheetDialog

    var reservationDate: String? = null
    var reservationTime: String? = null

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
            R.layout.dialog_bottom_select_gander,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allGander = requireView().findViewById<TextView>(R.id.tv_all_gander)
        val manGander = requireView().findViewById<TextView>(R.id.tv_man_gander)
        val womanGander = requireView().findViewById<TextView>(R.id.tv_woman_gander)
//        val close = requireView().findViewById<TextView>(R.id.close_btn)


        allGander.setOnClickListener {
            callback.invoke(GanderType.AllGander)
            dismiss()
        }
        manGander.setOnClickListener {
            callback.invoke(GanderType.ManGander)
            dismiss()
        }
        womanGander.setOnClickListener {
            callback.invoke(GanderType.WomanGander)
            dismiss()
        }
//        close.setOnClickListener {
//            dismiss()
//        }
    }
}

sealed class GanderType {
    object AllGander: GanderType()
    object ManGander: GanderType()
    object WomanGander: GanderType()

}

