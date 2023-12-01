import io.ktor.client.request.*
import io.ktor.client.statement.*

enum class Gesture(val points: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}

enum class Outcome(val points: Int) {
    WIN(6),
    DRAW(3),
    LOSS(0)
}

fun Char.toGesture(): Gesture {
    return when (this) {
        'A', 'X' -> Gesture.ROCK
        'B', 'Y' -> Gesture.PAPER
        'C', 'Z' -> Gesture.SCISSORS
        else -> error("Unknown gesture $this")
    }
}

fun Gesture.beats(): Gesture {
    return when (this) {
        Gesture.ROCK -> Gesture.SCISSORS
        Gesture.PAPER -> Gesture.ROCK
        Gesture.SCISSORS -> Gesture.PAPER
    }
}

fun Gesture.beatenBy(): Gesture {
    return when (this) {
        Gesture.ROCK -> Gesture.PAPER
        Gesture.PAPER -> Gesture.SCISSORS
        Gesture.SCISSORS -> Gesture.ROCK
    }
}

fun Char.toOutcome(): Outcome {
    return when (this) {
        'X' -> Outcome.LOSS
        'Y' -> Outcome.DRAW
        'Z' -> Outcome.WIN
        else -> error("Unknown outcome $this")
    }
}

suspend fun main() {

    val input = client.get("2022/day/2/input").bodyAsText()

    fun part1(): Int {
        return input.lines()
            .sumOf { round ->

                if (round.isEmpty()) {
                    return@sumOf 0
                }

                val (opponentGesture, yourGesture) = round.split(" ")
                    .map { it.first().toGesture() }

                return@sumOf computeOutcome(opponentGesture, yourGesture).points + yourGesture.points
            }
    }

    fun part2(): Int {
        return input.lines()
            .sumOf { round ->

                if (round.isEmpty()) {
                    return@sumOf 0
                }

                val (rawOpponentGesture, _, rawOutcome) = round.toCharArray()

                val opponentGesture = rawOpponentGesture.toGesture()
                val outcome = rawOutcome.toOutcome()

                val yourGesture = when (outcome) {
                    Outcome.WIN -> opponentGesture.beatenBy()
                    Outcome.DRAW -> opponentGesture
                    Outcome.LOSS -> opponentGesture.beats()
                }

                return@sumOf outcome.points + yourGesture.points
            }
    }

    println(part1())
    println(part2())

}

fun computeOutcome(
    opponent: Gesture,
    you: Gesture
) = when {
    opponent.beats() == you -> Outcome.LOSS
    opponent == you -> Outcome.DRAW
    else -> Outcome.WIN
}
