package com.itsz.quarkus.model

data class Extension(
    val id:String,
    val name:String,
    val shortName:String,
    val keywords: List<String>
)