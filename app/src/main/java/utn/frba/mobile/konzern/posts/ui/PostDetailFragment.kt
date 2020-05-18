package utn.frba.mobile.konzern.posts.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_post_detail.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.adapter.ImageSliderAdapter
import utn.frba.mobile.konzern.posts.model.Post

class PostDetailFragment : PostBaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedItem.observe(viewLifecycleOwner, Observer<Post> {
            setView(it)
        })
    }

    private fun setView(item: Post){
        vTextViewDatePostDetail?.text = item.date
        vTextSummaryPostDetail?.text = item.summary
        vTextDescriptionPostDetail.text = item.text
        setImageSlider(item.images)
    }

    private fun setImageSlider(images: List<Uri>) {
        if(images.count() > 0) {
            vSliderImagePostDetail.visibility = View.VISIBLE
            vSliderImagePostDetail.setSliderAdapter(ImageSliderAdapter(images), false)
        }
        else
            vSliderImagePostDetail.visibility = View.GONE
    }
}