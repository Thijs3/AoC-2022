fun main() {
    fun IntRange.contains(other: IntRange) = first <= other.first && last >= other.last

    fun IntRange.overlaps(other: IntRange) = !(first > other.last || last < other.first)

    fun part1(input: List<Pair<IntRange, IntRange>>): Int =
        input.filter { pair ->
            pair.first.contains(pair.second) || pair.second.contains(pair.first)
        }.size

    fun part2(input: List<Pair<IntRange, IntRange>>): Int =
        input.filter { pair ->
            pair.first.overlaps(pair.second)
        }.size

    val testInput = readAssignments("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4 )

    val input = readAssignments("Day04_input")
    println(part1(input))
    println(part2(input))
}
