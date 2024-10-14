import java.nio.file.Files

plugins {
    id("java")
    id("application")
    base
}

group = "com.github.abyssnlp"

application {
    mainClass = "com.github.com.abyssnlp.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

val downloadFlinkJars = tasks.register("downloadFlinkJars") {
    description = "Download Flink MySQL CDC jars"
    group = "Custom Tasks"

    val downloadDir = File(buildDir, "download-jars")
    val configuration = configurations.create("download-jars")

    dependencies {
        configuration("com.ververica:flink-connector-mysql-cdc:3.0.1")
        configuration("org.apache.flink:flink-shaded-hadoop-2-uber:2.7.5-10.0")
        configuration("")
    }

    inputs.files(configuration)
    outputs.dir(downloadDir)

    doLast {
        if (!downloadDir.exists()) {
            downloadDir.mkdirs()
        }

        configuration.resolve().forEach { file ->
            copy {
                from(file)
                into(downloadDir)
            }
        }

        println("JARS downloaded to ${downloadDir.absolutePath}")
    }

}

