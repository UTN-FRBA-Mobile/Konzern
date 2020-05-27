package utn.frba.mobile.konzern.posts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.model.Post

class ItemPostAdapter(
    private var items: List<Post>?,
    private val onClickListener: OnItemPostClickListener
) : RecyclerView.Adapter<ItemPostViewHolder>() {
    override fun getItemCount(): Int = items?.size ?: 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemPostViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.layout_post_item, p0, false)
        return ItemPostViewHolder(
            view,
            p0.context
        )
    }

    override fun onBindViewHolder(aView: ItemPostViewHolder, position: Int) {
        val item = items?.get(position)
        setView(aView, item!!)
        aView.itemView.setOnClickListener { onClickListener.onClick(item.id) }
    }

    private fun setView(aView: ItemPostViewHolder, item: Post) {
        aView.tvDate?.text = item.date
        aView.tvSummary?.text = item.summary
        aView.tvDescription?.text = item.text
        if(item.getMainImage() != null)
            Glide.with(aView.context).load(item.getMainImage()).into(aView.imgMain!!)
        else
            aView.imgMain?.visibility = GONE
    }
}

interface OnItemPostClickListener{
    fun onClick(id: Int)
}

class ItemPostViewHolder(view: View, var context: Context) : RecyclerView.ViewHolder(view) {
    val imgMain: ImageView? = view.findViewById(R.id.img_main)
    val tvDate: TextView? = view.findViewById(R.id.tv_date)
    val tvSummary: TextView? = view.findViewById(R.id.tv_summary)
    val tvDescription: TextView? = view.findViewById(R.id.tv_description)
}