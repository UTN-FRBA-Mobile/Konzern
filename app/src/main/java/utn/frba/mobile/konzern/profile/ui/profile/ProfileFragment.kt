package utn.frba.mobile.konzern.profile.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_profile.*
import utn.frba.mobile.konzern.R

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity?.run { ViewModelProvider(this).get(ProfileViewModel::class.java) } ?: throw Exception("Invalid Activity")
        viewModel.getProfile().run {
            vProfileUsername.text = username
            vProfilePhone.text = phone
            vProfileInfo.text = info
        }
    }
}
