import com.vanniktech.maven.publish.SonatypeHost
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
    id("com.vanniktech.maven.publish") version "0.27.0"
}

android {
    namespace = "com.hamster.core"
    compileSdk = 33

    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01)

    signAllPublications()
    coordinates("io.dpj.com", "cor-bus", "1.0.0-SNAPSHOT")

    pom {
        name.set("CorBus")
        description.set("Kotlin Coroutine Bus!")
        inceptionYear.set("2024")
        url.set("https://github.com/hamster-fly/CorBus.git")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("hamster")
                name.set("hamster-fly")
                url.set("https://github.com/hamster-fly/")
            }
        }
        scm {
            url.set("https://github.com/hamster-fly/CorBus.git")
            connection.set("scm:git:git://github.com/hamster-fly/CorBus.git")
            developerConnection.set("scm:git:ssh://git@github.com:hamster-fly/CorBus.git")
        }
    }
}
publishing {
    repositories {
        maven {
            name = "tal"
            version = "1.0.0-SNAPSHOT"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) "http://10.14.250.163:8081/repository/Android_Snapshots/" else "http://10.14.250.163:8081/repository/Android_Releases/")
            credentials(PasswordCredentials::class)
            isAllowInsecureProtocol = true
        }
    }
}