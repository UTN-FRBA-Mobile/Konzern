package utn.frba.mobile.konzern.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.model.Post

class HomePostItemAdapter(
    private var items: List<Post>?,
    private val onClickListener: OnHomeItemPostClickListener,
    private val tag: String
) : RecyclerView.Adapter<HomePostItemAdapter.HomeItemPostViewHolder>() {
    override fun getItemCount(): Int = items?.size ?: 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HomeItemPostViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.layout_home_post_item, p0, false)
        return HomeItemPostViewHolder(
            view,
            p0.context
        )
    }

    override fun onBindViewHolder(aView: HomeItemPostViewHolder, position: Int) {
        val item = items?.get(position)
        setView(aView, item!!)
        aView.itemView.setOnClickListener { onClickListener.onItemClick(item.id!!, tag) }
    }

    private fun setView(aView: HomeItemPostViewHolder, item: Post) {
        aView.tvDate?.text = item.getFormattedDate()
        aView.tvSummary?.text = item.summary
        aView.tvDescription?.text = item.description
        if(item.getMainImage() != null) {
            Glide.with(aView.context).load(item.getMainImage()).into(aView.imgMain!!)
        } else {
            aView.imgMain?.visibility = View.GONE
            aView.textDetail?.layoutParams = LinearLayout.LayoutParams( 0, LinearLayout.LayoutParams.WRAP_CONTENT, 3.0f )
        }

        if(item.isClaim) {
            aView.isClaimIcon.visibility = View.VISIBLE
            aView.claimText.visibility = View.VISIBLE
        }
    }

    interface OnHomeItemPostClickListener{
        fun onItemClick(id: String, tag: String)
    }

    class HomeItemPostViewHolder(view: View, var context: Context) : RecyclerView.ViewHolder(view) {
        val imgMain: ImageView? = view.findViewById(R.id.img_main)
        val textDetail: LinearLayout? = view.findViewById(R.id.text_detail)
        val tvDate: TextView? = view.findViewById(R.id.tv_date)
        val tvSummary: TextView? = view.findViewById(R.id.tv_summary)
        val tvDescription: TextView? = view.findViewById(R.id.tv_description)
        val isClaimIcon: AppCompatImageButton = view.findViewById(R.id.post_item_claim)
        val claimText: TextView = view.findViewById(R.id.post_item_claim_text)
    }
}

