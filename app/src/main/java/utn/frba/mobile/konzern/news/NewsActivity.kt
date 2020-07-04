package utn.frba.mobile.konzern.news

import android.view.View
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_post.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.BaseActivity
import utn.frba.mobile.konzern.utils.BaseViewModel

class NewsActivity: BaseActivity() {
    override val title: String? = "Novedades"

    override fun getCustomViewModel(): BaseViewModel {
        val vm: NewsViewModel by viewModels()
        return vm
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_news
    }

    override fun showProgress(){
        vActivityContentNews.visibility = View.GONE
        vNewsProgressBarLayout.visibility = View.VISIBLE
    }

    override fun hideProgress(){
        vNewsProgressBarLayout.visibility = View.GONE
        vActivityContentNews.visibility = View.VISIBLE
    }
}
