package com.turastory.miniaccount

import com.turastory.miniaccount.entity.Transaction
import com.turastory.miniaccount.entity.Type
import java.util.*

private val words = arrayOf(
    "laugh", "representative", "seat", "sense", "marble", "hydrant", "bath",
    "eggs", "tramp", "spade", "sand", "cook", "turn", "cats", "camera", "death", "value", "zoo",
    "prose", "education", "field", "pies", "cemetery", "weather", "bushes", "sweater", "crown",
    "liquid", "cup", "morning", "mice", "board", "event", "apparatus", "badge", "airplane", "boat",
    "scent", "things", "hook", "steel", "play", "stem", "smell", "route", "bulb", "cows", "harbor",
    "impulse", "tomatoes"
)

fun provideRandomTransaction(): Transaction {
    return Transaction(
        name = words.randomItem(),
        amount = (100..100000).random(),
        date = randomDate(60),
        type = randomType()
    )
}

fun randomType(): Type {
    return if (Random().nextInt(2) == 0) Type.INCOME else Type.OUTCOME
}

private fun randomDate(dayBefore: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = Date()
    cal.add(Calendar.DAY_OF_MONTH, -(0..dayBefore).random())
    return cal.time
}

private fun <T> Array<T>.randomItem(): T =
    this[Random().nextInt(this.size)]

private fun IntRange.random(): Int =
    Random().nextInt(this.endInclusive - start + 1) + start