package com.nexus.ai.floating

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.nexus.ai.ui.components.AiOrb

class NexusFloatingService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        val channelId = "NEXUS_OVERLAY_SERVICE"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Overlay Engine", NotificationManager.IMPORTANCE_MIN)
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("NEXUS Ambient Layer Active")
            .setContentText("Omnipresent system assistant overlay executing in foreground mode.")
            .setSmallIcon(android.R.drawable.presence_online)
            .build()

        startForeground(9912, notification)

        FloatingWindowManager.injectSystemOverlay(this) {
            // Self contained floating UI composition roots
            AiOrb(modifier = androidx.compose.ui.Modifier.run { androidx.compose.ui.Modifier.size(100.dp) })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        FloatingWindowManager.removeSystemOverlay(this)
    }
}
