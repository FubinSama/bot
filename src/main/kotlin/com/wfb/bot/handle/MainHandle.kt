package com.wfb.bot.handle

import com.wfb.bot.constants.PictureConstants
import com.wfb.bot.constants.QQConstants
import com.wfb.bot.constants.TipConstants
import com.wfb.bot.constants.TipConstants.changeToAdmin
import com.wfb.bot.constants.TipConstants.memberMute
import com.wfb.bot.constants.TipConstants.newMember
import com.wfb.bot.tools.PrintTool.wfbPrint
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.MemberPermission
import net.mamoe.mirai.contact.nameCardOrNick
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.event.subscribeTempMessages
import net.mamoe.mirai.join
import net.mamoe.mirai.message.FriendMessageEvent
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.uploadImage
import net.mamoe.mirai.utils.BotConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class MainHandle {

    @Autowired
    private lateinit var qqConstants: QQConstants

    @Autowired
    private lateinit var pictureConstants: PictureConstants

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Triple<Long, String, Boolean>>

    @Autowired
    private lateinit var textHandle: TextHandle

    @Value("\${device}")
    private lateinit var devicePath: String

    suspend fun doHandle() {
        val config = BotConfiguration()
        config.fileBasedDeviceInfo(devicePath)
        val bot = Bot(qqConstants.id.toLong(), qqConstants.password, config).alsoLogin()//新建Bot并登录

        bot.subscribeMessages {
            this.always {
                if (this.sender.id == this.bot.selfQQ.id) return@always
                when {
                    this.message.content.startsWith(".") -> textHandle.doHandle(this)?.apply { this@always.reply(this) }
                    this.message.content.contains("斌") -> reply("我最喜欢斌大大了(*^__^*) 嘻嘻……")
                    this.message.content == "你好" -> reply("你好( ⊙ o ⊙ )！")
                    this.message.firstIsInstanceOrNull<At>()?.target == bot.id -> quoteReply(At(sender as Member) + " 给爷爬╭(╯^╰)╮")
                }
            }
        }

        // 复读机
        bot.subscribeGroupMessages {
            this.always {
                val key = this.subject.id.toString()
                val thisMessage = this.message.last().toString()
                wfbPrint("thisMessage[本条消息]: $thisMessage")
                val opsForValue = redisTemplate.opsForValue()
                val triple = opsForValue[key]
                wfbPrint("triple[上条消息]: ${triple.toString()}")
                if (triple != null) {
                    if (thisMessage == triple.second) { // 跟上一条消息相同
                        if (triple.first != this.sender.id && !triple.third) { // 跟上一条消息不是一个人发送的且该条消息还未复读
                            // 复读该条消息并设置该条消息为已复读
                            opsForValue[key] = Triple(this.sender.id, thisMessage, true)
                            reply(this.message)
                        }
                    } else { // 跟上一条消息不同，需要更新缓存
                        opsForValue[key] = Triple(this.sender.id, thisMessage, false)
                    }
                } else {  // 还没有缓存，需要更新缓存
                    opsForValue[key] = Triple(this.sender.id, thisMessage, false)
                }
            }
        }

        // 朋友消息
        bot.subscribeAlways<FriendMessageEvent> {
            // val thisMessage = this.message.last()
            //TODO 根据消息的不同分别进行处理，并存储到数据库
        }

        // 好友消息撤回，api不支持
        bot.subscribeAlways<MessageRecallEvent.FriendRecall> {
            val friend = this.bot.getFriend(this.operator)
            val stream = ClassPathResource(pictureConstants.recall).inputStream
            val image = friend.uploadImage(stream)
            wfbPrint(this.operator.toString())
            this.bot.friends[this.operator].sendMessage(Image(image.imageId))
        }

        // 群友消息撤回
        bot.subscribeAlways<MessageRecallEvent.GroupRecall> {
            val stream = ClassPathResource(pictureConstants.recall).inputStream
            val image = this.group.uploadImage(stream)
            this.group.sendMessage(At(this.author) + Image(image.imageId))
        }

        // 机器人加入群组
        bot.subscribeAlways<BotJoinGroupEvent> {
            this.group.sendMessage(PlainText(TipConstants.join))
        }

        // 机器人新加朋友
        bot.subscribeAlways<FriendAddEvent> {
            this.friend.sendMessage(PlainText(TipConstants.join))
        }

        // 新人入群
        bot.subscribeAlways<MemberJoinEvent> { this.group.sendMessage(PlainText(this.member.nameCardOrNick.newMember())) }

        // 某人被禁言
        bot.subscribeAlways<MemberMuteEvent> { this.group.sendMessage(PlainText(this.member.nameCardOrNick.memberMute())) }

        // 某人称为管理员
        bot.subscribeAlways<MemberPermissionChangeEvent> {
            if (this.member.permission == MemberPermission.ADMINISTRATOR) {
                this.group.sendMessage(PlainText(this.member.nameCardOrNick.changeToAdmin()))
            }
        }

        // 某人对小斌发起临时会话
        bot.subscribeTempMessages { this.always { reply(TipConstants.tempMessage) } }

        bot.join() // 等待 Bot 离线, 避免主线程退出
    }
}