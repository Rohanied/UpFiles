package com.example.upfiles.ui.addfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.upfiles.data.FirebaseRepository

class AddFileViewModel:ViewModel() {

    fun uploadFile(uri: String, isPrivate: Boolean):MutableLiveData<Boolean>{
        return FirebaseRepository().uploadPrivateFile(uri,isPrivate)
    }
}