package com.example.marvel2.data

data class Series(
    val available: String,
    val collectionURI: String,
    val items: List<Item>,
    val returned: String
)