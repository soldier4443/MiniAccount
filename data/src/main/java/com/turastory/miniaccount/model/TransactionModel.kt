package com.turastory.miniaccount.model

import com.turastory.miniaccount.entity.Type
import java.util.*

data class TransactionModel(
    var id: Long,
    var name: String,
    var amount: Int,
    var date: Date,
    var type: Type
)
