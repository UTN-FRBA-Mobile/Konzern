package utn.frba.mobile.konzern.posts.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_post_detail.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.adapter.ImageSliderAdapter
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.posts.viewModel.BasePostViewModel
import utn.frba.mobile.konzern.posts.viewModel.PostViewModel

open class PostDetailFragment : PostBaseFragment() {

    override fun getCustomViewModel(): BasePostViewModel {
        val vm: PostViewModel by activityViewModels()
        return vm
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cleanView()

        viewModel.showDetailEvent.observe(viewLifecycleOwner, Observer {
            setView(viewModel.selectedItem!!)
        })
    }

    private fun cleanView(){
        setView(Post())
    }

    private fun setView(item: Post){
        vTextViewDatePostDetail?.text = item.getFormattedDate()
        vTextSummaryPostDetail?.text = item.summary
        vTextDescriptionPostDetail.text = item.description
        setImageSlider(item.images.map { it.url.toUri() })
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
