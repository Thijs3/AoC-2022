fun main() {
    fun String.findMarker(length: Int) = windowed(length).indexOfFirst { window ->
        window.toSet().size == length
    } + length

    fun part1(input: String): Int =
        input.findMarker(4)

    fun part2(input: String): Int =
        input.findMarker(14)

    val testInput = readInputString("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInputString("Day06_input")
    println(part1(input))
    println(part2(input))
}
