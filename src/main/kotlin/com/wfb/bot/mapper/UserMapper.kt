package com.wfb.bot.mapper

import com.wfb.bot.entity.User
import org.springframework.stereotype.Repository

@Repository
interface UserMapper {
    fun selectByPrimaryKey(qqId: Long): User?
    fun updateByPrimaryKeySelective(user: User): Int?
    fun insertSelective(user: User): Int?
    fun deleteByPrimaryKey(qqId: Long): Int?
}