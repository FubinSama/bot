package com.wfb.bot.test

import java.util.*


object PropertiesTool{
    private val properties = Properties()
    init {
        val stream = PropertiesTool.javaClass.classLoader.getResourceAsStream("application.properties")
        properties.load(stream)
    }
    fun getBooleanOrDefault(key: String, default: Boolean) = properties.getProperty(key)?.toBoolean() ?: default
    fun getIntOrDefault(key: String, default: Int) = properties.getProperty(key)?.toInt() ?: default
    fun getLongOrDefault(key: String, default: Long) = properties.getProperty(key)?.toLong() ?: default
    fun getStringOrDefault(key: String, default: String) = properties.getProperty(key) ?: default
    fun getMsOrDefault(key: String, default: Long): Long {
        val str = properties.getProperty(key) ?: return default
        return when {
            str.contains("ms") -> str.replace("ms", "").toLong()
            str.contains("s") -> str.replace("s", "").toLong() * 1_000
            str.contains("m") -> str.replace("m", "").toLong() * 60 * 1_000
            str.contains("h") -> str.replace("h", "").toLong() * 60 * 60 * 1_000
            str.contains("L") -> str.replace("L", "").toLong()
            str.contains("l") -> str.replace("l", "").toLong()
            else -> return str.toLong()
        }
    }
}


fun main() {
    val database = PropertiesTool.getIntOrDefault("redis.database", 1)
    println(database)
}