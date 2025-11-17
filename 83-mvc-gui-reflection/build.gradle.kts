plugins {
    application
    java
    id("org.danilopianini.gradle-java-qa") version "1.153.0"
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("it.unibo.mvc.LaunchApp")
}

dependencies {
    implementation("com.github.spotbugs:spotbugs-annotations:4.7.3")
}

tasks.javadoc {
    isFailOnError = false
}

