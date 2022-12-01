import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

fun readElvenWeights(name: String): List<List<Int>> = readInput(name)
    .joinToString(separator = "") { if (it == "") "." else "$it," }
    .split(".")
    .map { s ->
        s.split(",")
            .mapNotNull { item ->
                if (item == "") null
                else item.toInt()
            }
    }

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
