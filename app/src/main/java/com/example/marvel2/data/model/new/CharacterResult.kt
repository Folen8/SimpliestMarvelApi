package com.example.marvel2.data.model.new

import com.example.marvel2.data.Comics
import com.example.marvel2.data.Series
import com.example.marvel2.data.Thumbnail
import com.example.marvel2.data.Url

data class CharacterResult (
    var id: Int,
    var name: String,
    var description: String,
    var thumbnail: Thumbnail,
    var comics: Comics,
    var series: Series,
    var urls: List<Url> = ArrayList(),
)