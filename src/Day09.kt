import Direction.EAST
import Direction.NORTH
import Direction.SOUTH
import Direction.WEST
import kotlin.math.abs

fun main() {
    data class Move(val direction: Direction, val amount: Int)
    data class Position(val x: Int, val y: Int)

    fun String.toDirection() = when (this) {
        "U" -> NORTH
        "D" -> SOUTH
        "L" -> WEST
        "R" -> EAST
        else -> throw Exception("Not a valid direction in UDLR")
    }

    fun Position.executeMove(direction: Direction): Position = when (direction) {
        NORTH -> Position(x, y + 1)
        SOUTH -> Position(x, y - 1)
        WEST -> Position(x - 1, y)
        EAST -> Position(x + 1, y)
    }

    fun Position.directNeighbours(): List<Position> = listOf(
        Position(x - 1, y),
        Position(x + 1, y),
        Position(x, y - 1),
        Position(x, y + 1)
    )

    fun Position.diagonalNeighbours(): List<Position> = listOf(
        Position(x - 1, y - 1),
        Position(x + 1, y - 1),
        Position(x - 1, y + 1),
        Position(x + 1, y + 1)
    )

    fun Position.allNeighbours(): List<Position> = diagonalNeighbours() + directNeighbours()

    fun Position.follow(other: Position): Position {
        // if no move is needed
        if (abs(x - other.x) < 2 && abs(y - other.y) < 2) return Position(x, y)
        // if the move can be horizontal or vertical
        if (directNeighbours().any {
                other.directNeighbours().contains(it)
            }) return directNeighbours().first { other.directNeighbours().contains(it) }
        // else move diagonally
        return diagonalNeighbours().first { other.allNeighbours().contains(it) }
    }

    fun List<List<String>>.toMoves(): List<Move> = map { (direction, amount) ->
        Move(direction.toDirection(), amount.toInt())
    }

    fun part1(input: List<Move>): Int {
        val visited = mutableSetOf(Position(0, 0))
        var posHead = Position(0, 0)
        var posTail = Position(0, 0)

        for (move in input) {
            for (step in 1..move.amount) {
                posHead = posHead.executeMove(move.direction)
                posTail = posTail.follow(posHead)
                visited.add(posTail)
            }
        }
        return visited.size
    }

    fun part2(input: List<Move>): Int {
        val visited = mutableSetOf(Position(0, 0))
        var posHead = Position(0, 0)
        val posMiddle = MutableList(8) { Position(0, 0) }
        var posTail = Position(0, 0)

        for (move in input) {
            for (step in 1..move.amount) {
                posHead = posHead.executeMove(move.direction)
                posMiddle[0] = posMiddle[0].follow(posHead)
                for (knot in 1 until posMiddle.size) {
                    posMiddle[knot] = posMiddle[knot].follow(posMiddle[knot - 1])
                }
                posTail = posTail.follow(posMiddle.last())
                visited.add(posTail)
            }
        }
        return visited.size
    }

    val testInput = readInputWords("Day09_test").toMoves()
    check(part1(testInput) == 13 )
    check(part2(testInput) == 1)

    val input = readInputWords("Day09_input").toMoves()
    println(part1(input))
    println(part2(input))
}
