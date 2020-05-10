package utn.frba.mobile.konzern.posts.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.model.Post
import java.time.format.DateTimeFormatter

class ItemPostAdapter(
    var items: List<Post>?,
    val onClickListener: OnItemPostClickListener
) : RecyclerView.Adapter<ItemPostViewHolder>() {
    override fun getItemCount(): Int = items?.size ?: 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemPostViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.layout_item_posts, p0, false)
        return ItemPostViewHolder(
            view,
            p0.context
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(aView: ItemPostViewHolder, position: Int) {
        val item = items?.get(position)
        setView(aView, item!!)
        aView.itemView.setOnClickListener { onClickListener.onClick(position) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setView(aView: ItemPostViewHolder, item: Post) {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        aView.tvDate?.text = item.date.format(formatter)
        aView.tvSummary?.text = item.summary
        if(item.text != null)
            aView.tvDescription?.text = item.text
    }
}

interface OnItemPostClickListener{
    fun onClick(position: Int)
}

class ItemPostViewHolder(view: View, var context: Context) : RecyclerView.ViewHolder(view) {
    val imgMain: ImageView? = view.findViewById(R.id.img_main)
    val tvDate: TextView? = view.findViewById(R.id.tv_date)
    val tvSummary: TextView? = view.findViewById(R.id.tv_summary)
    val tvDescription: TextView? = view.findViewById(R.id.tv_description)
}