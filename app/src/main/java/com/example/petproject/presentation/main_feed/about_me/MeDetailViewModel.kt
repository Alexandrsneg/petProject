package com.example.petproject.presentation.main_feed.about_me

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.esoft.ko.data.model.rest.guber.AdminProfile
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MeDetailViewModel : ViewModel() {

    var job: Job? = null
    val detailInfo: StateFlow<AdminProfile>
        get() = _detailInfo.asStateFlow()

    val loadError: StateFlow<String>
        get() = _loadError.asStateFlow()

    val loading: StateFlow<Boolean>
        get() = _loading.asStateFlow()

    private val _detailInfo = MutableStateFlow(AdminProfile(attachments = null))
    private val _loadError = MutableStateFlow("")
    private val _loading = MutableStateFlow(false)

    private val detailInfoUseCase = UniversitiesUseCase()
    private val myExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable.localizedMessage ?: "")
    }

    private fun onError(message: String) {
        _loading.value = false
        _loadError.value = message
    }

    fun getDetailInfo() {
        _loading.value = true
        job = viewModelScope.launch(Dispatchers.Main + myExceptionHandler) {

            detailInfoUseCase.getDetailInfo()
            detailInfoUseCase.detailInfoFlow?.collect {
                Log.d("FLOW collect", it.toString())
                _detailInfo.value = it
                _loading.value = false
                detailInfoUseCase.exceptionFlow?.collect {
                    onError(it)
                }
            }
        }

    }


}