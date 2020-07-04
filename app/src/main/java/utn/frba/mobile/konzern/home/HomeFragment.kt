package utn.frba.mobile.konzern.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.home.adapter.HomePostItemAdapter
import utn.frba.mobile.konzern.home.adapter.LoadingItemAdapter
import utn.frba.mobile.konzern.news.NewsActivity
import utn.frba.mobile.konzern.posts.PostActivity
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.news.NewsViewModel
import utn.frba.mobile.konzern.posts.viewModel.PostViewModel
import utn.frba.mobile.konzern.utils.ScaleLayoutManager

class HomeFragment : Fragment(), HomePostItemAdapter.OnHomeItemPostClickListener {
    private val postViewModel: PostViewModel by activityViewModels()
    private val newsViewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPostModuleView()
        setNewsModuleView()
    }

    private fun setPostModuleView(){
        postViewModel.itemList.observe(viewLifecycleOwner, Observer<List<Post>> {
            setPostRecycler(it)
        })

        postViewModel.showProgressIndicator.observe(viewLifecycleOwner, Observer {
            if(it!!){
                vRecyclerViewPostHome.adapter = LoadingItemAdapter()
            }
        })

        postViewModel.initItemList()
    }

    private fun setNewsModuleView(){
        newsViewModel.itemList.observe(viewLifecycleOwner, Observer<List<Post>> {
            setNewsRecycler(it)
        })

        newsViewModel.showProgressIndicator.observe(viewLifecycleOwner, Observer {
            if(it!!){
                vRecyclerViewNewsHome.adapter = LoadingItemAdapter()
            }
        })

        newsViewModel.initItemList()
    }

    override fun onItemClick(id: String) {
        postViewModel.showDetailItem(id)
        findNavController().navigate(R.id.action_HomeFragment_to_ItemPostFragment)
    }

    private fun setRecycler(items: List<Post>, recyclerView: RecyclerView){
        recyclerView.adapter = HomePostItemAdapter(items, this)
        recyclerView.layoutManager = ScaleLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false, 0.15f, 0.85f)

        val snapHelperCardsRecycler = androidx.recyclerview.widget.PagerSnapHelper()
        snapHelperCardsRecycler.attachToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(SnapItemDecorator(requireActivity()))
    }

    private fun setPostRecycler(items: List<Post>){
        setRecycler(items, vRecyclerViewPostHome)

        vButtonPostMain.setOnClickListener{
            val intent = Intent(requireContext(), PostActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun setNewsRecycler(items: List<Post>){
        setRecycler(items, vRecyclerViewNewsHome)

        vButtonNewsMain.setOnClickListener{
            val intent = Intent(requireContext(), NewsActivity::class.java)
            this.startActivity(intent)
        }
    }
}
