import io.ktor.client.request.*
import io.ktor.client.statement.*

suspend fun main() {

    val input = client.get("2022/day/6/input")
        .bodyAsText()
        .dropLast(1) // Removes empty line at the end of the input

    fun findPacketMarkerLength(
        input: String,
        markerLength: Int
    ): Int {

        var marker = listOf<Char>()

        for (i in input.indices) {

            val char = input[i]
            if (char in marker) {
                marker = marker.drop(marker.indexOf(char) + 1)
            }

            marker += char

            if (marker.size == markerLength) {
                return i + 1
            }

        }

        error("Unable to find packet marker length")
    }

    println(findPacketMarkerLength(input, 4))
    println(findPacketMarkerLength(input, 14))

}
