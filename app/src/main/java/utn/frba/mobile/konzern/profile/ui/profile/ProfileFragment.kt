package utn.frba.mobile.konzern.profile.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.contact.repository.ContactRepository
import utn.frba.mobile.konzern.posts.viewModel.ControlledException
import utn.frba.mobile.konzern.posts.viewModel.Failure
import utn.frba.mobile.konzern.posts.viewModel.SingleLiveEvent
import utn.frba.mobile.konzern.profile.Profile
import utn.frba.mobile.konzern.profile.ProfileRepository

class ProfileFragment : Fragment() {

    private val repository = ProfileRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getProfileFromRepository()
    }

    private fun getProfileFromRepository() {
        repository.getProfile(object : ProfileRepository.ProfileRepositoryInterface {
            override fun onComplete(profile: Profile?) {
                vProfileUsername.text = profile?.email
                vProfilePhone.text = profile?.phone
                vProfileInfo.text = profile?.info
            }
        })
    }
}
