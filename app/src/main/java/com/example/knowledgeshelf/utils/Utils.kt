package com.example.knowledgeshelf.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

object Utils {
    fun uriToPngMultipart(uri: Uri, context: Context, paramName: String = "file"): MultipartBody.Part? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val tempFile = File(context.cacheDir, "tempFile.png")
        FileOutputStream(tempFile).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        val requestFile = RequestBody.create("image/png".toMediaType(), tempFile)
        return MultipartBody.Part.createFormData(paramName, tempFile.name, requestFile)
    }
}