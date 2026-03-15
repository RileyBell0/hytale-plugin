plugins {
    id("java")
    id("com.gradleup.shadow") version "9.4.0"
}

group = "dev.twunk"
version = "0.1.0"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java {
            // Points to the root of the 'dev/twunk' folder structure
            srcDirs("../libtwunk/src/main/java")
        }
    }
}

dependencies {
    // Add all JAR files in a directory
    compileOnly(files("libs/HytaleServer.jar"))

    implementation("org.ow2.asm:asm:9.7")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    archiveClassifier.set("") // replace normal jar
}

tasks.build {
    dependsOn(tasks.shadowJar)
}