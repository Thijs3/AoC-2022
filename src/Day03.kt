fun main() {
    val itemTypePriorities: Map<Char,Int> = ((('a'..'z').toList() + ('A'..'Z').toList())
        .zip(1..52) )
        .toMap()

    fun String.splitInHalf() = substring(0, (length / 2)) to substring(length / 2)

    fun Pair<String, String>.findCommonChar(): Char = first
        .first { char -> second.contains(char) }

    fun List<String>.findBadge(): Char = first().first { char ->
        sumOf { rucksack ->
            rucksack.contains(char).compareTo(false)
        } > 2
    }

    fun part1(input: List<String>): Int =
        input.sumOf { items ->
            itemTypePriorities.getOrDefault(items.splitInHalf().findCommonChar(), 0)
        }

    fun part2(input: List<String>): Int =
        input.chunked(3).sumOf { group ->
            itemTypePriorities.getOrDefault(group.findBadge(), 0)
        }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03_input")
    println(part1(input))
    println(part2(input))
}
