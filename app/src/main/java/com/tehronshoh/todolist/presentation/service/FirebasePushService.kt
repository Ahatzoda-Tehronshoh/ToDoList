package com.tehronshoh.todolist.presentation.service


import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tehronshoh.todolist.presentation.util.sendLocalNotification

class FirebasePushService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        var notificationTitle = ""
        var notificationBody = ""

        // Check if message contains a notification payload
        remoteMessage.notification?.let {
            notificationTitle = it.title.toString()
            notificationBody = it.body.toString()
        }

        // If you want to fire a local notification (that notification on the top of the phone screen)
        // you should fire it from here
        sendLocalNotification(notificationTitle, notificationBody)
    }
}

/*

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                          String  token = Objects.requireNonNull(task.getResult()).getToken();

                        }

                    }
                });

 */