package com.amonteiro.a23_10_sparks

import android.Manifest
import android.app.Notification
import android.app.Notification.DEFAULT_ALL
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH


object NotificationUtils {

    const val CHANNEL_ID = "MonSuperChannel"
    const val CHANNEL_NAME = "Commandes"

    fun getNotification(context: Context, message: String): Notification {
        //Initialisation du chanel
        initChannel(context)
        //Ce qui se passera quand on cliquera sur la notif
        val intent = Intent(context, MainActivity::class.java)
        var flags = PendingIntent.FLAG_ONE_SHOT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            //Notification modifiable
            flags = flags or PendingIntent.FLAG_MUTABLE
        }
        val pi = PendingIntent.getActivity(context, 0, intent, flags)

        //La notification
        val nb = NotificationCompat.Builder(context, CHANNEL_ID)
        nb.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Le Titre")
            .setContentText(message)
            .setContentIntent(pi) //le clic dessus
            .setPriority(IMPORTANCE_HIGH)
            .setDefaults(DEFAULT_ALL) //Son + afficher la notification

        return nb.build()
    }

    //Je reporte le controle de permission au dessus
//Envoyer une notification immediate
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun createInstantNotification(context: Context, message: String) {
        //Initialisation du chanel
        initChannel(context)
        //Ce qui se passera quand on cliquera sur la notif
        val intent = Intent(context, MainActivity::class.java)
        var flags = PendingIntent.FLAG_ONE_SHOT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            //Notification modifiable
            flags = flags or PendingIntent.FLAG_MUTABLE
        }
        val pi = PendingIntent.getActivity(context, 0, intent, flags)

        //La notification
        val nb = NotificationCompat.Builder(context, CHANNEL_ID)
        nb.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Le Titre")
            .setContentText(message)
            .setContentIntent(pi) //le clic dessus
            .setPriority(IMPORTANCE_HIGH)
            .setDefaults(DEFAULT_ALL) //Son + afficher la notification

        //Envoyer la notification
        val ncm = NotificationManagerCompat.from(context)
        ncm.notify(29, nb.build())
    }

    //Création d'un channel
    private fun initChannel(context: Context) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Réglage du channel
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        channel.description = "Commandes" // Description
        channel.enableLights(true) // Lumière
        channel.enableVibration(true) // Vibration
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        // Création du channel
        nm.createNotificationChannel(channel)
    }
}