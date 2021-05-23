package com.example.petproject.presentation.main_feed.popups
import com.example.petproject.data.model.rest.Attachment


data class OnboardingModel(
        var id: Long,
        var name: String,
        var title: String? = null,
        var content: String? = null,
        var footer: String? = null,
        var buttonText: String? = null,
        var image: Attachment? = null
)

