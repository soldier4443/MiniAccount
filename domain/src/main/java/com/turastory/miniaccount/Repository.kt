package com.turastory.miniaccount

import com.turastory.miniaccount.entity.Transaction

interface Repository {
    fun loadTransactions(): List<Transaction>
    fun addTransaction(transaction: Transaction): Long
    fun getTransaction(id: Long): Transaction?
}