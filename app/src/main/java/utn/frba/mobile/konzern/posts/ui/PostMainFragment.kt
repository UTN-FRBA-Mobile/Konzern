package utn.frba.mobile.konzern.posts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_post_main.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.adapter.ItemPostAdapter
import utn.frba.mobile.konzern.posts.adapter.OnItemPostClickListener
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.posts.viewModel.BasePostViewModel
import utn.frba.mobile.konzern.posts.viewModel.PostViewModel

open class PostMainFragment : PostBaseFragment(), OnItemPostClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_main, container, false)
    }

    override fun getCustomViewModel(): BasePostViewModel {
        val vm: PostViewModel by activityViewModels()
        return vm
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.itemList.observe(viewLifecycleOwner, Observer<List<Post>> {
            vRecyclerViewPostMain.adapter = ItemPostAdapter(it, this)
        })

        viewModel.initItemList()

        if(!viewModel.canEdit) {
            vButtonAddPostMain.visibility = GONE
        } else{
            setAddItemButton();
        }
    }

    override fun onItemClick(id: String) {
        viewModel.showDetailItem(id)
        findNavController().navigate(getItemNavId())
    }

    override fun onEditClick(id: String) {
        viewModel.editItem(id)
        findNavController().navigate(getEditItemNavId())
    }

    override fun onDeleteClick(id: String) {
        viewModel.deleteItem(id)
    }

    protected open fun getItemNavId(): Int{
        return R.id.action_MainPostsFragment_to_ItemPostFragment
    }

    private fun getEditItemNavId(): Int{
        return R.id.action_MainPostsFragment_to_NewItemPostFragment
    }

    private fun getNewItemNavId(): Int{
        return R.id.action_MainPostsFragment_to_NewItemPostFragment
    }

    private fun setAddItemButton(){
        vButtonAddPostMain.setOnClickListener {
            vTextButtonAddPost.apply { isVisible = !isVisible }
            vTextButtonAddClaim.apply { isVisible = !isVisible }
        }

        vTextButtonAddClaim.setOnClickListener {
            viewModel.editItem(null)
            viewModel.isClaim = true
            findNavController().navigate(getNewItemNavId())
        }

        vTextButtonAddPost.setOnClickListener {
            viewModel.editItem(null)
            viewModel.isClaim = false
            findNavController().navigate(getNewItemNavId())
        }
    }
}
