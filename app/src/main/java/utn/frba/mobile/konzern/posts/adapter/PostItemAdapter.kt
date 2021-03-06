package utn.frba.mobile.konzern.posts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.layout_post_item.view.*
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
        aView.itemView.setOnClickListener { onClickListener.onItemClick(item.id!!) }
    }

    private fun setView(aView: ItemPostViewHolder, item: Post) {
        aView.tvDate?.text = item.getFormattedDate()
        aView.tvSummary?.text = item.summary
        aView.tvDescription?.text = item.description
        if(item.getMainImage() != null)
            Glide.with(aView.context).load(item.getMainImage()).into(aView.imgMain!!)
        else
            aView.imgMain?.visibility = GONE
        if(item.isOwnedByUser()){
            aView.btnEdit?.setOnClickListener{ onClickListener.onEditClick(item.id!!) }
            aView.btnDelete?.setOnClickListener{ onClickListener.onDeleteClick(item.id!!) }
        } else{
            aView.layoutButtons?.visibility = GONE
        }
        if(item.isClaim) {
            aView.isClaimIcon.visibility = VISIBLE
            aView.claimText.visibility = VISIBLE
        }
    }
}

interface OnItemPostClickListener{
    fun onItemClick(id: String)
    fun onEditClick(id: String)
    fun onDeleteClick(id: String)
}

class ItemPostViewHolder(view: View, var context: Context) : RecyclerView.ViewHolder(view) {
    val imgMain: ImageView? = view.findViewById(R.id.img_main)
    val tvDate: TextView? = view.findViewById(R.id.tv_date)
    val tvSummary: TextView? = view.findViewById(R.id.tv_summary)
    val tvDescription: TextView? = view.findViewById(R.id.tv_description)
    val layoutButtons: ViewGroup? = view.findViewById(R.id.layout_buttons)
    val btnEdit: Button? = view.findViewById(R.id.btn_edit)
    val btnDelete: Button? = view.findViewById(R.id.btn_delete)
    val isClaimIcon: AppCompatImageButton = view.findViewById(R.id.post_item_claim)
    val claimText: TextView = view.findViewById(R.id.post_item_claim_text)
}