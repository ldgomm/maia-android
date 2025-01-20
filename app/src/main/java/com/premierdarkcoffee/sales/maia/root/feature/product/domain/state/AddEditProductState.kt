package com.premierdarkcoffee.sales.maia.root.feature.product.domain.state

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Category
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.CreditCard
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Image
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Information
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Offer
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Price
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Specifications
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Warranty
import com.premierdarkcoffee.sales.maia.root.util.function.ImageInfo
import com.premierdarkcoffee.sales.maia.root.util.function.downloadImageDataFromFirebaseByReference

data class AddEditProductState(val id: String = "",
                               val name: String = "",
                               val label: String = "",
                               val owner: String = "",
                               val year: String = "2024",
                               val model: String = "",
                               val description: String = "",
                               val category: Category = Category(group = "", domain = "", subclass = ""),
                               val price: Price = Price(amount = 0.0,
                                                        offer = Offer(isActive = true, discount = 10),
                                                        creditCard = CreditCard(withoutInterest = 0,
                                                                                withInterest = 0,
                                                                                freeMonths = 0)),
                               val stock: Int = 0,
                               var image: Image = Image(path = "", url = "", belongs = false),
                               val origin: String = "",
                               val date: Long = System.currentTimeMillis(),
                               val overview: List<Information> = emptyList(),
                               val keywords: MutableList<String> = mutableListOf(),
                               val specifications: Specifications? = null,
                               val warranty: Warranty? = null,
                               val legal: String? = null,
                               val warning: String? = null,
                               val storeId: String? = null) {

    suspend fun toProduct(image: Image): Product {
        this.image = image
        try {
            Log.d(TAG, "Tracking | AddEditProductState | toProduct: Path(${image.path}), Belongs(${image.belongs})")
            val imageInfo = getUrlForNewMainPhoto()
            return Product(id = id,
                           name = name,
                           label = label,
                           owner = owner,
                           year = year,
                           model = model,
                           description = description,
                           category = category,
                           price = price,
                           stock = stock,
                           image = Image(imageInfo!!.path, url = imageInfo.url, belongs = true),
                           origin = origin,
                           date = System.currentTimeMillis(),
                           overview = overview,
                           keywords = keywords,
                           specifications = specifications,
                           warranty = warranty,
                           legal = legal,
                           warning = warning,
                           storeId = FirebaseAuth.getInstance().uid.toString())
        } catch (e: Exception) {
            Log.e(TAG, "Tracking | Error in toProduct: ${e.message}")
            throw e
        }
    }

    private suspend fun getUrlForNewMainPhoto(): ImageInfo? {
        Log.d(TAG, "Tracking | AddEditProductState | getUrlForNewMainPhoto: Path(${image.path}), Belongs(${image.belongs})")
        return if (!image.belongs) {
            image.path?.let { path ->
                downloadImageDataFromFirebaseByReference(path)
            }
        } else {
            image.path?.let { ImageInfo(path = it, url = image.url) }
        }
    }
}
