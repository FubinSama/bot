package com.wfb.bot.handle

import com.wfb.bot.constants.TipConstants.calcIllegal
import com.wfb.bot.constants.TipConstants.calcResult
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

object CalcHandle {
    private val opHeader = arrayOf('+', '-', '*', '/', '(', ')', '#')
    private val opTable = arrayOf(
        // op2       +    -    *    /    (    )    #    // op1
            arrayOf('>', '>', '<', '<', '<', '>', '>'), // +
            arrayOf('>', '>', '<', '<', '<', '>', '>'), // -
            arrayOf('>', '>', '>', '>', '<', '>', '>'), // *
            arrayOf('>', '>', '>', '>', '<', '>', '>'), // /
            arrayOf('<', '<', '<', '<', '<', '=', 'E'), // (
            arrayOf('>', '>', '>', '>', 'E', '>', '>'), // )
            arrayOf('<', '<', '<', '<', '<', 'E', '=')  // #
    )

    // 返回两个运算符的优先级关系，null表示非法字符或状态
    fun getPriority(op1: Char, op2: Char): Char {
        val index1 = opHeader.indexOf(op1)
        if (index1 < 0) throw Exception("[$op1]不是一个合法运算符")
        val index2 = opHeader.indexOf(op2)
        if (index2 < 0) throw Exception("[$op2]不是一个合法运算符")
        val priority = opTable[index1][index2]
        if (priority == 'E') throw Exception("[$op1] ... [$op2]不是一个正确的写法")
        return priority
    }

    fun calc(str: String): String {
        val calc = Calc(str)
        return try {
            calc.doCalc().substring(0, 16).calcResult()
        } catch (e: Exception) {
            (e.message ?: "").calcIllegal()
        }
    }

    class Calc(str: String) {

        private val opStack = LinkedList<Char>().apply { this.push('#') }
        private val dataStack = LinkedList<BigDecimal>()
        private val charArray = if (!str.contains("#")) "$str#".toCharArray() else throw Exception("#不应该出现在式子中")

        //状态初始化：初始时栈中含有#，相当于前一个ch为左括号
        private var i = 0
        private var isLastedNumber = false
        private var isLastedLeftParentheses = true

        private fun setLastedNumber() { resetAllStatus(); isLastedNumber = true }

        private fun setLastedLeftParentheses() { resetAllStatus(); isLastedLeftParentheses = true }

        private fun resetAllStatus() { isLastedLeftParentheses = false; isLastedNumber = false }

        private fun doRead() = charArray[i++] // 读取一个字符

        private fun reRead() = i-- //撤销一次字符读取

        private fun skipWhite() { while(Character.isWhitespace(charArray[i])) i++ } //跳过空白符

        private fun readNumber(): BigDecimal {
            //遇到不符合数字逻辑的字符就退出，将验证交给调用方处理
            if (isLastedNumber) throw Exception("两个数字不能连在一起") //上一个也是数字，则抛出异常
            setLastedNumber() // 将上次为数字的状态标志设置为true
            val st = i
            var dotFlag = false
            loop@ while (i < charArray.size) {
                val ch = doRead()
                when {
                    Character.isDigit(ch) -> {} // do nothing
                    ch == '.' -> if (!dotFlag) dotFlag=true else break@loop
                    else -> break@loop
                }
            }
            reRead() // 回退一次读取
            return BigDecimal(charArray, st, i-st) //此时st指向数字的开始，i指向第一个非数字
        }

        private fun readNegative() = readNumber().negate()

        private fun doEval(op: Char) {
            when (op) {
                '+' -> dataStack.push(dataStack.pop().add(dataStack.pop()))
                '-' -> {val num1 = dataStack.pop(); dataStack.push(dataStack.pop().subtract(num1))}
                '*' -> dataStack.push(dataStack.pop().multiply(dataStack.pop()))
                '/' -> {val num1 = dataStack.pop(); dataStack.push(dataStack.pop().divide(num1, 24, RoundingMode.HALF_UP))}
            }
        }

        private fun handleOp(ch: Char) {
            when (ch) { // 根据ch设置相应的状态标志
                '(' -> setLastedLeftParentheses()
                else -> resetAllStatus()
            }
            when (getPriority(opStack.peek(), ch)) {
                '<' -> opStack.push(ch)
                '>' -> {
                    do { doEval(opStack.pop()) } while (getPriority(opStack.peek(), ch) == '>') // 弹栈并运算直到op1(栈顶)优先级不大于op2
                    if (getPriority(opStack.peek(), ch) == '=') opStack.pop() // 若op1优先级与op2相等，则直接弹出'('或者'#'
                    else opStack.push(ch) // 若op1优先级小于op2，则将op2放入栈中
                }
                '=' -> opStack.pop() //直接弹出'('或者'#'
            }
        }

        fun doCalc(): String {
            while (i < charArray.size) {
                val ch = doRead()
                when {
                    ch == ' ' -> skipWhite()
                    Character.isDigit(ch) -> { reRead(); dataStack.push(readNumber()) }
                    ch == '-' && isLastedLeftParentheses -> dataStack.push(readNegative())
                    ch == '+' && isLastedLeftParentheses -> dataStack.push(readNumber())
                    ch in opHeader -> handleOp(ch)
                    else -> throw Exception("表达式含有非法的字符")
                }
            }
            if (opStack.isEmpty() && dataStack.size == 1) return dataStack.pop().toString()
            throw Exception("一个未完成的表达式")
        }
    }
}
//
//fun main() {
//    CalcHandle.calc("-7+5").apply { println(this) }
//}