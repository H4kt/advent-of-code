plugins {
    kotlin("jvm")
}

val ktorVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    api("io.ktor:ktor-client-core:$ktorVersion")
    api("io.ktor:ktor-client-cio:$ktorVersion")
}
