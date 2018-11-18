package com.turastory.miniaccount.model

import java.util.*

data class TransactionModel(
    var id: Long,
    var name: String,
    var amount: Int,
    var date: Date
)
