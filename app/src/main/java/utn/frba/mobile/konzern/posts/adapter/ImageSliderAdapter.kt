package utn.frba.mobile.konzern.posts.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.smarteist.autoimageslider.SliderViewAdapter
import utn.frba.mobile.konzern.R

class ImageSliderAdapter(
    private var items: List<Drawable>?
) : SliderViewAdapter<ImageSliderItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup): ImageSliderItemViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_image_slider_item, parent, false)
        return ImageSliderItemViewHolder(view, parent.context)
    }

    override fun getCount(): Int {
        return if(items == null) 0 else items!!.count()
    }

    override fun onBindViewHolder(viewHolder: ImageSliderItemViewHolder, position: Int) {
        val item = items?.get(position)
        setView(viewHolder, item!!)
    }

    private fun setView(viewHolder: ImageSliderItemViewHolder, item: Drawable) {
        viewHolder.image!!.setImageDrawable(item)
    }
}

class ImageSliderItemViewHolder(view: View, var context: Context) : SliderViewAdapter.ViewHolder(view) {
    val image: ImageView? = view.findViewById(R.id.img_slider_view)
}