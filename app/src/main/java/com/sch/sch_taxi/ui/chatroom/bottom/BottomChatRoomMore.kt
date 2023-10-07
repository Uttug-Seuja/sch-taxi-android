package com.sch.sch_taxi.ui.chatroom.bottom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sch.sch_taxi.R
import com.sch.sch_taxi.ui.reservationdetail.bottom.TaxiMoreType

class BottomChatRoomMore(
    val callback: (type: ChatRoomMoreType) -> Unit
) : BottomSheetDialogFragment() {
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
        return inflater.inflate(R.layout.dialog_bottom_chat_room_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val update = requireView().findViewById<TextView>(R.id.update_btn)
        val delete = requireView().findViewById<TextView>(R.id.delete_btn)
        val userDeclare = requireView().findViewById<TextView>(R.id.user_declare_btn)
        val report = requireView().findViewById<TextView>(R.id.report_btn)
        val close = requireView().findViewById<TextView>(R.id.close_btn)

        update.setOnClickListener {
            callback.invoke(ChatRoomMoreType.Update)
            dismiss()
        }
        delete.setOnClickListener {
            callback.invoke(ChatRoomMoreType.Delete)
            dismiss()
        }
        userDeclare.setOnClickListener {
            callback.invoke(ChatRoomMoreType.UserDeclare)
            dismiss()
        }
        report.setOnClickListener {
            callback.invoke(ChatRoomMoreType.Report)
            dismiss()
        }
        close.setOnClickListener {
            dismiss()
        }
    }
}

sealed class ChatRoomMoreType {
    object Update : ChatRoomMoreType()
    object Delete : ChatRoomMoreType()
    object UserDeclare : ChatRoomMoreType()
    object Report : ChatRoomMoreType()

}

