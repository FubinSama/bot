package com.wfb.bot.service

interface SignService {
    fun signIn(qqId: Long): String
    fun getAmount(qqId: Long): String
}