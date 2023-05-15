package com.sch.sch_taxi.ui.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sch.domain.model.Reservation
import com.sch.sch_taxi.databinding.HolderReservationBinding
import com.sch.sch_taxi.ui.home.HomeActionHandler

class ReservationAdapter(
    private val eventListener: HomeActionHandler,
) : PagingDataAdapter<Reservation, ReservationAdapter.ViewHolder>(ReservationItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    eventListener = this@ReservationAdapter.eventListener
                }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class ViewHolder(
        private val binding: HolderReservationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val gender =
            hashMapOf<String, String>("ALL" to "남녀모두", "MAN" to "남자만", "WOMAN" to "여자만")
        private val reserveStatus =
            hashMapOf<String, String>(
                "POSSIBLE" to "신청가능",
                "IMMINENT" to "마감임박!",
                "DEADLINE" to "마감"
            )
        private val stateTextColor = hashMapOf<String, String>(
            "POSSIBLE" to "#FFFFFF",
            "IMMINENT" to "#FFFFFF",
            "DEADLINE" to "#cccccc"
        )
        private val stateBtnColor = hashMapOf<String, String>(
            "POSSIBLE" to "#1570ff",
            "IMMINENT" to "#FF4D37",
            "DEADLINE" to "#EEEEEE"
        )

        fun bind(item: Reservation) {
            binding.apply {
                holder = item
                startTimeText.text = item.departureDate.substring(11, 16)
                binding.subTitleText.text =
                    "${item.startPoint}->${item.destination}•${gender[item.gender]}"
                binding.stateBtn.setCardBackgroundColor(Color.parseColor(stateBtnColor[item.reservationStatus]))
                binding.stateText.text = reserveStatus[item.reservationStatus]
                binding.stateText.setTextColor(Color.parseColor(stateTextColor[item.reservationStatus]))
                executePendingBindings()
            }
        }
    }


    internal object ReservationItemDiffCallback : DiffUtil.ItemCallback<Reservation>() {
        override fun areItemsTheSame(oldItem: Reservation, newItem: Reservation) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Reservation, newItem: Reservation) =
            oldItem.equals(newItem)
    }
}