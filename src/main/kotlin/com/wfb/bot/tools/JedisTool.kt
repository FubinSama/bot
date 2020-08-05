package com.wfb.bot.tools

object JedisTool{
    private val sp = "\u200b"
    // 发送者 消息 是否已复读
    fun Triple<Long, String, Boolean>.toStr(): String = "$first$sp$second$sp$third"
    fun String.toTripe(): Triple<Long, String, Boolean> = this.let {
        val s = this.split(sp)
        Triple<Long, String, Boolean>(s[0].toLong(), s[1], s[2].toBoolean())
    }

}
