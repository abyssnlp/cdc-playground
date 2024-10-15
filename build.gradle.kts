plugins {
    id("java")
}

group = "com.github.abyssnlp"
version = "1.0"

repositories {
    maven {
        url = uri("https://repo.maven.apache.org/maven2")
    }
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}