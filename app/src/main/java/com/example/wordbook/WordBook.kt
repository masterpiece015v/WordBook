package com.example.wordbook

data class WordBook(
    val _id:Int,
    val category:String,
    val japanese:String,
    val english:String,
    var remembered:Int,
    var count:Int
)
