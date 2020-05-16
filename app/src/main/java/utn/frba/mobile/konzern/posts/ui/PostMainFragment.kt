package utn.frba.mobile.konzern.posts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_post_main.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.adapter.ItemPostAdapter
import utn.frba.mobile.konzern.posts.adapter.OnItemPostClickListener
import utn.frba.mobile.konzern.posts.model.Post

class PostMainFragment : PostBaseFragment(), OnItemPostClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.itemList.observe(viewLifecycleOwner, Observer<List<Post>> {
            vRecyclerViewPostMain.adapter = ItemPostAdapter(it, this)
        })

        vButtonAddPostMain.setOnClickListener {
            viewModel.selectItem(null)
            findNavController().navigate(R.id.action_MainPostsFragment_to_NewItemPostFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadItemList()
    }

    override fun onClick(id: Int) {
        viewModel.selectItem(id)
        findNavController().navigate(R.id.action_MainPostsFragment_to_ItemPostFragment)
    }


}
