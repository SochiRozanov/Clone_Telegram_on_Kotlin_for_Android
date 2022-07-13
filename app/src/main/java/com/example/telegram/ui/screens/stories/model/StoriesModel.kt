package com.example.telegram.ui.screens.stories.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoriesModel(
    val title: String,
    val description: String,
    val imagePath: String,
    val previewImage: String,
    var video: String?,
    val mediaType: MediaType
): Parcelable

@Parcelize
enum class MediaType : Parcelable{
    VIDEO,
    IMAGE
}