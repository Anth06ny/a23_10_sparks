package com.amonteiro.a23_10_sparks

const val LONG_TEXT = """Le Lorem Ipsum est simplement
    du faux texte employé dans la composition
    et la mise en page avant impression.
    Le Lorem Ipsum est le faux texte standard
    de l'imprimerie depuis les années 1500"""
data class PictureData(val url: String, val text: String, val longText: String)

//jeu de donnée
val pictureList = arrayListOf(PictureData("https://picsum.photos/200", "ABCD", LONG_TEXT),
    PictureData("https://picsum.photos/201", "BCDE", LONG_TEXT),
    PictureData("https://picsum.photos/202", "CDEF", LONG_TEXT),
    PictureData("https://picsum.photos/203", "EFGH", LONG_TEXT)
)