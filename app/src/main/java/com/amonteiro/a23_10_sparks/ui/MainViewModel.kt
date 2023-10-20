package com.amonteiro.a23_10_sparks.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amonteiro.a23_10_sparks.PictureData
import com.amonteiro.a23_10_sparks.RequestUtils
import com.amonteiro.a23_10_sparks.pictureList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var _myList = mutableStateListOf<PictureData>()
    val myList  : List<PictureData> = _myList

    var searchText by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf("")
        private set

    var runInProgress by mutableStateOf(false)
        private set


    fun uploadSearchText(newText : String){
        searchText = newText
    }

    fun loadData() {//Simulation de chargement de donnée
        _myList.clear()
        errorMessage = ""
        runInProgress = true

        viewModelScope.launch (Dispatchers.Default){
            try {
                _myList.addAll(RequestUtils.loadPicture())
            }
            catch(e:Exception){
                e.printStackTrace()
                errorMessage = "Une erreure est survenue : ${e.message}"
            }
            runInProgress = false
        }
    }
}