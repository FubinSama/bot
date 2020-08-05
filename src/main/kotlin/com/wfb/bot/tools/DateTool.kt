package com.wfb.bot.tools

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTool {

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun formatTime(time: LocalTime): String = time.format(timeFormatter)


    fun formatDate(date: LocalDate): String = date.format(dateFormatter)

    fun formatDateTime(dateTime: LocalDateTime): String = dateTime.format(dateTimeFormatter)

    fun getCurrentTime(): String {
        val time = LocalDateTime.now()
        return formatDateTime(time)
    }
}