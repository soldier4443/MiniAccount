package com.turastory.miniaccount.ui.addtransaction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.turastory.miniaccount.R
import com.turastory.miniaccount.TransactionListUseCase
import com.turastory.miniaccount.entity.Transaction
import com.turastory.miniaccount.toast
import kotlinx.android.synthetic.main.activity_add_transaction.*
import org.koin.android.ext.android.inject
import org.koin.ext.isInt
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 1
        const val INTENT_NEW_TRANSACTION_ID = "new_transaction_id"
    }

    private var date: Date = Date()

    private val useCase: TransactionListUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        updateDateText()
        setupDateButtons()

        setupCompleteButton()
    }

    private fun updateDateText() {
        dateText.text = date.format("MM/dd")
    }

    private fun setupDateButtons() {
        dateMinusButton.setOnClickListener {
            date -= 1
            updateDateText()
        }

        datePlusButton.setOnClickListener {
            date += 1
            updateDateText()
        }
    }

    private fun setupCompleteButton() {
        completeButton.setOnClickListener {
            val name = nameEdit.text.trim().toString()
            if (name.isEmpty()) {
                toast("이름을 입력해주세요.")
                return@setOnClickListener
            }

            val amountText = amountEdit.text.trim().toString()
            if (amountText.isEmpty()) {
                toast("값을 입력해주세요.")
                return@setOnClickListener
            } else if (!amountText.isInt()) {
                toast("올바른 값을 입력해주세요.")
                return@setOnClickListener
            }

            val date = date

            val id = useCase.addTransaction(
                Transaction(
                    name = name,
                    amount = amountText.toInt(),
                    date = date
                )
            )

            setResult(RESULT_OK, Intent().apply {
                putExtra(INTENT_NEW_TRANSACTION_ID, id)
            })
            finish()
        }
    }
}

private operator fun Date.plusAssign(day: Int) {
    this.time += day.toMillis()
}

private operator fun Date.minusAssign(day: Int) {
    this.time -= day.toMillis()
}

private fun Date.format(format: String): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}

private fun Int.toMillis(): Long {
    return (this * 86400 * 1000).toLong()
}
