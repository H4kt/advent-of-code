import io.ktor.client.request.*
import io.ktor.client.statement.*

suspend fun main() {

    val input = client.get("2022/day/1/input").bodyAsText()

    println(input.split("\n\n"))

    val elvesCalories = input.split("\n\n")
        .map { calories ->
            calories.lines().sumOf { it.toInt() }
        }

    println(elvesCalories.sortedDescending().take(3).sum())

}
