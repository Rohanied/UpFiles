package com.example.upfiles.ui.home

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.upfiles.data.FirebaseRepository

class HomeViewModel: ViewModel() {

    fun getAllFile(uid: String): MutableLiveData<ArrayList<String>>{
        return FirebaseRepository().listAllFiles(uid)
    }

    fun getUid(): String{
        return FirebaseRepository().getUserId()
    }
}