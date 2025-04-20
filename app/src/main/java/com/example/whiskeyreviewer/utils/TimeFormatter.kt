package com.example.whiskeyreviewer.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale

object TimeFormatter {

    fun dateCalculation(date: LocalDate): String {
        val currentDate = LocalDate.now()
        val day=ChronoUnit.DAYS.between(currentDate, date)
//        return when(val day=ChronoUnit.DAYS.between(currentDate, date)) {
//            0L -> "D+"
//            else -> "D-${day}"
//        }

        return "D+${day}"
    }


    fun formatDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

        return date.format(formatter)
    }


    fun formatDate(date: String):String{
        val dateTime = LocalDate.parse(date)

        val formatter = DateTimeFormatter.ofPattern("yyyy/M/d")
        return dateTime.format(formatter)
    }

    fun formatDateToKorea(date: String): String {
        val dateTime = LocalDate.parse(date)

        val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")
        return dateTime.format(formatter)
    }
    fun formatTime(time: LocalTime): String {
        val outputFormatter = DateTimeFormatterBuilder()
            // 오전/오후
            .appendText(ChronoField.AMPM_OF_DAY)
            .appendLiteral(' ')
            // 12시간제
            .appendValue(ChronoField.HOUR_OF_AMPM)
            .appendLiteral("시 ")
            .appendValue(ChronoField.MINUTE_OF_HOUR)
            .appendLiteral("분")
            .toFormatter()

        return time.format(outputFormatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun timeAgo(stringDate: String): String {
        Log.d("stringDate",stringDate.toString())

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]")
        val dateTime = try {
            LocalDateTime.parse(stringDate, formatter)
        } catch (e: Exception) {
            return "오류"
        }
        val now = LocalDateTime.now()

        val years = ChronoUnit.YEARS.between(dateTime, now)
        val months = ChronoUnit.MONTHS.between(dateTime, now)
        val days = ChronoUnit.DAYS.between(dateTime, now)
        val hours = ChronoUnit.HOURS.between(dateTime, now)
        val minutes = ChronoUnit.MINUTES.between(dateTime, now)
        val seconds = ChronoUnit.SECONDS.between(dateTime, now)

        return when {
            seconds < 60 -> "방금 전"
            minutes < 60 -> if (minutes == 1L) "1분 전" else "${minutes}분 전"
            hours < 24 -> if (hours == 1L) "1시간 전" else "${hours}시간 전"
            days < 30 -> if (days == 1L) "1일 전" else "${days}일 전"
            months < 12 -> if (months == 1L) "한달 전" else "${months}달 전"
            else -> if (years == 1L) "1년 전" else "${years}년 전"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun stringTimeFormatter(stringTime:String):String {

        val trimmedStringTime = stringTime.substring(0, 10)

        val pastDate = LocalDate.parse(trimmedStringTime)

        val currentDate = LocalDate.now()

        return ChronoUnit.DAYS.between(pastDate, currentDate).toString()
    }

    fun stringToLocalDate(dateString: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(dateString, formatter)
    }
}