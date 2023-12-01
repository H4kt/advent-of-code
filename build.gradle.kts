plugins {
    kotlin("jvm") version "1.9.21"
}

group = "dev.h4kt"
version = "1.0"

val ktorVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
}

kotlin {
    jvmToolchain(8)
}
