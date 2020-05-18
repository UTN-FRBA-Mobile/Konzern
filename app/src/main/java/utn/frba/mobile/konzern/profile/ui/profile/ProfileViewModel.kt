package utn.frba.mobile.konzern.profile.ui.profile

import androidx.lifecycle.ViewModel
import utn.frba.mobile.konzern.profile.Profile

class ProfileViewModel : ViewModel() {

    fun getProfile(): Profile{
        return Profile("lucascharlab", "11 5705-8939", "Vivo en el 2ºB. Aguante boca")
    }
}
