package utn.frba.mobile.konzern.profile

import android.os.Bundle
import utn.frba.mobile.konzern.profile.ui.profile.ProfileFragment
import utn.frba.mobile.konzern.BaseActivity
import utn.frba.mobile.konzern.R

class ProfileActivity : BaseActivity(), ProfileFragment.ProfileFragmentView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showProgress()
    }

    override fun getContentLayout(): Int {
        return R.layout.content_profile
    }

    override fun getViewTitle(): String? {
        return getString(R.string.profile_toolbar_title)
    }

}
