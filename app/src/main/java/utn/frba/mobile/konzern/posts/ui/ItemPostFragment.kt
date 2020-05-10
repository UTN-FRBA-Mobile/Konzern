package utn.frba.mobile.konzern.posts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_item_post.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.posts.viewModel.PostsViewModel
import java.time.format.DateTimeFormatter

class ItemPostFragment : Fragment() {

    private lateinit var viewModel: PostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(PostsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.selectedItem.observe(viewLifecycleOwner, Observer<Post> {
            setView(it)
        })
    }

    private fun setView(item: Post){
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        tv_date?.text = item.date.format(formatter)
        tv_summary?.text = item.summary
        if(item.text != null)
            tv_description.text = item.text
    }
}
