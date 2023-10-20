package com.amonteiro.a23_10_sparks

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReceiver : BroadcastReceiver() {

    //Ecouter des événement exterieur déclarés dans l'AndroidManifest
    override fun onReceive(context: Context, intent: Intent) {
        println(intent.action)

        //Lance un service en arrière plan
        context.startForegroundService(Intent(context, MyService::class.java))

    }
}