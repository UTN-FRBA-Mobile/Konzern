package utn.frba.mobile.konzern.reservations.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.reservations.ReservationViewModel
import utn.frba.mobile.konzern.reservations.model.Reservation

class ReservationAdapter(
    private var items: List<Reservation>?,
    private var viewModel: ReservationViewModel
) : RecyclerView.Adapter<ReservationViewHolder>() {
    override fun getItemCount(): Int = items?.size ?: 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReservationViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.reservations_card_item, p0, false)
        return ReservationViewHolder(
            view,
            p0.context
        )
    }

    override fun onBindViewHolder(aView: ReservationViewHolder, position: Int) {
        val item = items?.get(position)
        setView(aView, item!!)
    }

    private fun setView(aView: ReservationViewHolder, item: Reservation) {
        var date: Array<String> = item.date.split('/').toTypedArray()
        date[1] = (date[1].toInt() + 1).toString() // No sabemos por qué, pero el calendario resta un mes. Así que hacemos este feo fix :(
        var forrmatedDate = date.joinToString("/")
        val hour = item.hour.toString() + ":00"
        aView.dateMain?.text = "$forrmatedDate $hour"
        aView.amenityMain?.text = viewModel.getById(item.amenityId).name

    }
}

class ReservationViewHolder(view: View, var context: Context) : RecyclerView.ViewHolder(view) {
    val dateMain: TextView? = view.findViewById(R.id.card_res_date)
    val amenityMain: TextView? = view.findViewById(R.id.reservation_amenity)
}