package com.premierdarkcoffee.sales.maia.root.util.function

import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImageToFirebase(
    contentResolver: ContentResolver,
    uri: Uri,
    imageInfo: (ImageInfo) -> Unit
) {
    FirebaseAuth.getInstance().currentUser?.uid?.let { userId ->
        val remoteImagePath = "fake/stores/images/${userId}/${UUID.randomUUID()}.jpg"
        compressImage(uri, contentResolver)?.let {
            FirebaseStorage.getInstance().reference.child(remoteImagePath).putFile(it)
                .addOnSuccessListener {
                    FirebaseStorage.getInstance().reference.child(remoteImagePath).downloadUrl.addOnSuccessListener { downloadUrl ->
                        imageInfo(ImageInfo(remoteImagePath, downloadUrl.toString()))
                    }.addOnFailureListener { exception ->
                        Log.d(TAG, "AddEditProductView | Failed to get download URL: $exception")
                    }
                }.addOnFailureListener { exception ->
                    Log.d(TAG, "AddEditProductView | Failed uploadImageToFirebase: $exception")
                }
        } ?: Log.d(TAG, "AddEditProductView | Failed to compress image")
    } ?: Log.d(TAG, "AddEditProductView | User ID not found")
}
