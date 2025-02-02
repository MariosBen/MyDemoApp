package com.benetatos.core.util

fun Long?.toFormatTime(): String {
    if (this == null) return "00:00:00"
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    return "${String.format("%02d", hours % 100)}:${String.format("%02d", minutes)}:${String.format("%02d", seconds)}"
}