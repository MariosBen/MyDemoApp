plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.benetatos.livepage_domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "com.benetatos.mydemoapp.livepage.HiltTestRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    // Hilt Core
    implementation (libs.hilt.android)
    implementation(project(":core"))
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.jupiter)
    kapt (libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation (libs.mockk)
    // For unit tests
    testImplementation(libs.truth)

    // For instrumented (Android) tests
    androidTestImplementation(libs.truth)
    testImplementation (libs.hilt.android.testing)
    testImplementation("org.slf4j:slf4j-simple:2.0.7")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3") // Use the latest version

}
kapt {
    correctErrorTypes = true
}