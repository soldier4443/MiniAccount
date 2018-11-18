package com.turastory.miniaccount.ui.addtransaction

import android.os.Bundle
import android.view.View
import com.google.android.material.chip.Chip
import com.turastory.miniaccount.R
import com.turastory.miniaccount.TransactionListUseCase
import com.turastory.miniaccount.entity.Transaction
import com.turastory.miniaccount.entity.Type
import com.turastory.miniaccount.toast
import com.turastory.miniaccount.ui.common.NavFragment
import kotlinx.android.synthetic.main.fragment_add_transaction.*
import org.koin.android.ext.android.inject
import org.koin.ext.isInt
import java.text.SimpleDateFormat
import java.util.*

class AddTransactionFragment : NavFragment<AddTransactionNavigation>() {
    companion object {
        const val TAG = "add_transaction"
    }

    private var date: Date = Date()
    private var type: Type = Type.OUTCOME

    private val useCase: TransactionListUseCase by inject()

    override fun provideLayoutRes(): Int {
        return R.layout.fragment_add_transaction
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateDateText()
        setupDateButtons()

        setupTypeChips()
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

    private fun setupTypeChips() {
        typeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.outcomeTypeChip -> this.type = Type.OUTCOME
                R.id.incomeTypeChip -> this.type = Type.INCOME
                Chip.NO_ID -> {
                    // Prevent no selection
                    if (this.type == Type.OUTCOME) {
                        typeChipGroup.check(R.id.outcomeTypeChip)
                    } else if (this.type == Type.INCOME) {
                        typeChipGroup.check(R.id.incomeTypeChip)
                    }
                }
            }
        }
    }

    fun onClickAdd() {
        saveTransaction()
    }

    private fun saveTransaction() {
        val name = nameEdit.text.trim().toString()
        if (name.isEmpty()) {
            toast("이름을 입력해주세요.")
            return
        }

        val amountText = amountEdit.text.trim().toString()
        if (amountText.isEmpty()) {
            toast("값을 입력해주세요.")
            return
        } else if (!amountText.isInt()) {
            toast("올바른 값을 입력해주세요.")
            return
        }

        val date = date

        useCase.addTransaction(
            Transaction(
                name = name,
                amount = amountText.toInt(),
                date = date,
                type = type
            )
        ).let { id ->
            navigation.saveTransaction(id)
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
}