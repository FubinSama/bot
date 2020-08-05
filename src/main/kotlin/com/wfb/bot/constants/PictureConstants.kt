package com.wfb.bot.constants

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "picture")
class PictureConstants {
    lateinit var recall: String
}