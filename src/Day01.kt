fun main() {
    fun part1(input: List<List<Int>>): Int =
        input.maxOf { elf -> elf.sum() }

    fun part2(input: List<List<Int>>): Int =
        input.map { elf -> elf.sum() }
            .sorted()
            .takeLast(3)
            .sum()

    val testInput = readElvenWeights("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readElvenWeights("Day01_input")
    println(part1(input))
    println(part2(input))
}
