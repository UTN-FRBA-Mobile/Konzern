package utn.frba.mobile.konzern.news

import androidx.fragment.app.activityViewModels
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.adapter.OnItemPostClickListener
import utn.frba.mobile.konzern.posts.ui.PostMainFragment
import utn.frba.mobile.konzern.posts.viewModel.BasePostViewModel

class NewsMainFragment : PostMainFragment(), OnItemPostClickListener {
    override fun getCustomViewModel(): BasePostViewModel {
        val vm: NewsViewModel by activityViewModels()
        return vm
    }

    override fun getItemNavId(): Int {
        return R.id.action_newsMainFragment_to_newsDetailFragment
    }
}
