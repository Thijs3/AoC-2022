fun main() {
    class Monkey(startingItems: List<Long>, val rule: (Long) -> Long, val targets: Pair<Int, Int>, val modulus: Long ){
        val items = startingItems.toMutableList()
        var inspectCount = 0L

        fun playTurn(monkeys: List<Monkey>) {
            items.forEach { item ->
                inspectCount += 1L
                val worry = rule(item) / 3L
                if (worry % modulus == 0L) monkeys[targets.first].items.add(worry) else monkeys[targets.second].items.add(worry)
            }
            items.clear()
        }

        fun playOtherTurn(monkeys: List<Monkey>) {
            items.forEach { item ->
                inspectCount += 1L
                val worry = rule(item) % 9699690L // product of all moduli
                if (worry % modulus == 0L) monkeys[targets.first].items.add(worry) else monkeys[targets.second].items.add(worry)
            }
            items.clear()
        }
    }

    fun String.toMonkey(): Monkey {
        val lines = split("\n").map { it.trim() }
        val startingItems = lines[1].split(": ")[1].split(", ").map { it.toLong() }
        val operationValueRaw = lines[2].split(" ").last()
        val operator = lines[2].split(" ")[4]
        val modulus: Long = lines[3].split(" ").last().toLong()
        val rule: (Long) -> Long = { worry ->
            val operationValue = if (operationValueRaw == "old") worry else operationValueRaw.toLong()
            when (operator) {
                "*" -> (worry * operationValue)
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
    //check(part2(testInputSecond) == 2713310158L)

    val input = readInputString("Day11_input").toMonkeys()
    val inputSecond = readInputString("Day11_input").toMonkeys()
    println(part1(input))
    println(part2(inputSecond))
}
