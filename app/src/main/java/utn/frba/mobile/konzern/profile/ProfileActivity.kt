package utn.frba.mobile.konzern.profile

import utn.frba.mobile.konzern.BaseActivity
import utn.frba.mobile.konzern.R

class ProfileActivity : BaseActivity() {
    override fun getContentLayout(): Int {
        return R.layout.content_profile
    }

    override fun getViewTitle(): String? {
        return getString(R.string.profile_toolbar_title)
    }
}
