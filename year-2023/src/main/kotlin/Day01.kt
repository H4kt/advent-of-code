import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.math.min

val digitNames = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

suspend fun main() {

    val input = client.get("2023/day/1/input")
        .bodyAsText()
        .dropLast(1)

    fun part1() {

        val result = input.lines()
            .sumOf { line ->

                val first = line.find(Char::isDigit)
                val last = line.findLast(Char::isDigit)

                return@sumOf "$first$last".toInt()
            }

        println("Part1: $result")

    }

    fun part2() {

        val result = input.lines()
            .sumOf { line ->

                var first: Int? = null
                var last: Int? = null

                line.forEachIndexed { index, char ->

                    var current: Int? = null
                    if (char.isDigit()) {
                        current = char.digitToInt()
                    }

                    for (nameIndex in digitNames.indices) {

                        val name = digitNames[nameIndex]
                        val rangeEnd = min(line.length, index + name.length)

                        if (line.substring(index, rangeEnd) == name) {
                            current = nameIndex.inc()
                            break
                        }

                    }

                    if (current == null) {
                        return@forEachIndexed
                    }

                    if (first == null) {
                        first = current
                    }

                    last = current

                }

                return@sumOf "$first$last".toInt()
            }

        println("Part 2: $result")

    }

    part1()
    part2()

}
