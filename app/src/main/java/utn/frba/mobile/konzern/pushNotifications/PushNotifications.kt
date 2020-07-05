package utn.frba.mobile.konzern.pushNotifications

import com.google.firebase.messaging.FirebaseMessagingService

class PushNotifications : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        //TODO: Verificar si es necesario hacer override de este método
    }
}