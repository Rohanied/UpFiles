package com.example.upfiles.data

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.FileInputStream

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun getUserId():String {
        return auth.uid.toString()
    }

    fun uploadPrivateFile(selectedFile: String, isPrivate: Boolean):MutableLiveData<Boolean>{

        val index = selectedFile.lastIndexOf("/")
        val fileName = selectedFile.substring(index+1)

        val stream = FileInputStream(File(selectedFile.toString()))

        val res = MutableLiveData<Boolean>()

        val child :StorageReference = if(isPrivate)
            storage.reference.child("Private/${auth.uid.toString()}/$fileName")
        else
            storage.reference.child("Public/$fileName")

        child.putStream(stream).addOnSuccessListener {
            Log.d("Firebase", "Completed")
            res.value = true
        }.addOnFailureListener{
            Log.d("Firebase", "Error $it")
            res.value = false
        }
        return res

    }

    fun listAllFiles(uid:String):MutableLiveData<ArrayList<String>>{

        val res = MutableLiveData<ArrayList<String>>()
        val fileNames = ArrayList<String>()
        val listRef : StorageReference = if(uid=="public")
            storage.reference.child("Public")
        else
            storage.reference.child("Private/$uid")

        listRef.listAll()
            .addOnSuccessListener {
                for(i in it.items){
                    fileNames.add(i.name)
                }
                Log.d("Firebase", "Files $fileNames")
                res.value = fileNames

            }
            .addOnFailureListener {
                Log.d("Firebase", "Error $it")
                res.value = fileNames
            }

        return  res
    }
}