package utn.frba.mobile.konzern.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.reservations_main_fragment.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.home.adapter.HomePostItemAdapter
import utn.frba.mobile.konzern.home.adapter.LoadingItemAdapter
import utn.frba.mobile.konzern.news.NewsActivity
import utn.frba.mobile.konzern.news.NewsViewModel
import utn.frba.mobile.konzern.posts.BasePostActivity
import utn.frba.mobile.konzern.posts.PostActivity
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.posts.viewModel.PostViewModel
import utn.frba.mobile.konzern.profile.ProfileActivity
import utn.frba.mobile.konzern.reservations.ReservationViewModel
import utn.frba.mobile.konzern.reservations.ReservationsActivity
import utn.frba.mobile.konzern.reservations.adapter.ReservationAdapter
import utn.frba.mobile.konzern.reservations.model.Reservation
import utn.frba.mobile.konzern.utils.ScaleLayoutManager
import java.util.*

class HomeFragment : Fragment(), HomePostItemAdapter.OnHomeItemPostClickListener {
    private val postViewModel: PostViewModel by activityViewModels()
    private val newsViewModel: NewsViewModel by activityViewModels()
    private val reservationsViewModel: ReservationViewModel by activityViewModels()

    private val RECYCLER_POSTS_TAG = "posts"
    private val RECYCLER_NEWS_TAG = "news"
    private val RECYCLER_RESERVATIONS_TAG = "reservations"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPostModuleView()
        setNewsModuleView()
        setReservationsModuleView()
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

        postViewModel.initItemList(true)
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

        newsViewModel.initItemList(true)
    }

    private fun setReservationsModuleView(){
       reservationsViewModel.initAmenities()
       setReservationRecycler()

        reservationsViewModel.showProgressIndicator.observe(viewLifecycleOwner, Observer {
            if(it!!){
                vRecyclerViewReservationsHome.adapter = LoadingItemAdapter()
            }
        })

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var date = "$day/$month/$year"
        reservationsViewModel.initDayReservations(date)
    }

    private fun setRecycler(items: List<Post>, recyclerView: RecyclerView, tag: String){
        recyclerView.adapter = HomePostItemAdapter(items, this, tag)
        recyclerView.layoutManager = ScaleLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false, 0.15f, 0.85f)

        val snapHelperCardsRecycler = androidx.recyclerview.widget.PagerSnapHelper()
        snapHelperCardsRecycler.attachToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(SnapItemDecorator(requireActivity()))
    }

    private fun setPostRecycler(items: List<Post>){
        setRecycler(items, vRecyclerViewPostHome, RECYCLER_POSTS_TAG)

        vButtonPostMain.setOnClickListener{
            val intent = Intent(requireContext(), PostActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun setNewsRecycler(items: List<Post>){
        setRecycler(items, vRecyclerViewNewsHome, RECYCLER_NEWS_TAG)

        vButtonNewsMain.setOnClickListener{
            val intent = Intent(requireContext(), NewsActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun setReservationRecycler(){
        reservationsViewModel.dayReservations.observe(viewLifecycleOwner, Observer<List<Reservation>> {
            vRecyclerViewReservationsHome.adapter = ReservationAdapter(it, reservationsViewModel)
        })

        vButtonReservationMain.setOnClickListener{
            val intent = Intent(requireContext(), ReservationsActivity::class.java)
            this.startActivity(intent)
        }
    }


    override fun onItemClick(id: String, tag: String) {
        if(tag == RECYCLER_POSTS_TAG) {
            val intent = Intent(requireContext(), PostActivity::class.java)
            intent.putExtra(BasePostActivity.NAV_POST_ITEM_ID, id)
            startActivity(intent)
        } else if(tag == RECYCLER_NEWS_TAG){
            val intent = Intent(requireContext(), NewsActivity::class.java)
            intent.putExtra(BasePostActivity.NAV_POST_ITEM_ID, id)
            startActivity(intent)
        }
    }

}
