package utn.frba.mobile.konzern.posts

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_post.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.contact.ContactActivity
import utn.frba.mobile.konzern.customviews.ToolbarMenuInterface
import utn.frba.mobile.konzern.posts.viewModel.PostViewModel
import utn.frba.mobile.konzern.profile.ProfileActivity
import utn.frba.mobile.konzern.reservations.ReservationsActivity

class PostActivity : AppCompatActivity(), ToolbarMenuInterface {
    val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        vActivityPostToolbar.setTitle(getString(R.string.posts_toolbar_title))

        viewModel.showProgressIndicator.observe(this, Observer {
            if(it!!){
                showProgress()
            }else{
                hideProgress()
            }
        })
    }

    private fun showProgress(){
        vActivityContentPost.visibility = GONE
        vPostProgressBarLayout.visibility = VISIBLE
    }

    private fun hideProgress(){
        vPostProgressBarLayout.visibility = GONE
        vActivityContentPost.visibility = VISIBLE
    }

    override fun onMenuMyProfileClicked() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onMenuSeeReservationsClicked() {
        val intent = Intent(this, ReservationsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onToolbarLogoClicked() {
        finish()
    }

    override fun onMenuContactInfoClicked() {
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
        finish()
    }
}
