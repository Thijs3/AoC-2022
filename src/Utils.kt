import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun readInputString(name: String) = File("src", "$name.txt").readText()
fun readInputLines(name: String) = File("src", "$name.txt")
    .readLines()

fun readInputWords(name: String) = File("src", "$name.txt")
    .readLines()
    .map { line ->
        line.split(" ")
    }

fun readElvenCalories(name: String): List<List<Int>> = File("src", "$name.txt")
    .readText()
    .split("\n\n")
    .map { elf ->
        elf.split("\n")
            .map { calories ->
                calories.toInt()
            }
    }

fun readRockPaperScissors(name: String): List<Pair<String, String>> = readInputLines(name)
    .map { line ->
        line.split(" ")
            .let { game ->
                game.first() to game.last()
            }
    }

fun readAssignments(name: String): List<Pair<IntRange, IntRange>> = readInputLines(name)
    .map { line ->
        line.split(",")
    }.map { elves ->
        elves.first().split("-").let { it.first().toInt()..it.last().toInt() } to
            elves.last().split("-").let { it.first().toInt()..it.last().toInt() }
    }

fun readCargo(name: String): Cargo = File("src", "$name.txt")
    .readText()
    .split("\n\n")
    .map { it.split("\n") }
    .let { (stacks, moves) ->
        Cargo(stacks.toMutableList(), moves.toMoves())
    }

fun readIntGrid(name: String) = readInputLines(name)
    .map { line ->
        line
            .map { value ->
                value.toString().toInt()
            }.toMutableList()
    }.toMutableList()
    .let { IntGrid(it) }

fun readGridFromChars(lines: List<String>): IntGrid = lines
    .map { line ->
        line.toList()
            .map {value ->
                value.code - 97
            }.toMutableList()
    }.toMutableList()
    .let { IntGrid(it) }

fun List<String>.toMoves(): List<CraneMove> = map { line ->
    line.split(" ")
        .let { (amount, from, to) ->
            CraneMove(amount.toInt(), from.toInt() - 1, to.toInt() - 1)
        }
}

fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

abstract class Grid<T>(val rows: MutableList<MutableList<T>>) {
    val cols: MutableList<MutableList<T>> = MutableList(rows[0].size) { c ->
        MutableList(rows.size) { r -> rows[r][c] }
    }
}

class IntGrid(rows: MutableList<MutableList<Int>>) : Grid<Int>(rows)

class StringGrid(rows: MutableList<MutableList<String>>) : Grid<String>(rows)

enum class Direction { NORTH, SOUTH, EAST, WEST}
