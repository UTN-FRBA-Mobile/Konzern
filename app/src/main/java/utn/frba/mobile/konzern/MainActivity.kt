package utn.frba.mobile.konzern

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import utn.frba.mobile.konzern.posts.PostsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setButtonsListeners()
    }

    private fun setButtonsListeners(){
        btn_home_posts.setOnClickListener{
            onGotToPosts()
        }
    }

    private fun onGotToPosts(){
        val intent = Intent(this, PostsActivity::class.java)
        this.startActivity(intent)
    }
}
