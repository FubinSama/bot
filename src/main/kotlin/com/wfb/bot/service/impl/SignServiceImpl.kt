package com.wfb.bot.service.impl

import com.wfb.bot.constants.TipConstants.alreadySign
import com.wfb.bot.constants.TipConstants.sign
import com.wfb.bot.constants.TipConstants.getAmount
import com.wfb.bot.entity.User
import com.wfb.bot.mapper.UserMapper
import com.wfb.bot.service.SignService
import com.wfb.bot.tools.DateTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class SignServiceImpl : SignService {

    @Autowired
    private lateinit var userMapper: UserMapper

    override fun signIn(qqId: Long): String {
        val value = Random.nextInt(1, 11).toLong()
        val user = userMapper.selectByPrimaryKey(qqId)
        val now = LocalDateTime.now()
        if (user == null) {
            // 创建一个新的用户
            User().apply {
                this.qqId = qqId
                this.createTime = now
                this.signTime = now
                this.updateTime = now
                this.money = value
            }.let { userMapper.insertSelective(it) }
        } else {
            // 已经签到返回null
            if (user.signTime?.toLocalDate() == LocalDate.now()) {
                val localTime = user.signTime!!.toLocalTime()
                return DateTool.formatTime(localTime).alreadySign()
            }
            // 更新数据库
            User().apply {
                this.qqId = qqId
                this.updateTime = now
                this.signTime = now
                this.money = user.money!! + value
            }.let { userMapper.updateByPrimaryKeySelective(it) }
        }
        return value.toString().sign()
    }

    override fun getAmount(qqId: Long): String {
        val money = userMapper.selectByPrimaryKey(qqId)!!.money.toString()
        return money.getAmount()
    }
}