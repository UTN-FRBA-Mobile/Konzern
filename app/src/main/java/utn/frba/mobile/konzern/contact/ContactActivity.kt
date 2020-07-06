package utn.frba.mobile.konzern.contact

import android.widget.Toast
import utn.frba.mobile.konzern.BaseActivity
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.contact.ui.ContactFragment

class ContactActivity : BaseActivity() , ContactFragment.ContactView {
    override fun getContentLayout(): Int {
        return R.layout.content_contact
    }

    override fun getViewTitle(): String? {
        return getString(R.string.contact_toolbar_title)
    }

    override fun errorGettinContactInfo() {
        Toast.makeText(this, R.string.contact_error_getting_info, Toast.LENGTH_SHORT).show()
    }
}