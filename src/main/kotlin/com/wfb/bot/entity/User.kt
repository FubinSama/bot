package com.wfb.bot.entity

import java.io.Serializable
import java.time.LocalDateTime

class User: Serializable{
    var qqId: Long? = null
    var updateTime: LocalDateTime? = null
    var signTime: LocalDateTime? = null
    var money: Long? = null
    var createTime: LocalDateTime? = null
}