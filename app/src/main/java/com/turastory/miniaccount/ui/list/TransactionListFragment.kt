package com.turastory.miniaccount.ui.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.turastory.miniaccount.R
import com.turastory.miniaccount.TransactionListUseCase
import com.turastory.miniaccount.le
import com.turastory.miniaccount.ui.common.BasicFragment
import kotlinx.android.synthetic.main.fragment_transaction_list.*
import org.koin.android.ext.android.inject

class TransactionListFragment : BasicFragment() {

    companion object {
        const val TAG = "transaction_list"
    }

    private val transactionAdapter by lazy {
        TransactionListAdapter(
            recyclerView
        )
    }

    private val useCase: TransactionListUseCase by inject()

    override fun provideLayoutRes(): Int =
        R.layout.fragment_transaction_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        le("List fragment is showing")

        recyclerView.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        loadItems()
    }

    private fun loadItems() {
        transactionAdapter.setTransactions(useCase.loadTransactions())
    }

    fun addNewItem(id: Long) {
        useCase.getTransaction(id)?.let { transactionAdapter.addTransaction(it) }
    }
}