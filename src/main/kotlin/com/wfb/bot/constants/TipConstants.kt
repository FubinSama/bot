package com.wfb.bot.constants

object TipConstants{


    val tip = """
你是想要呼叫小斌吗？要晓得小斌有强迫症呦！！！
不给正确的参数，我可是不会去干活的╭(╯^╰)╮
    """.trimIndent()

    val help = """
小斌现在支持如下命令：
.help [arg]
.echo [arg]
.ping
.time
可以输入 [help + 命令]，如[.help echo]查看详细信息呦
    """.trimIndent()

    val echoHelp = """
这你都不知道咋用😂😂😂😂😂？不就是：
你说:[.echo 你好],小斌就会回复[你好]吗？
放弃吧，小斌才不会跟你玩呢╭（╯＾╰）╮
    """.trimIndent()

    val pingHelp = """
这你都不知道咋用😂😂😂😂😂？不就是：
你说:[.ping],小斌就会回复[pong]吗？
放弃吧，小斌才不会跟你玩呢╭（╯＾╰）╮
    """.trimIndent()

    val timeHelp = """
这你都不知道咋用😂😂😂😂😂？不就是：
你说:[.time],小斌就会回复[当前时间]吗？
放弃吧，小斌才不会跟你玩呢╭（╯＾╰）╮
    """.trimIndent()

    val dotStartTip = """
小斌不知道你在说什么呢。。。
如果是想找小斌的话，请输入正确的命令呦
可以使用[.help]查看小斌支持的命令(*^__^*)
    """.trimIndent()

    val tempMessage = """
你是zz吗？？？别给小斌发临时消息╭(╯^╰)╮
    """.trimIndent()

    fun String.notSupportInfo() = """
你好，小斌现在还不支持[${this}]这个命令呦
小斌现在支持如下命令：
.help [arg]
.echo [arg]
.ping
.time
可以输入 help + [命令] ，如[.help echo]查看详细信息呦
""".trimIndent()

    fun String.changeToAdmin() = """
恭喜“${this}”成为了本群的管理员！！！！！！
啥时候，小斌也能混个管理员当当( ⊙ o ⊙ )！
""".trimIndent()

    fun String.newMember() = """
欢迎“${this}”加入本群！！！
    """.trimIndent()

    fun String.memberMute() = """
恭喜老铁“${this}”喜提禁言套餐一份
    """.trimIndent()

    val join = """
你好，我是小斌，是一个呆萌可爱的robot!
$help
    """.trimIndent()

    val twiceEcho = """
这个不是酱紫读吗?????
小斌希望这是最后一次教你了
😓😓😓😓😓😓😓😓😓😓😓😓
    """.trimIndent()

    val thirdEcho = """
你咋这么笨呢╮(╯▽╰)╭？
这是小斌最后一次教你了!!！
小斌希望你能过会儿在找我
🤬🤬🤬🤬🤬🤬🤬🤬🤬
    """.trimIndent()

    val forthEcho = """
你太笨了，小斌现在不想理你！
2分钟后，再来找小斌吧！
🤫🤫🤫🤫🤫🤫🤫🤫🤫
    """.trimIndent()

    fun String.moreEcho() = """
都跟你说了小斌不想理你了...
please ${this}秒  后再来找我
    """.trimIndent()

    fun String.sign() = """
恭喜你！签到成功👍👍👍👍👍
你本次获得${this}枚游戏币
斌大大正在开发游戏功能中

    """.trimIndent()

    fun String.alreadySign() = """
你今天在${this}时已经签到了
我怀疑你想骗小斌的签到奖励
🤬🤬🤬🤬🤬🤬🤬🤬🤬🤬
    """.trimIndent()

    fun String.getAmount() = """
你当前拥有${this}枚游戏币！！！
    """.trimIndent()
}
