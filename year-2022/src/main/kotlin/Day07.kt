import io.ktor.client.request.*
import io.ktor.client.statement.*

sealed interface InputLine {

    data class Command(
        val name: String,
        val args: List<String>
    ) : InputLine

    @JvmInline
    value class CommandOutput(
        val value: String
    ) : InputLine

}

sealed interface FileSystemEntry {

    val name: String

    data class File(
        override val name: String,
        val size: Long
    ) : FileSystemEntry

    data class Directory(
        override val name: String,
        val parent: Directory? = null,
        val children: MutableList<FileSystemEntry> = mutableListOf()
    ) : FileSystemEntry

}

fun String.parseInputLine(): InputLine {
    return if (startsWith("$")) {
        val data = split(" ")
        InputLine.Command(
            name = data[1],
            args = data.drop(2)
        )
    } else {
        InputLine.CommandOutput(this)
    }
}

suspend fun main() {

    val input = client.get("2022/day/6/input")
        .bodyAsText()
        .dropLast(1) // Removes empty line at the end of the input

    val root = FileSystemEntry.Directory(
        name = "/"
    )

    var currentDir = root
    var lastCommand: InputLine.Command? = null

//    input.lines()
//        .map(String::parseInputLine)
//        .forEach {
//
//            when (it) {
//                is InputLine.Command -> {
//                    when (it.name) {
//                        "cd" -> {
//                            val newPath = it.args.first()
//                            if (newPath.startsWith("/")) {
//                                currentPath = newPath
//                            } else {
//                                currentPath += "/$newPath"
//                            }
//                        }
//                    }
//                }
//                is InputLine.CommandOutput -> {
//
//                }
//            }
//
//        }
//
//    fun part1() {
//
//    }

}
