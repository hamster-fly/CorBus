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
        minSdk = 16

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
    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01)

    signAllPublications()
    coordinates("io.github.hamster-fly", "CorBus", "1.0.0-SNAPSHOT")
    configure(
        com.vanniktech.maven.publish.AndroidSingleVariantLibrary(
            variant = "release",
            sourcesJar = true,
            publishJavadocJar = true,
        )
    )
    pom {
        name.set("CorBus")
        description.set("Kotlin Coroutine Bus!")
        url.set("https://github.com/hamster-fly/CorBus/")
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
            url.set("https://github.com/hamster-fly/CorBus/")
            connection.set("scm:git:git://github.com/hamster-fly/CorBus.git")
            developerConnection.set("scm:git:ssh://git@github.com/hamster-fly/CorBus.git")
        }
    }
}