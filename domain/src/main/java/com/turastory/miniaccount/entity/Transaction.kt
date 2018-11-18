package com.turastory.miniaccount.entity

import java.util.*

data class Transaction(
    var id: Long = 0,
    val name: String,
    val amount: Int,
    val date: Date,
    val type: Type
)