package com.turastory.miniaccount.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.turastory.miniaccount.R
import com.turastory.miniaccount.goBack
import com.turastory.miniaccount.startFragment
import com.turastory.miniaccount.topFragment
import com.turastory.miniaccount.ui.addtransaction.AddTransactionFragment
import com.turastory.miniaccount.ui.addtransaction.AddTransactionNavigation
import com.turastory.miniaccount.ui.list.TransactionListFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), AddTransactionNavigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        showListFragment()

        addButton.setOnClickListener {
            topFragment()?.let {
                if (it is TransactionListFragment) {
                    showAddFragment()
                } else if (it is AddTransactionFragment) {
                    it.onClickAdd()
                }
            }
        }
    }

    private fun showListFragment() {
        if (fragmentNotFound(TransactionListFragment.TAG)) {
            startFragment(TransactionListFragment(), TransactionListFragment.TAG)
        }
    }

    private fun showAddFragment() {
        if (fragmentNotFound(AddTransactionFragment.TAG)) {
            startFragment(AddTransactionFragment().setup(this), AddTransactionFragment.TAG)
        }
    }

    private fun fragmentNotFound(tag: String) = findFragment(tag) == null

    private fun findFragment(tag: String) = supportFragmentManager.findFragmentByTag(tag)

    override fun saveTransaction(id: Long) {
        goBack()
        updateList(id)
    }

    private fun updateList(id: Long) {
        topFragment()?.let {
            if (it is TransactionListFragment) {
                it.addNewItem(id)
            }
        }
    }
}
