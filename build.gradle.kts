plugins {
    id("java")
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
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}