package utn.frba.mobile.konzern

import android.content.Intent
import androidx.activity.viewModels
import utn.frba.mobile.konzern.news.NewsViewModel
import utn.frba.mobile.konzern.posts.viewModel.PostViewModel
import utn.frba.mobile.konzern.profile.ProfileActivity

class MainActivity : BaseActivity() {
    override val startsNewFlow: Boolean = false

    override fun getContentLayout(): Int {
        return R.layout.content_home
    }

    override fun getViewTitle(): String? {
        return getString(R.string.app_name)
    }

    override fun refreshData() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
