package com.wfb.bot.test

import java.io.File

fun main() {
    val file = File(PropertiesTool.getStringOrDefault("picture.recall", ""))
    println(file.absoluteFile)
    println(file.exists())
    println(file.canRead())

    val file2 = File(PropertiesTool.getStringOrDefault("device", ""))
    println(file2.absoluteFile)
    println(file2.exists())
    println(file2.canRead())
}