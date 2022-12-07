data class CraneMove(
    val amount: Int,
    val from: Int,
    val to: Int
)

class Cargo(
    private val stacks: MutableList<String>,
    private val moves: List<CraneMove>
) {
    fun stackTops(): String = stacks.map { it.last() }.joinToString("")
    fun executeAllMoves9000() = moves.forEach { move ->
        stacks[move.to] += stacks[move.from].takeLast(move.amount).reversed()
        stacks[move.from] = stacks[move.from].dropLast(move.amount)
    }

    fun executeAllMoves9001() = moves.forEach { move ->
        stacks[move.to] += stacks[move.from].takeLast(move.amount)
        stacks[move.from] = stacks[move.from].dropLast(move.amount)
    }
}
fun main() {
    fun part1(cargo: Cargo): String {
        cargo.executeAllMoves9000()
        return cargo.stackTops()
    }

    fun part2(cargo: Cargo): String {
        cargo.executeAllMoves9001()
        return cargo.stackTops()
    }

    val testInput1 = readCargo("Day05_test")
    val testInput2 = readCargo("Day05_test")
    check(part1(testInput1) == "CMZ")
    check(part2(testInput2) == "MCD")

    val input1 = readCargo("Day05_input")
    val input2 = readCargo("Day05_input")
    println(part1(input1))
    println(part2(input2))
}
