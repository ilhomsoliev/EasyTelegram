package com.ilhomsoliev.shared.common.extensions

private const val DASH = "-"
const val FORMAT = "yyyy-MM-dd"
const val TIME_FORMAT = "HH:mm:ss"
val LOCAL_TIME: LocalTime = LocalTime.now()
val LOCAL_DATE: LocalDate = LocalDate.now()
const val ZERO_TIME = "T00:00:00.000Z"
val NOW_DATE = "${LOCAL_DATE}T$LOCAL_TIME.000Z"
val TOMORROW = "${LOCAL_DATE.plusDays(1)}T$LOCAL_TIME.000Z"

fun getDate() = arrayListOf<String>().let {
    repeat(365) { i ->
        it.add("${LOCAL_DATE.plusDays(i)}$ZERO_TIME")
    }
    it.toList()
}

fun getDateByParentTime(
    datetime: String,
    duration: Int
) = arrayListOf<String>().let {
    val localDate = LocalDate.of(datetime)
    val localeDateTime = LocalDateTime.of(datetime)
    it.add("${localDate}$ZERO_TIME")
    val endLocalDateTime = LocalDateTime.of(datetime).plusMinute(duration).plusHour(5)
    val hasTomorrow = localeDateTime.day() != endLocalDateTime.day()
    if (hasTomorrow) {
        it.add("${localDate.plusDays(1)}$ZERO_TIME")
    }
    it.toList()
}

fun getTimeByParentTimeFirstPart(
    datetime: String,
    duration: Int
): Pair<ArrayList<String>, ArrayList<String>> {
    val localDateTime = LocalDateTime.of(datetime = datetime)
    val minHour = localDateTime.minusHour(1).hour()
    val maxHour = localDateTime.plusHour(duration).plusHour(5).hour()
    val targetHour = if (maxHour < minHour) 23 else maxHour
    return arrayListOf<String>().let {
        for (i in minHour..targetHour) {
            it.add(
                if (i in 0..9)
                    "0$i" else "$i"
            )
        }
        it
    } to arrayListOf<String>().let {
        repeat(12) { i ->
            it.add(
                when (i) {
                    0 -> "00"
                    1 -> "05"
                    else -> "${i * 5}"
                }
            )
        }; it
    }
}

fun getTimeByParentTimeSecondPart(
    datetime: String,
    duration: Int
): Pair<ArrayList<String>, ArrayList<String>> {
    val localDateTime = LocalDateTime.of(datetime = datetime)
    val minHour = localDateTime.minusHour(1).hour()
    val maxHour = localDateTime.plusHour(duration).plusHour(5).hour()

    return arrayListOf<String>().let {
        for (i in 0..maxHour) {
            it.add(
                if (i in 0..9)
                    "0$i" else "$i"
            )
        }
        it
    } to arrayListOf<String>().let {
        repeat(12) { i ->
            it.add(
                when (i) {
                    0 -> "00"
                    1 -> "05"
                    else -> "${i * 5}"
                }
            )
        }; it
    }
}

fun getTime() = arrayListOf<String>().let {
    repeat(24) { i ->
        it.add(
            if (i in 0..9)
                "0$i" else "$i"
        )
    }; it
} to arrayListOf<String>().let {
    repeat(12) { i ->
        it.add(
            when (i) {
                0 -> "00"
                1 -> "05"
                else -> "${i * 5}"
            }
        )
    }; it
}

fun todayControl(date: Long) =
    LocalDate(date).format(FORMAT) == LOCAL_DATE.format(FORMAT)

fun yesterdayControl(date: Long) =
    (LocalDate(date).format(FORMAT).split(DASH).let {
        LocalDate.of(
            it.first().toInt(),
            it[1].toInt(),
            it.last().toInt()
        ).second()
    }) in yesterday().first..yesterday().second

fun tomorrowControl(date: String) =
    date.format(FORMAT) == LOCAL_DATE
        .plusDays(1)
        .format(FORMAT)

fun weekControl(
    date: String,
) = (date.format(FORMAT)
    .split(DASH).let {
        LocalDate.of(
            it.first().toInt(),
            it[1].toInt(),
            it.last().toInt()
        ).second()
    }) in thisWeek().first..thisWeek().second

//Useless
@Suppress("unused")
fun earlierWeekControl(date: String): Boolean {
    val dateList = date.format(FORMAT).split(DASH)
    val localDate = LocalDate.of(
        dateList.first().toInt(),
        dateList[1].toInt(),
        dateList.last().toInt()
    ).second()
    return (localDate < thisWeek().first)
}

fun monthControl(date: String) = (date.format(FORMAT)
    .split(DASH).let {
        LocalDate.of(
            it.first().toInt(),
            it[1].toInt(),
            it.last().toInt()
        ).second()
    }) in thisMonth().first..thisMonth().second

fun getDifferenceOfTime(date: String): String {
    val difference = (
            LocalDateTime.now().plusMinute(1).millis() -
                    LocalDateTime.of(date).millis()
            ) / 1000
    return when {
        difference < 60 -> "$difference cек"
        difference in 60..3_599 -> "${difference / 60} мин"
        difference in 3_600..86_399 -> "${difference / 3_600} ч"
        difference in 86_400..604_799 -> "${difference / 86_400} дн."
        difference in 604_800..2_591_999 -> "${difference / 604_800} нед."
        difference in 2_592_000..946_079_999 -> "${difference / 2_592_000} мес."
        else -> (difference / 946_080_000).let {
            when ("$it".last()) {
                '1' -> "$it год"
                '2', '3', '4' -> "$it года"
                else -> "$it лет"
            }
        }
    }
}

fun String.date(): LocalDate {
    val dateList = this.format(FORMAT).split(DASH)
    return LocalDate.of(
        dateList.first().toInt(),
        dateList[1].toInt(),
        dateList.last().toInt()
    )
}

fun String.timeClock() =
    this.format("HH:mm")

fun String.getDurationForCard(): String {

    val hours = this.substringBefore(':')
    val minutes = this.substringAfter(':')
    return hours + if (minutes == "00") "часа" else "ч $minutes"
}

fun Long.dateCalendar() =
    LocalDate(this).format("dd MMMM YYYY.")

fun String.fullDateCalendar() =
    this.format("dd MMMM YYYY")


fun String.time() = this
    .format(TIME_FORMAT)
    .split(":").let {
        LocalTime.of(
            it.first().toInt(),
            it[1].toInt(),
            it.last().toInt()
        )
    }

fun yesterday() = LOCAL_DATE.minusDays(1)
    .let { it.second() to it.plusDays(1).second() }

fun thisWeek() = LOCAL_DATE.minusDays(1)
    .let { it.minusDays(7).second() to it.second() }

fun thisMonth() = LOCAL_DATE.minusDays(8)
    .let { it.minusDays(30).second() to it.second() }

/*
fun thisWeek() = LOCAL_DATE
    .let { it.minusDays(it.dayOfWeek().ordinal) }
    .let { it.second() to it.plusDays(7).second() }
*/

fun getMonth(date: String): Int {
    val dateList = date.format(FORMAT).split(DASH)
    val localDate = LocalDate.of(
        dateList.first().toInt(),
        dateList[1].toInt(),
        dateList.last().toInt()
    ).millis()

    return LocalDateTime(localDate).month()
}

// Chat
fun getChatDateSeparator(s: Long) = when {
    todayControl(s) -> "Сегодня"
    yesterdayControl(s) -> "Вчера"
    else -> s.dateCalendar()
}
