import io.ktor.client.request.*
import io.ktor.client.statement.*

private val gameRegex = "Game (\\d+): (.+)".toRegex()

suspend fun main() {

    val input = client.get("2023/day/2/input")
        .bodyAsText()
        .dropLast(1)

    val games = input.lines()
        .map { line ->
            val (_, gameId, outcomes) = gameRegex.matchEntire(line)!!.groupValues
            return@map gameId to outcomes
        }

    fun part1() {

        val maxValuesByColor = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )

        val result = games.sumOf { (gameId, outcomes) ->

            val mappedOutcomes = outcomes.split("; ")
                .map {
                    it.split(", ")
                }
                .map { visible ->
                    visible.map { it.split(" ") }
                        .groupBy { (_, color) ->
                            color
                        }
                        .mapValues { (_, value) ->
                            value.fold(0) { acc, it ->
                                acc + it.first().toInt()
                            }
                        }
                }

            val anyExcess = mappedOutcomes.any {
                it.any { (color, value) ->
                    value > maxValuesByColor[color]!!
                }
            }

            return@sumOf if (anyExcess) {
                0
            } else {
                gameId.toInt()
            }
        }

        println(result)

    }

    fun part2() {

        val result = games.sumOf { (_, outcomes) ->

            val minValueByColor = mutableMapOf<String, Int>()

            outcomes.split("; ")
                .map {
                    it.split(", ")
                }
                .forEach { visible ->

                    visible
                        .map {
                            val (count, color) = it.split(" ")
                            return@map count.toInt() to color
                        }
                        .forEach { (count, color) ->

                            val currentCount = minValueByColor[color] ?: 0
                            if (currentCount < count) {
                                minValueByColor[color] = count
                            }

                        }

                }

            return@sumOf minValueByColor.values.reduce { acc, it -> acc * it }
        }

        println(result)

    }

//    part1()
    part2()

}
