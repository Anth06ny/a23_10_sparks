package com.amonteiro.a23_10_sparks

import com.google.gson.Gson
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.Request

object RequestUtils {

    val client = OkHttpClient()
    val gson = Gson()

    suspend fun loadPicture(): Array<PictureData> {
        delay(3000)

        val json = sendGet("https://www.amonteiro.fr/api/pictureList")

        return gson.fromJson(json, Array<PictureData>::class.java)
    }

    //Méthode qui prend en entrée une url, execute la requête
    //Retourne le code HTML/JSON reçu
    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()
        //Execution de la requête
        return client.newCall(request).execute().use { //it:Response
            //use permet de fermer la réponse qu'il y ait ou non une exception
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            //Résultat de la requête
            it.body.string()
        }
    }
}