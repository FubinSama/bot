package com.wfb.bot.constants

object TipConstants{

    val tip = """
        你是想呼叫小斌吗( ⊙ o ⊙ )？
        要晓得小斌有强迫症呦！！！！！
        不给正确的参数🤬🤬🤬🤬🤬
        我是不会去干活的╭(╯^╰)╮
    """.trimIndent()

    val help = """
        小斌现在支持如下命令：
        .help [arg]
        .echo [arg]
        .calc [arg]
        .game [arg]
        .bing [arg]
        .hhsh [arg]
        .ping
        .time
        .签到
        可以输入 [help + 命令]，
        如[.help echo]查看详细信息呦
    """.trimIndent()

    val echoHelp = """
        这你都不知道咋用😂😂😂😂😂？
        不就是：你说:[.echo 你好]，
        小斌就会回复[你好]吗╭（╯＾╰）╮
        放弃吧，小斌才不会跟你玩呢😓😓😓
    """.trimIndent()

    val pingHelp = """
        这你都不知道咋用😂😂😂😂😂？
        不就是：你说:[.ping]，
        小斌就会回复[pong]吗╭(╯^╰)╮
        放弃吧，小斌才不会跟你玩呢😓😓😓
    """.trimIndent()

    val timeHelp = """
        这你都不知道咋用😂😂😂😂😂？
        不就是：你说:[.time]，
        小斌就会回复[当前时间]吗╭(╯^╰)╮
        放弃吧，小斌才不会跟你玩呢😓😓😓
    """.trimIndent()

    val calcHelp = """
        这你都不知道咋用😂😂😂😂😂？
        不就是：你说:[.calc 式子]，
        小斌就会回复[运行结果]吗╭(╯^╰)╮
        放弃吧，小斌才不会跟你玩呢😓😓😓
    """.trimIndent()

    val signHelp = """
        这你都不知道咋用😂😂😂😂😂？
        不就是：你说:[.签到]，
        小斌就会[替你签到]吗╭(╯^╰)╮
        别想骗小斌的签到奖励呦( ⊙ o ⊙ )！
        小斌记性可好了，一天只会给你一次奖励
        放弃吧，小斌才不会跟你玩呢😓😓😓     
    """.trimIndent()

    val gameHelp = """
        这你都不知道咋用😂😂😂😂😂？
        不就是：你说:[.game arg]，
        小斌就会[替你做事]吗？
        小斌支持如下选项呦：
         1. amount：查看剩余游戏币
         2. 没有2的啦，斌大大太懒了
        放弃吧，小斌才不会跟你玩呢😓😓😓
    """.trimIndent()

    val bingHelp = """
        这你都不知道咋用😂😂😂😂😂？
        不就是：你说:[.bing 关键字]，
        小斌就会[替你搜索]吗╭(╯^╰)╮
        放弃吧，小斌才不会跟你玩呢😓😓😓     
    """.trimIndent()

    val hhshHelp = """
        这你都不知道咋用😂😂😂😂😂？
        不就是：你说:[.hhsh 关键字]，
        小斌就会[替你搜索]吗╭(╯^╰)╮
        放弃吧，小斌才不会跟你玩呢😓😓😓     
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
        $help
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
        please  ${this}秒   后再来找我
    """.trimIndent()

    fun String.sign() = """
        恭喜你！签到成功👍👍👍👍👍
        你本次获得${this}枚游戏币
        斌大大正在开发游戏功能中
    """.trimIndent()

    fun String.alreadySign() = """
        你在${this}时已经签到了
        我怀疑你想骗小斌的签到奖励
        🤬🤬🤬🤬🤬🤬🤬🤬🤬🤬
    """.trimIndent()

    fun String.getAmount() = """
        你当前拥有${this}枚游戏币！！！
    """.trimIndent()

    val notPrivileges = """
        小斌可是不会认错斌大大的！
        我会看好家的，你休想进来！
        🤬🤬🤬🤬🤬🤬🤬🤬🤬
    """.trimIndent()

    val notSupport = """
        斌大大，小斌还没有这个功能呦！
        要不你抽空教教人家这个命令呗！
        (*^__^*)(*^__^*)(*^__^*)
    """.trimIndent()

    fun String.calcResult() = """
        这么简单的式子你都不会算吗？
        小斌只用了一会就算出来了呦！
        答案是：
        $this
    """.trimIndent()

    fun String.calcIllegal() = """
        这个表达式是什么鬼呦🤬🤬🤬
        小斌感觉它不是正常的四则运算
        下面是来自Java数字王国的通知：
        $this
    """.trimIndent()
}
