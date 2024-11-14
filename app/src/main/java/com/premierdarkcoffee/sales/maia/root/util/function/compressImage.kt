package com.premierdarkcoffee.sales.maia.root.util.function

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream

fun compressImage(uri: Uri, contentResolver: ContentResolver): Uri? {
    try {
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "converted_image.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        val outputStream: OutputStream? = imageUri?.let { contentResolver.openOutputStream(it) }
        val quality = 50
        var byteArray: ByteArray
        do {
            byteArray = ByteArrayOutputStream().use { byteArrayOutputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
                byteArrayOutputStream.toByteArray()
            }
        } while (byteArray.size > 500 * 1024 && quality > 0)
        outputStream?.write(byteArray)
        outputStream?.close()
        inputStream?.close()
        return imageUri
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}
