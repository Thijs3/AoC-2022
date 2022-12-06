fun main() {
    val itemTypePriorities: Map<Char,Int> = ((('a'..'z').toList() + ('A'..'Z').toList())
        .zip(1..52) )
        .toMap()

    fun Char.toPriority(): Int = itemTypePriorities.getOrDefault(this, 0)

    fun String.splitInHalves() = substring(0, (length / 2)) to substring(length / 2)

    fun Pair<String, String>.findCommonItem(): Char = first
        .first { char -> second.contains(char) }

    fun List<String>.findBadge(): Char = first()
        .first { char ->
            2 < sumOf { rucksack -> rucksack.contains(char).compareTo(false) }
    }

    fun part1(input: List<String>): Int = input
        .sumOf { items ->
            items.splitInHalves()
                .findCommonItem()
                .toPriority()
        }

    fun part2(input: List<String>): Int = input
        .chunked(3)
        .sumOf { group ->
            group.findBadge()
                .toPriority()
        }

    val testInput = readInputLines("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInputLines("Day03_input")
    println(part1(input))
    println(part2(input))
}
