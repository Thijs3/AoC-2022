import java.math.BigInteger

fun main() {
    class Monkey(startingItems: List<BigInteger>, val rule: (BigInteger) -> BigInteger, val targets: Pair<Int, Int>, val modulus: BigInteger ){
        val items = startingItems.toMutableList()
        var inspectCount = 0L

        fun playTurn(monkeys: List<Monkey>) {
            items.forEach { item ->
                inspectCount += 1L
                val worry = rule(item) / BigInteger("3")
                if (worry % modulus == BigInteger.ZERO) monkeys[targets.first].items.add(worry) else monkeys[targets.second].items.add(worry)
            }
            items.clear()
        }

        fun playOtherTurn(monkeys: List<Monkey>) {
            items.forEach { item ->
                inspectCount += 1L
                val worry = rule(item)
                if (worry % modulus == BigInteger.ZERO) monkeys[targets.first].items.add(worry) else monkeys[targets.second].items.add(worry)
            }
            items.clear()
        }
    }

    fun String.toMonkey(): Monkey {
        val lines = split("\n").map { it.trim() }
        val startingItems = lines[1].split(": ")[1].split(", ").map { it.toBigInteger() }
        val operationValueRaw = lines[2].split(" ").last()
        val operator = lines[2].split(" ")[4]
        val modulus: BigInteger = lines[3].split(" ").last().toBigInteger()
        val rule: (BigInteger) -> BigInteger = { worry ->
            val operationValue = if (operationValueRaw == "old") worry else operationValueRaw.toBigInteger()
            when (operator) {
                "*" -> ((worry * operationValue) % BigInteger("9699690")) // product of all moduli
                else -> (worry + operationValue)
            }
        }
        val targets = lines[4].split(" ").last().toInt() to lines[5].split(" ").last().toInt()
        return Monkey(startingItems, rule, targets, modulus)
    }
    fun String.toMonkeys(): List<Monkey> = split("\n\n").map { it.toMonkey() }


    fun part1(monkeys: List<Monkey>): Long {
        (1..20).forEach { _ -> monkeys.forEach { monkey -> monkey.playTurn(monkeys) } }
        val monkeyBusiness = monkeys.map { it.inspectCount }.sorted()
        return monkeyBusiness[monkeyBusiness.size-2] * monkeyBusiness.last()
    }

    fun part2(monkeys: List<Monkey>): Long {
        (1..10000).forEach { turn -> monkeys.forEach { monkey ->
            monkey.playOtherTurn(monkeys) } }
        val monkeyBusinessRaw = monkeys.map { it.inspectCount }
        val monkeyBusiness = monkeyBusinessRaw.sorted()
        return monkeyBusiness[monkeyBusiness.size - 2] * monkeyBusiness.last()
    }

    val testInput = readInputString("Day11_test").toMonkeys()
    val testInputSecond = readInputString("Day11_test").toMonkeys()
    check(part1(testInput) == 10605L)
    //check(part2(testInputSecond) == 2713310158)

    val input = readInputString("Day11_input").toMonkeys()
    val inputSecond = readInputString("Day11_input").toMonkeys()
    println(part1(input))
    println(part2(inputSecond))
}
