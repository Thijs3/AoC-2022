fun main() {
    fun part1(input: List<String>): Int =
        input.size

    fun part2(input: List<String>): Int =
        input.size

    val testInput = readInput("Day02_test")
    // check(part1(testInput) == )
    // check(part2(testInput) == )
    println(testInput)

    val input = readInput("Day02_input")
    println(part1(input))
    println(part2(input))
}