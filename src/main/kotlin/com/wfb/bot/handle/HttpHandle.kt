package com.wfb.bot.handle

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

object HttpHandle {
    suspend fun bingHandle(q: String): String {
        val resp = HttpClient().use {
            it.get<String>("https://api.cognitive.microsoft.com/bing/v7.0/search") {
                header("Ocp-Apim-Subscription-Key", "efc88abacdea423db5d10f2e3a5e8c2a")
                parameter("q", q)
                parameter("count", 1)
            }
        }
        val res = Json.parseJson(resp).jsonObject["webPages"]?.jsonObject?.get("value")?.jsonArray?.get(0)?.jsonObject
                ?: throw Exception("什么东西坏掉了,大概是bing吧...不可能是小斌!")
        return """
            小斌帮你🔍到了这个呦：
            ${res["name"]}
            ${res["snippet"]}
            ${res["url"]}
        """.trimIndent()
    }

    suspend fun hhsh(text: String): String {
        val resp = HttpClient().use {
            it.post<String>("https://lab.magiconch.com/api/nbnhhsh/guess"){
                body = TextContent("""
                    {
                        "text": "$text"
                    }
                """.trimIndent(), ContentType.Application.Json)
            }
        }
        val res = Json.parseJson(resp).jsonArray[0].jsonObject["trans"] ?: throw Exception("什么东西坏掉了,大概是hhsh吧...不可能是小斌!")
        return """
            小斌帮你🔍到了这个呦：
            $res
        """.trimIndent()
    }
}

//suspend fun main() {
//    val response = HttpHandle.hhsh("hhsh")
//    println(response)
//}