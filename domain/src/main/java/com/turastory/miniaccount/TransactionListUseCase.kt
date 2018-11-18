package com.turastory.miniaccount

import com.turastory.miniaccount.entity.Transaction

class TransactionListUseCase(private val localRepository: Repository) {
    fun loadTransactions(): List<Transaction> {
        return localRepository.loadTransactions()
    }

    fun getTransaction(id: Long): Transaction? {
        return localRepository.getTransaction(id)
    }

    fun addTransaction(transaction: Transaction): Long {
        return localRepository.addTransaction(transaction)
    }
}