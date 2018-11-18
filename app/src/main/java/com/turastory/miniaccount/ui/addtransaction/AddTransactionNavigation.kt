package com.turastory.miniaccount.ui.addtransaction

import com.turastory.miniaccount.ui.common.Navigation

interface AddTransactionNavigation : Navigation {
    fun saveTransaction(id: Long)
}