package com.wfb.bot.test

import com.wfb.bot.handle.TextHandle.textHandle

fun main() {
    textHandle(".help").apply { println(this) }
    textHandle(".help echo").apply { println(this) }
    textHandle(".help echo ").apply { println(this) }
    textHandle(".help ").apply { println(this) }
    textHandle(".echo").apply { println(this) }
    textHandle(".echo ").apply { println(this) }
    textHandle(".echo 123").apply { println(this) }
    textHandle(".echo 123 ").apply { println(this) }
    textHandle(".time").apply { println(this) }
    textHandle(".签到").apply { println(this) }
}