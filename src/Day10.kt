import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val states = mutableListOf(1, 1)
        input.forEach { op ->
            if (op == "noop") {
                states.add(states.last())
            } else {
                states.add(states.last())
                states.add(states.last() + op.split(" ")[1].toInt())
            }
        }
        val indices = listOf(20, 60, 100, 140, 180, 220)
        return indices.sumOf { it * states[it] }
    }

    fun part2(input: List<String>) {
        val states = mutableListOf(1, 1)
        input.forEach { op ->
            if (op == "noop") {
                states.add(states.last())
            } else {
                states.add(states.last())
                states.add(states.last() + op.split(" ")[1].toInt())
            }
        }
        val pixels = states.take(241).takeLast(240).chunked(40)
        pixels.forEach { row ->
            row.forEachIndexed { index, pixel ->
                if (abs(pixel-index) < 2) print("#")
                else print(".")
            }
            print("\n")
        }
    }

    val testInput = readInputLines("Day10_test")
    check(part1(testInput) == 13140)
    // part2(testInput)

    val input = readInputLines("Day10_input")
    println(part1(input))
    part2(input)
}