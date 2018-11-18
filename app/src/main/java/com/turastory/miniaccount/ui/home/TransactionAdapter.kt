package com.turastory.miniaccount.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.turastory.miniaccount.R
import com.turastory.miniaccount.entity.Transaction
import com.turastory.miniaccount.le
import kotlinx.android.synthetic.main.item_transaction.view.*
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(private val view: androidx.recyclerview.widget.RecyclerView) :
    androidx.recyclerview.widget.RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    // List of transactions - always sorted with date in descending order..
    private val transactions = ArrayList<Transaction>()

    fun setTransactions(newTransactions: List<Transaction>) {
        transactions.apply {
            clear()
            addAll(newTransactions)
            sortBy { it.date }
            reverse()
        }

        notifyDataSetChanged()
    }

    fun addTransaction(transaction: Transaction) {
        val pos = findPosition(transaction)

        transactions.add(pos, transaction)
        notifyItemInserted(pos)

        le("Add item at pos $pos")

        view.scrollToPosition(pos)
    }

    private fun findPosition(transaction: Transaction): Int {
        transactions.forEachIndexed { index, item ->
            if (item.date.before(transaction.date))
                return index
        }

        return transactions.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_transaction, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(vh: ViewHolder, i: Int) {
        vh.bind(transactions[i])
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bind(transaction: Transaction) {
            with(itemView) {
                nameText.text = transaction.name
                timeText.text = transaction.date.formatDate()
                setAmountText(transaction.amount)
            }
        }

        private fun Date.formatDate() =
            SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(this)

        private fun View.setAmountText(amount: Int) {
            amountText.text = "$amount"
            when {
                amount < 0 ->
                    amountText.setTextColor(colorOf(R.color.colorRed))
                amount == 0 ->
                    amountText.setTextColor(colorOf(R.color.colorText))
                else ->
                    amountText.setTextColor(colorOf(R.color.colorBlue))
            }
        }

        private fun colorOf(@ColorRes resId: Int): Int =
            ContextCompat.getColor(itemView.context, resId)
    }
}
