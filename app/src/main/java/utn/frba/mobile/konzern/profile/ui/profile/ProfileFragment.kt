package utn.frba.mobile.konzern.profile.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.contact.repository.ContactRepository
import utn.frba.mobile.konzern.profile.Profile
import utn.frba.mobile.konzern.profile.ProfileRepository

class ProfileFragment : Fragment() {

    private val repository = ProfileRepository()
    private var profileView: ProfileFragmentView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ProfileFragmentView) {
            profileView = context
        } else {
            throw RuntimeException("$context must be ProfileFragmentView")
        }
    }

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
        val lastSignIn = GoogleSignIn.getLastSignedInAccount(requireContext())
        repository.getProfile(object : ProfileRepository.ProfileRepositoryInterface {
            override fun onComplete(profile: Profile?) {
                vProfileUsername.text = profile?.email
                vProfilePhone.text = profile?.phone
                vProfileInfo.text = profile?.info
                Glide.with(requireContext())
                    .load(lastSignIn?.photoUrl)
                    .error(R.drawable.profile_placeholder)
                    .into(vProfilePicture)

                profileView?.hideProgress()
            }
        })
    }

    companion object {
        fun newInstance() =
            ProfileFragment()
    }

    interface ProfileFragmentView {

        fun showProgress()

        fun hideProgress()
    }
}
