package utn.frba.mobile.konzern.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import utn.frba.mobile.konzern.R

class LoadingItemAdapter: RecyclerView.Adapter<LoadingItemAdapter.ItemViewHolder>() {
    override fun getItemCount(): Int = 1

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.layout_home_progress_bar, p0, false)
        return ItemViewHolder(view, p0.context)
    }

    override fun onBindViewHolder(aView: ItemViewHolder, position: Int) {}

    class ItemViewHolder(view: View, var context: Context) : RecyclerView.ViewHolder(view)
}