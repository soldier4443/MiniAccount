package com.turastory.miniaccount

import com.turastory.miniaccount.entity.Transaction
import com.turastory.miniaccount.mapper.TransactionMapper
import com.turastory.miniaccount.model.TransactionModel

class LocalRepository(private val transactionMapper: TransactionMapper) : Repository {

    companion object {
        private const val count = 5
        private var idRef = 0L
    }

    // Mocking local data source
    private val transactions = ArrayList<TransactionModel>()

    init {
        (1..count)
            .map { provideRandomTransaction() }
            .forEach { addTransaction(it) }
    }

    override fun loadTransactions(): List<Transaction> {
        return transactions.map(transactionMapper::invert)
    }

    override fun addTransaction(transaction: Transaction): Long {
        transaction.id = idRef++
        transactions.add(transactionMapper.convert(transaction))

        return transaction.id
    }

    override fun getTransaction(id: Long): Transaction? {
        return transactions.find { it.id == id }?.let { transactionMapper.invert(it) }
    }
}