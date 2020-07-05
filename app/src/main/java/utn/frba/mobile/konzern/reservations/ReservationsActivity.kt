package utn.frba.mobile.konzern.reservations

import utn.frba.mobile.konzern.BaseActivity
import utn.frba.mobile.konzern.R

class ReservationsActivity : BaseActivity(){
    override fun getContentLayout(): Int {
        return R.layout.reservations_content
    }

    override fun getViewTitle(): String? {
        return getString(R.string.reservations_title_toolbar)
    }
}
