import io.ktor.client.request.*
import io.ktor.client.statement.*

fun Char.toPriority(): Int {
    val lowercasePriority = lowercaseChar() - 'a' + 1
    return if (isLowerCase()) {
        lowercasePriority
    } else {
        lowercasePriority + 26
    }
}

suspend fun main() {

    val input = client.get("2022/day/3/input").bodyAsText()

    fun part1(): Int {
        return input.lines()
            .filterNot { it.isEmpty() }
            .map {
                val halfLength = it.length / 2
                return@map it.take(halfLength) to it.drop(halfLength)
            }
            .sumOf { (first, second) ->

                val index = first.indexOfFirst {
                    it in second
                }

                val item = first[index]

                return@sumOf item.toPriority()
            }
    }

    fun part2(): Int {
        return input.lines()
            .filterNot { it.isEmpty() }
            .chunked(3)
            .sumOf {

                val first = it.first()
                val otherTwo = it.drop(1)

                val commonItem = first.first { item ->
                    otherTwo.all { contents -> item in contents }
                }

                return@sumOf commonItem.toPriority()
            }
    }

    println(part1())
    println(part2())

}
