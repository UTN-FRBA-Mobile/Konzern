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
import kotlinx.android.synthetic.main.fragment_new_item_post.*

import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.posts.viewModel.PostsViewModel

class NewItemPostFragment : Fragment() {
    private lateinit var viewModel: PostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_item_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(PostsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        btn_save.setOnClickListener {
            this.onBtnSave()
        }

        btn_cancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onBtnSave(){
        validateFields()
        if(layout_summary.error == null && layout_description.error == null) {
            viewModel.addItem(et_summary.text.toString(), et_description.text.toString())
            findNavController().navigateUp()
        }
    }

    private fun validateFields() {
        layout_summary.error = null
        layout_description.error = null

        if(et_summary.text.isNullOrEmpty())
            layout_summary.error = layout_summary.helperText

        if(et_description.text.isNullOrEmpty())
            layout_description.error = layout_description.helperText
    }
}
