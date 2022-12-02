fun main() {
    fun calculateScore(game: Pair<String, String>): Int =
        when(game) {
            Pair("A", "X") -> 4
            Pair("A", "Y") -> 8
            Pair("A", "Z") -> 3
            Pair("B", "X") -> 1
            Pair("B", "Y") -> 5
            Pair("B", "Z") -> 9
            Pair("C", "X") -> 7
            Pair("C", "Y") -> 2
            Pair("C", "Z") -> 6
            else -> throw(Error("Unexpected input: $game"))
        }

    fun Pair<String, String>.transformToOriginalStrategy(): Pair<String, String> =
        when(this) {
            Pair("A", "X") -> Pair("A", "Z")
            Pair("A", "Y") -> Pair("A", "X")
            Pair("A", "Z") -> Pair("A", "Y")
            Pair("B", "X") -> Pair("B", "X")
            Pair("B", "Y") -> Pair("B", "Y")
            Pair("B", "Z") -> Pair("B", "Z")
            Pair("C", "X") -> Pair("C", "Y")
            Pair("C", "Y") -> Pair("C", "Z")
            Pair("C", "Z") -> Pair("C", "X")
            else -> throw(Error("Unexpected input: $this"))
        }

    fun part1(input: List<Pair<String, String>>): Int =
        input.sumOf { game -> calculateScore(game) }

    fun part2(input: List<Pair<String, String>>): Int =
        input.sumOf { game -> calculateScore(game.transformToOriginalStrategy()) }

    val testInput = readRockPaperScissors("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readRockPaperScissors("Day02_input")
    println(part1(input))
    println(part2(input))
}