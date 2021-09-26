package utils.datetime

import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

private fun getOrdinal(n: Int) = when {
    n in 11..13 -> "${n}th"
    n % 10 == 1 -> "${n}st"
    n % 10 == 2 -> "${n}nd"
    n % 10 == 3 -> "${n}rd"
    else        -> "${n}th"
}

private val daysLookup = (1..31).associate { it.toLong() to getOrdinal(it) }
private val monthsLookup = mapOf(
    1 to "Jan",
    2 to "Feb",
    3 to "Mar",
    4 to "Apr",
    5 to "May",
    6 to "Jun",
    7 to "Jul",
    8 to "Aug",
    9 to "Sep",
    10 to "Oct",
    11 to "Nov",
    12 to "Dec",
).mapKeys { it.key.toLong() }

private val englishDateFormatter = DateTimeFormatterBuilder()
    .appendLiteral(" ")
    .appendText(ChronoField.DAY_OF_MONTH, daysLookup)
    .appendLiteral(" ")
    .appendText(ChronoField.MONTH_OF_YEAR, monthsLookup)
    .appendLiteral(" ")
    .appendPattern("yyyy")
    .toFormatter(Locale.ENGLISH)

fun LocalDateTime.format() = this.format(englishDateFormatter)
