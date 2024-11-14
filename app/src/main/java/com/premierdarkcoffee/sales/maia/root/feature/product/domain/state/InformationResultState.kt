package com.premierdarkcoffee.sales.maia.root.feature.product.domain.state

import android.net.Uri

data class InformationResultState(val id: String,
                                  val title: String,
                                  val subtitle: String,
                                  val description: String,
                                  val image: Uri? = null,
                                  val path: String,
                                  val belongs: Boolean,
                                  val place: Int,
                                  val isCreated: Boolean = false,
                                  val isDeleted: Boolean = false) {

    /*suspend fun toInformation(): Information {
        val newImageUrl = getUrlForNewMainPhoto()
        Log.d(TAG,
              "InformationResultState | toInformation: Old path($path), isCreated($isCreated), isDeleted($isDeleted), and old belongs($belongs)$")
        Log.d(TAG,
              "InformationResultState | toInformation: newImagePath($newImageUrl), isCreated($isCreated), isDeleted($isDeleted), and old belongs($belongs)$")
        val information = Information(id, title, subtitle, description, Image(newImageUrl, true), place)
        Log.d(TAG,
              "InformationResultState | toInformation: path ${information.image.path}, belongs: ${information.image.belongs}")
        return information
    }*/

    /*fun toInformation(path: String, belongs: Boolean): Information {
        Log.d(TAG,
              "InformationResultState | Error: for new path($path), isCreated($isCreated), isDeleted($isDeleted), and new belongs($belongs)$")
        return Information(id, title, subtitle, description, Image(path, belongs), place)
    }*/

    /*private suspend fun getUrlForNewMainPhoto(): String {
        Log.d(TAG, "InformationResultState | getUrlForNewMainPhoto: Path(${path}), Belongs(${belongs})")
        return if (!belongs) {
            downloadImageDataFromFirebaseByReference(path)
        } else {
            path
        }
    }*/
}