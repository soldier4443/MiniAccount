package com.turastory.miniaccount.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.turastory.miniaccount.R
import com.turastory.miniaccount.TransactionListUseCase
import com.turastory.miniaccount.toast
import com.turastory.miniaccount.ui.addtransaction.AddTransactionActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    private val transactionAdapter by lazy { TransactionAdapter(recyclerView) }
    private val useCase: TransactionListUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView.apply {
            adapter = transactionAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@HomeActivity)
            setHasFixedSize(true)
        }

        addButton.setOnClickListener {
            startActivityForResult(
                Intent(this, AddTransactionActivity::class.java),
                AddTransactionActivity.REQUEST_CODE
            )
        }

        loadItems()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AddTransactionActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                toast("Add transaction success!")
                val newTransactionId = data
                    ?.getLongExtra(AddTransactionActivity.INTENT_NEW_TRANSACTION_ID, -1) ?: -1

                if (newTransactionId != -1L) {
                    useCase.getTransaction(newTransactionId)?.let {
                        transactionAdapter.addTransaction(it)
                    }
                }
            } else {
                toast("Cancel adding transaction.!")
            }
        }
    }

    private fun loadItems() {
        transactionAdapter.setTransactions(useCase.loadTransactions())
    }
}
