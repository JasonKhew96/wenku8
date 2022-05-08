package com.jasonkhew96.wenku8.model

data class Novel(
    val aid: Int, val title: String,
    val author :String,
    val classType: String,
    val lastUpdate: String,
    val charCount: String,
    val status: String,
    val tags: String,
    val description: String,
)
