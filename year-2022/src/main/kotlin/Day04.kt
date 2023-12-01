import io.ktor.client.request.*
import io.ktor.client.statement.*

fun String.toRange(): IntRange {
    val (from, to) = split("-").map(String::toInt)
    return from..to
}

operator fun IntRange.contains(other: IntRange): Boolean {
    return first <= other.first && last >= other.last
}

infix fun IntRange.overlapsWith(other: IntRange): Boolean {
    return intersect(other).isNotEmpty()
}

suspend fun main() {

    val input = client.get("2022/day/4/input")
        .bodyAsText()
        .dropLast(1) // Removes empty line at the end of the input

    fun part1(): Int {
        return input
            .lines()
            .map {
                it.split(",").map(String::toRange)
            }
            .count { (first, second) ->
                first in second || second in first
            }
    }

    fun part2(): Int {
        return input
            .lines()
            .map {
                it.split(",").map(String::toRange)
            }
            .count { (first, second) ->
                first overlapsWith second
            }
    }

    println(part1())
    println(part2())

}
