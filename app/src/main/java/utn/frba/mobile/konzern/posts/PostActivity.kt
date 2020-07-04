package utn.frba.mobile.konzern.posts

import android.view.View
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_post.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.viewModel.PostViewModel
import utn.frba.mobile.konzern.utils.BaseViewModel

class PostActivity : BaseActivity() {
    override val title: String? = "Publicaciones"

    override fun getCustomViewModel(): BaseViewModel {
        val vm: PostViewModel by viewModels()
        return vm
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_post
    }

    override fun showProgress(){
        vActivityContentPost.visibility = View.GONE
        vPostProgressBarLayout.visibility = View.VISIBLE
    }

    override fun hideProgress(){
        vPostProgressBarLayout.visibility = View.GONE
        vActivityContentPost.visibility = View.VISIBLE
    }
}
