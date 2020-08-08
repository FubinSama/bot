package com.wfb.bot.handle

import com.wfb.bot.constants.QQConstants
import com.wfb.bot.constants.TipConstants
import com.wfb.bot.constants.TipConstants.moreEcho
import com.wfb.bot.constants.TipConstants.notSupportInfo
import com.wfb.bot.service.PrivilegeService
import com.wfb.bot.service.SignService
import com.wfb.bot.tools.DateTool.getCurrentTime
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.MessageEvent
import net.mamoe.mirai.message.data.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class TextHandle {

    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    @Autowired
    private lateinit var qqConstants: QQConstants

    @Autowired
    private lateinit var signService: SignService

    @Autowired
    private lateinit var privilegeService: PrivilegeService

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

    suspend fun doHandle(messageEvent: MessageEvent): Message? {
        val qqId = messageEvent.sender.id
        val message = messageEvent.message.content
        val list = "(?i)^\\.([\\w\u4e00-\u9fa5]+)\\s*([\\w\\W]*)\$".toRegex().matchEntire(message)?.groupValues
        if (list == null || list.size != 3 || list[1].isBlank()) return PlainText(TipConstants.dotStartTip)
        else {
            when (list[1]) {
                "help" -> return when (list[2]) {
                    "" -> PlainText(TipConstants.help)
                    "echo" -> PlainText(TipConstants.echoHelp)
                    "ping" -> PlainText(TipConstants.pingHelp)
                    "time" -> PlainText(TipConstants.timeHelp)
                    "calc" -> PlainText(TipConstants.calcHelp)
                    "bing" -> PlainText(TipConstants.bingHelp)
                    "hhsh" -> PlainText(TipConstants.hhshHelp)
                    "game" -> PlainText(TipConstants.gameHelp)
                    "签到" -> PlainText(TipConstants.signHelp)
                    else -> PlainText(TipConstants.tip)
                }
                "ping" -> return when (list[2]) {
                    "" -> PlainText("pong")
                    else -> PlainText(TipConstants.tip)
                }
                "time" -> return when (list[2]) {
                    "" -> PlainText("当前时间是：${getCurrentTime()}")
                    else -> PlainText(TipConstants.tip)
                }
                "echo" -> return when (list[2]) {
                    "" -> PlainText(TipConstants.tip)
                    else -> PlainText(echoHandle(messageEvent, list[2]))
                }
                "calc" -> return when(list[2]) {
                    "" -> PlainText(TipConstants.tip)
                    else -> PlainText(CalcHandle.calc(list[2]))
                }
                "bing" -> return when(list[2]) {
                    "" -> PlainText(TipConstants.tip)
                    else -> At(messageEvent.sender as Member) + HttpHandle.bingHandle(list[2])
                }
                "hhsh" -> return when(list[2]) {
                    "" -> PlainText(TipConstants.tip)
                    else -> At(messageEvent.sender as Member) + HttpHandle.hhsh(list[2])
                }
                "签到" -> return when(list[2]) {
                    "" -> PlainText(signService.signIn(qqId))
                    else -> PlainText(TipConstants.tip)
                }
                "game" -> return when(list[2]) {
                    "amount" -> PlainText(signService.getAmount(qqId))
                    else -> PlainText(TipConstants.tip)
                }
                "pull" -> return if (qqId !in qqConstants.privileges) PlainText(TipConstants.notPrivileges)
                    else when (list[2]) {
                        "" -> PlainText(privilegeService.pull())
                        else -> PlainText(TipConstants.notSupport)
                    }
                else -> return PlainText(list[1].notSupportInfo())
            }
        }
    }
}
