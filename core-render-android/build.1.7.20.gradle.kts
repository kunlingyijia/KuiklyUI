plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

group = MavenConfig.GROUP
version = Version.getRenderVersion()

afterEvaluate {
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

        publications {
            create<MavenPublication>("maven") {
                groupId = MavenConfig.GROUP
                artifactId = MavenConfig.RENDER_ANDROID_ARTIFACT_ID
                version = Version.getRenderVersion()
                from(components["release"])
            }
        }
    }
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro")
//        }
//    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(Dependencies.tdfCommon)
    compileOnly("androidx.recyclerview:recyclerview:1.2.1")
    compileOnly(project(":core"))
    compileOnly("androidx.core:core-ktx:1.7.0")
    compileOnly("androidx.appcompat:appcompat:1.4.2")
    compileOnly("androidx.dynamicanimation:dynamicanimation:1.0.0")
}
