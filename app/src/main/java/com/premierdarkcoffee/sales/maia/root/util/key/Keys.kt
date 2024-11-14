package com.premierdarkcoffee.sales.maia.root.util.key

import android.content.Context
import com.premierdarkcoffee.sales.maia.root.feature.product.presentation.viewmodel.getApiKey

fun getMaiaKey(context: Context): String {
    return getApiKey(context)
}