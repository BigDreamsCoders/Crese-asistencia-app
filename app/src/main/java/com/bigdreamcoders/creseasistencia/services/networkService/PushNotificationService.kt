package com.bigdreamcoders.creseasistencia.services.networkService

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
        Log.d("TOKEN", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

    }
}
