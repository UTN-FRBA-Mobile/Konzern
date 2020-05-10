package utn.frba.mobile.konzern.posts.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main_posts.*

import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.adapter.ItemPostAdapter
import utn.frba.mobile.konzern.posts.adapter.OnItemPostClickListener
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.posts.viewModel.PostsViewModel

class MainPostsFragment : Fragment(), OnItemPostClickListener {
    private lateinit var viewModel: PostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_posts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = activity?.run { ViewModelProvider(this).get(PostsViewModel::class.java) } ?: throw Exception("Invalid Activity")
        viewModel.loadItemList()

        viewModel.itemList.observe(viewLifecycleOwner, Observer<List<Post>> {
            recycler_posts.adapter = ItemPostAdapter(it, this)
        })
    }

    override fun onClick(position: Int) {
        viewModel.selectItem(position)
        findNavController().navigate(R.id.action_MainPostsFragment_to_ItemPostFragment)
    }
}
