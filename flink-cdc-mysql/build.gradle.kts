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

val downloadFlinkJars = tasks.register("downloadFlinkJars") {
    description = "Download Flink MySQL CDC jars"
    group = "Custom Tasks"

    val downloadDir = File(buildDir, "download-jars")
    val configuration = configurations.create("download-jars")
    val debeziumConnectorName = "debezium-connector-mysql"
    val debeziumConnectorVersion = "1.9.7.Final"

    dependencies {
        configuration("com.ververica:flink-connector-mysql-cdc:2.4.1")
        configuration("io.debezium:${debeziumConnectorName}:${debeziumConnectorVersion}")
        configuration("org.apache.flink:flink-shaded-hadoop-2-uber:2.4.1-10.0")
        configuration("io.delta:delta-flink:3.2.0")
        configuration("io.delta:delta-standalone_2.12:3.2.0")
        configuration("io.delta:delta-storage:3.2.0")
//        configuration("org.apache.flink:flink-sql-parquet:1.16.0")
        configuration("com.chuusai:shapeless_2.12:2.3.4")
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

        // Rename the debezium jar as it leads to issues
        // https://github.com/apache/flink-cdc/issues/2340

        val debeziumFileName = "${debeziumConnectorName}-${debeziumConnectorVersion}.jar"
        val newFileName = "flink-dep-${debeziumFileName}"
        val oldFile = File(downloadDir, debeziumFileName)

        if (oldFile.exists()) {
            val newFile = File(downloadDir, newFileName)
            if (oldFile.renameTo(newFile)) {
                println("Renamed $debeziumFileName to $newFileName")
            } else {
                println("Failed to rename $debeziumFileName to $newFileName")
            }
        } else {
            println("File $debeziumFileName not found in the build directory!")
        }

        println("JARS downloaded to ${downloadDir.absolutePath}")
    }

}

