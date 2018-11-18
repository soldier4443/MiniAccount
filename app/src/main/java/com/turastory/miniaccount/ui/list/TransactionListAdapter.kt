package com.turastory.miniaccount.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.turastory.miniaccount.R
import com.turastory.miniaccount.colorOf
import com.turastory.miniaccount.entity.Transaction
import com.turastory.miniaccount.entity.Type
import com.turastory.miniaccount.le
import kotlinx.android.synthetic.main.item_transaction.view.*
import java.text.SimpleDateFormat
import java.util.*

class TransactionListAdapter(private val view: RecyclerView) :
    RecyclerView.Adapter<TransactionListAdapter.ViewHolder>() {

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

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(transaction: Transaction) {
            with(itemView) {
                nameText.text = transaction.name
                timeText.text = transaction.date.formatDate()
                setAmountText(transaction)
            }
        }

        private fun View.setAmountText(transaction: Transaction) {
            when {
                transaction.type == Type.INCOME -> {
                    amountText.text = "+${transaction.amount}"
                    amountText.setTextColor(context.colorOf(R.color.colorBlue))
                }
                transaction.type == Type.OUTCOME -> {
                    amountText.text = "-${transaction.amount}"
                    amountText.setTextColor(context.colorOf(R.color.colorRed))
                }
            }
        }

        private fun Date.formatDate() =
            SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(this)
    }
}
