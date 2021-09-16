package com.example.petproject.presentation.main_feed.about_me

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import info.esoft.ko.data.model.rest.guber.AdminProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class UniversitiesUseCase {

    val detailInfoFlow: StateFlow<AdminProfile>
        get() = _detailInfoFlow

    private var _detailInfoFlow = MutableStateFlow(AdminProfile())

    private val dataBase = Firebase.firestore
    var exceptionFlow: Flow<String>? = null

    suspend fun getDetailInfo() {
        dataBase.collection("users").document("adminProfile")
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    it.toObject(AdminProfile::class.java)?.let { profile ->
                        _detailInfoFlow.value = profile
                    }
                }
            }
            .addOnFailureListener {
                exceptionFlow = flow {
                    emit(it.localizedMessage ?: "")
                }
            }
    }

}