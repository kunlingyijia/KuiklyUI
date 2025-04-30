plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

group = MavenConfig.GROUP
version = Version.getCoreVersion()

publishing {
    repositories {
        val username = MavenConfig.getUsername(project)
        val password = MavenConfig.getPassword(project)
        if (username.isNotEmpty() && password.isNotEmpty()) {
            maven {
                credentials {
                    setUsername(username)
                    setPassword(password)
                }
                url = uri(MavenConfig.getRepoUrl(version as String))
            }
        } else {
            mavenLocal()
        }
    }
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(Dependencies.kotlinpoet)
                implementation(Dependencies.kspApi)
                implementation(project(":core-annotations"))
            }
            kotlin.srcDir("src/main/kotlin")
            kotlin.srcDir("src/main/kotlin/impl")
            resources.srcDir("src/main/resources")
        }
    }
}