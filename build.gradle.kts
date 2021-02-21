import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
}

group = "pt.diogo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven {
        setUrl("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        setUrl("https://mvn.intellectualsites.com/content/repositories/releases/")
    }
}

dependencies {
    implementation("com.zaxxer:HikariCP:4.0.2")
    implementation("org.jetbrains.exposed:exposed-core:0.29.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.29.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.29.1")
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.intellectualsites.fawe:FAWE-Bukkit:1.16-583")
    testImplementation(kotlin("test-junit"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}