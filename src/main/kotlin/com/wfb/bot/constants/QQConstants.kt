package com.wfb.bot.constants

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("qq")
class QQConstants {
    lateinit var id: String
    lateinit var password: String

    val privileges = arrayOf(1070952257L, 122442573L)
}
