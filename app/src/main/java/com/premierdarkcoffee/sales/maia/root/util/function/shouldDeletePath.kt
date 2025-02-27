package com.premierdarkcoffee.sales.maia.root.util.function

fun shouldDeletePath(path: String): Boolean {
    val basePath = "fake/stores"
    return path.startsWith("$basePath/") && path.length > basePath.length
}
