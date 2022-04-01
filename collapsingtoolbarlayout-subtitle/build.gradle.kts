plugins {
    android("library")
    kotlin("android")
//    `maven-publish`
//    signing
}

android {
    compileSdk = SDK_TARGET
    defaultConfig {
        minSdk = SDK_MIN
        targetSdk = SDK_TARGET
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
    sourceSets {
        named("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDir("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
        named("androidTest") {
            setRoot("tests")
            manifest.srcFile("tests/AndroidManifest.xml")
            java.srcDir("tests/src")
            res.srcDir("tests/res")
            resources.srcDir("tests/src")
        }
    }
    libraryVariants.all {
        generateBuildConfigProvider.orNull?.enabled = false
    }
}

dependencies {
    implementation(material())
    runtimeOnly(androidx("appcompat")) // TODO: investigate why this is necessary
    androidTestImplementation(kotlin("stdlib", VERSION_KOTLIN))
    androidTestImplementation(kotlin("test-junit", VERSION_KOTLIN))
    androidTestImplementation(hendraanggrian("material", "bannerbar-ktx", "$VERSION_ANDROIDX-SNAPSHOT"))
    androidTestImplementation(material())
    androidTestImplementation(androidx("multidex", version = VERSION_MULTIDEX))
    androidTestImplementation(androidx("core", "core-ktx"))
    androidTestImplementation(androidx("appcompat"))
    androidTestImplementation(androidx("coordinatorlayout", version = "1.1.0"))
    androidTestImplementation(androidx("test", "core-ktx", VERSION_ANDROIDX_TEST))
    androidTestImplementation(androidx("test", "runner", VERSION_ANDROIDX_TEST))
    androidTestImplementation(androidx("test", "rules", VERSION_ANDROIDX_TEST))
    androidTestImplementation(androidx("test.ext", "junit-ktx", VERSION_ANDROIDX_JUNIT))
    androidTestImplementation(androidx("test.ext", "truth", VERSION_ANDROIDX_TRUTH))
    androidTestImplementation(androidx("test.espresso", "espresso-core", VERSION_ESPRESSO))
}

tasks {
    val javadoc by registering(Javadoc::class) {
        isFailOnError = false
        source = android.sourceSets["main"].java.getSourceFiles()
        classpath += project.files(android.bootClasspath.joinToString(File.pathSeparator))
    }
}

//mavenPublishAndroid(sources = android.sourceSets["main"].java.srcDirs)