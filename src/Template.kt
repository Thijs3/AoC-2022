fun main() {
    fun part1(input: List<String>): Int =
        input.size

    fun part2(input: List<String>): Int =
        input.size

    val testInput = readInputLines("Day02_test")
    // check(part1(testInput) == )
    // check(part2(testInput) == )
    println(testInput)

    val input = readInputLines("Day02_input")
    println(part1(input))
    println(part2(input))
}
