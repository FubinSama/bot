package com.wfb.bot.handle

import com.wfb.bot.constants.TipConstants
import com.wfb.bot.constants.TipConstants.moreEcho
import com.wfb.bot.constants.TipConstants.notSupportInfo
import com.wfb.bot.service.SignService
import com.wfb.bot.tools.DateTool.getCurrentTime
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class TextHandle {

    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    @Autowired
    private lateinit var signService: SignService

    fun echoHandle(messageEvent: MessageEvent, content: String): String {
        val key = "ECHO_" + messageEvent.sender.id.toString()
        val ops = stringRedisTemplate.opsForValue()
        when (ops[key]?.toInt() ?: 0) {
            0 -> {
                ops.set(key, "1", 20, TimeUnit.SECONDS)
                return content
            }
            1 -> {
                ops.set(key, "2", 40, TimeUnit.SECONDS)
                return "${TipConstants.twiceEcho}\n$content"
            }
            2 -> {
                ops.set(key, "3", 60, TimeUnit.SECONDS)
                return "${TipConstants.thirdEcho}\n$content"
            }
            3 -> {
                ops.set(key, "4", 120, TimeUnit.SECONDS)
                return TipConstants.forthEcho
            }
            else -> {
                val time = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS)
                return time.toString().moreEcho()
            }
        }
    }

    fun doHandle(messageEvent: MessageEvent): String {
        val qqId = messageEvent.sender.id
        val message = messageEvent.message.content
        val list = "(?i)^\\.([\\w\u4e00-\u9fa5]+)\\s*([\\w\\W]*)\$".toRegex().matchEntire(message)?.groupValues
        if (list == null || list.size != 3 || list[1].isBlank()) return(TipConstants.dotStartTip)
        else {
            when (list[1]) {
                "help" -> return when (list[2]) {
                    "" -> TipConstants.help
                    "echo" -> TipConstants.echoHelp
                    "ping" -> TipConstants.pingHelp
                    "time" -> TipConstants.timeHelp
                    else -> TipConstants.tip
                }
                "ping" -> return when (list[2]) {
                    "" -> "pong"
                    else -> TipConstants.tip
                }
                "time" -> return when (list[2]) {
                    "" -> "当前时间是：${getCurrentTime()}"
                    else -> TipConstants.tip
                }
                "echo" -> return when (list[2]) {
                    "" -> TipConstants.tip
                    else -> echoHandle(messageEvent, list[2])
                }
                "签到" -> return when(list[2]) {
                    "" -> signService.signIn(qqId)
                    else -> TipConstants.tip
                }
                else -> return list[1].notSupportInfo()
            }
        }
    }
}
