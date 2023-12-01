import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import java.io.File

val env = File(".env")
    .takeIf { it.exists() }
    ?.readLines()
    ?.associate { line ->
        val (key, value) = line.split("=")
        key to value
    }
    ?: emptyMap()

private val sessionToken = env["sessionToken"]
    ?: error("Could not find session token in .env file")

val client = HttpClient(CIO) {
    defaultRequest {
        url("https://adventofcode.com/")
        cookie("session", sessionToken)
    }
}
