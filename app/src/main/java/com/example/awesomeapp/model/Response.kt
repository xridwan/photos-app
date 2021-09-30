package com.example.awesomeapp.model

data class Response(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: ArrayList<Photo>
)