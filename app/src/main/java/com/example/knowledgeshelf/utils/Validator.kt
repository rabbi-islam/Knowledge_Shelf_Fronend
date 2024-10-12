package com.example.knowledgeshelf.utils

import android.net.Uri

object Validator {
    fun validateImage(imageUri: Uri?): String {
        return if (imageUri == null) {
            "Image is required!"
        } else {
            ""
        }
    }
}