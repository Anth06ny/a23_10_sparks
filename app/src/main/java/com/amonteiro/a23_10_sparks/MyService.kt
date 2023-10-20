package com.amonteiro.a23_10_sparks

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()
        println("Service onCreate")

        //3 secondes pour lui associer une notification sinon il s'arrete
        startForeground(1, NotificationUtils.getNotification(this, "ForegroundService") )

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        println("Service onStartCommand $startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Service onDestroy")
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}