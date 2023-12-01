import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.util.LinkedList
import kotlin.math.ceil

val crateRegex = "\\s?[\\s\\[](.)[\\s\\]]".toRegex()
val moveRegex = "move (?<amount>\\d+) from (?<from>\\d+) to (?<to>\\d+)".toRegex()

data class Move(
    val amount: Int,
    val from: Int,
    val to: Int
)

fun String.parseStacks(): List<LinkedList<Char>> {

    val input = lines()

    val stackCount = input.first()
        .length
        .div(4.0)
        .run(::ceil)
        .toInt()

    val stacks = List(stackCount) {
        LinkedList<Char>()
    }

    input
        .dropLast(1) // Removes the numbers line
        .map {
            crateRegex.findAll(it).map { result ->
                result.groupValues[1].first()
            }
        }
        .forEach { row ->
            row.forEachIndexed { index, char ->

                if (char != ' ') {
                    stacks[index] += char
                }

            }
        }

    return stacks
}

fun String.parseMove(): Move {

    val match = moveRegex.matchEntire(this)
        ?: error("Invalid move input: $this")

    fun MatchGroupCollection.getNamedInt(name: String): Int {
        return this[name]!!.value.toInt()
    }

    return Move(
        amount = match.groups.getNamedInt("amount"),
        from = match.groups.getNamedInt("from").dec(),
        to = match.groups.getNamedInt("to").dec()
    )
}

fun String.parseMoves(): List<Move> {
    return lines().map(String::parseMove)
}

fun printStacks(stacks: List<List<Char>>) {

    val maxStackSize = stacks.maxOf { it.size }
    val formattedStacks = MutableList(maxStackSize) { "" }

    val mappedStacks = stacks
        .map { stack ->
            stack.reversed().map { crate -> "[$crate]" }
        }

    for (i in 0..<maxStackSize) {
        formattedStacks[i] = mappedStacks
            .map { it.getOrNull(i) }
            .joinToString(" ") { it ?: "   " }
    }

    formattedStacks.reversed()
        .forEach(::println)

    stacks.indices
        .joinToString(" ") {
            " $it "
        }
        .run(::println)

}

suspend fun main() {

    val input = client.get("2022/day/5/input")
        .bodyAsText()
        .dropLast(1) // Removes empty line at the end of the input

    val (stacksInput, movesInput) = input.split("\n\n")

    fun part1(): String {

        val stacks = stacksInput.parseStacks()
        val moves = movesInput.parseMoves()

        moves.forEach { move ->
            repeat(move.amount) {
                val crate = stacks[move.from].pop()
                stacks[move.to].push(crate)
            }
        }

        return stacks
            .map { it.first }
            .joinToString("")
    }

    fun part2(): String {

        val stacks = stacksInput.parseStacks()
        val moves = movesInput.parseMoves()

        moves.forEach { move ->

            val taken = stacks[move.from].take(move.amount)
            stacks[move.to].addAll(0, taken)

            repeat(move.amount) {
                stacks[move.from].pop()
            }

        }

        return stacks
            .map { it.first }
            .joinToString("")
    }

    println(part1())
    println(part2())

}
