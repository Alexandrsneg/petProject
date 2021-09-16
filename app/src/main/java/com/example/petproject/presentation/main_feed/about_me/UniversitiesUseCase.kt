package com.example.petproject.presentation.main_feed.about_me

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import info.esoft.ko.data.model.rest.guber.AdminProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UniversitiesUseCase {
//    private val universitiesService = UniversitiesService.getUniversitiesService()

    var detailInfoFlow: Flow<AdminProfile>? = null
    private val dataBase = Firebase.firestore

    var exceptionFlow: Flow<String>? = null


//    suspend fun getUniversities() {
//        val universities = universitiesService.getUniversities()
//        universityFlow = flow {
//            universities.body()?.forEach {
//                kotlinx.coroutines.delay(1000)
//                Log.d("FLOW emit", it.name ?: "empty")
//                emit(it)
//            }
//        }
//    }


    suspend fun getDetailInfo() {
        dataBase.collection("users").document("adminProfile")
            .get()
            .addOnSuccessListener {
                if (it.exists()){
                    val result = it.toObject(AdminProfile::class.java)
                    detailInfoFlow = flow {
                        emit(result ?: AdminProfile(age = 999, attachments = null))
                        Log.d("FLOW getDetail Success ", result?.firstName ?: "empty")
                    }
                }
            }
            .addOnFailureListener {
                Log.d("FLOW getDetail failure ", it.toString())
                exceptionFlow = flow {
                    emit(it.localizedMessage ?: "")
                }
            }
    }
}