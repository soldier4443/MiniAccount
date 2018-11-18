package com.turastory.miniaccount.mapper

import com.turastory.miniaccount.entity.Transaction
import com.turastory.miniaccount.model.TransactionModel

class TransactionMapper : InvertableMapper<Transaction, TransactionModel> {
    override fun convert(from: Transaction): TransactionModel {
        return TransactionModel(
            from.id,
            from.name,
            from.amount,
            from.date,
            from.type
        )
    }

    override fun invert(to: TransactionModel): Transaction {
        return Transaction(
            to.id,
            to.name,
            to.amount,
            to.date,
            to.type
        )
    }
}