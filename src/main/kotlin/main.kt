import java.util.*
import java.util.function.Consumer
import java.util.regex.Pattern
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    while (true) {
        println("Please enter expression in Reverse Polish notation and press 'Enter' or empty line for exit")
        val input: String? = readLine()

        if (input.isNullOrBlank()) {
            exitProcess(0)
        }

        try {
            println("${calculate(list(input))}")
        } catch (e: EmptyStackException) {
            println("It seems like expression was not in Reverse Polish notation. Check and correct expression")
        }
    }
}

fun calculate(split: List<String>?): Double {
    val numberStack: Stack<Double> = Stack()

    split?.forEach(Consumer {
        if (Pattern.matches("\\d+", it)) {
            numberStack.push(it.toDouble())
        } else {
            val second = numberStack.pop()
            if(numberStack.empty()){
                numberStack.push(any(it).invoke(second, 0.0))
            }
            else {
                numberStack.push(any(it).invoke(second, numberStack.pop()))
            }
        }
    })

    return numberStack.firstElement()
}

private fun any(operator: String): (Double, Double) -> Double {
    return when (operator) {
        "+" -> ::plus
        "-" -> ::minus
        "/" -> ::divide
        else -> ::multiply
    }
}

private fun list(input: String?) = input?.replace("\\(\\s?-", "(0-")
    ?.replace("^-", "0-")
    ?.replace("^\\+", "0+")
    ?.trim()
    ?.split(Pattern.compile("\\s+"))

private fun plus(second: Double, first: Double) = first + second

private fun minus(second: Double, first: Double) = first.minus(second)

private fun divide(second: Double, first: Double) = first.div(second)

private fun multiply(second: Double, first: Double) = first.times(second)