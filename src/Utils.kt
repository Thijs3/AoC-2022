import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
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

fun readRockPaperScissors(name: String): List<Pair<String, String>> =
    readInput(name).map { line -> Pair(line.split(" ")[0], line.split(" ")[1]) }

fun readAssignments(name: String): List<Pair<IntRange,IntRange>> = readInput(name)
    .map { line -> line.split(",") }.map { elves ->
        Pair(elves.first().split("-").let { it.first().toInt()..it.last().toInt() },
            elves.last().split("-").let {it.first().toInt()..it.last().toInt()})
    }

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
