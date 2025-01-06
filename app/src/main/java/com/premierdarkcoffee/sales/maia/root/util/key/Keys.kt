package com.premierdarkcoffee.sales.maia.root.util.key

import android.content.Context
import com.premierdarkcoffee.sales.maia.root.util.helper.ApiKeySecurePreferencesHelper

fun getMaiaKey(context: Context): String? {
    return ApiKeySecurePreferencesHelper.getApiKey(context)
}