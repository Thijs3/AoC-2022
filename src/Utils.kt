import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

fun readElvenCalories(name: String): List<List<Int>> = File("src", "$name.txt")
    .readText()
    .split("\n\n")
    .map { elf ->
        elf.split("\n")
            .map { calories ->
            calories.toInt()
        }
    }

fun readRockPaperScissors(name: String): List<Pair<String, String>> = readInput(name)
    .map { line ->
        line.split(" ")
            .let { game ->
                game.first() to game.last()
            }
    }

fun readAssignments(name: String): List<Pair<IntRange,IntRange>> = readInput(name)
    .map { line ->
        line.split(",")
    }.map { elves ->
        elves.first().split("-").let { it.first().toInt()..it.last().toInt() } to
            elves.last().split("-").let {it.first().toInt()..it.last().toInt()}
    }

fun readCargo(name: String): Cargo =  File("src", "$name.txt")
    .readText()
    .split("\n\n")
    .map { it.split("\n") }
    .let { (stacks, moves) ->
        Cargo(stacks.toMutableList(), moves.toMoves())
    }

fun List<String>.toMoves(): List<CraneMove> = map { line ->
    line.split(" ")
        .let { (amount, from, to) ->
            CraneMove(amount.toInt(), from.toInt() - 1, to.toInt() - 1)
        }
}

fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
