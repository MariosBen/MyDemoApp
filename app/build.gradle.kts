plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.room")
}

android {
    namespace = "com.benetatos.mydemoapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.benetatos.mydemoapp"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.benetatos.mydemoapp.livepage.HiltTestRunner"
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
    buildFeatures {
        compose = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.gson)

    // Hilt Core
    implementation(libs.hilt.android)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)
    kapt(libs.hilt.android.compiler)

    // Compose Core Dependencies
    implementation (libs.ui)
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    // Hilt Navigation for Compose
    implementation (libs.androidx.hilt.navigation.compose)

    // Retrofit
    implementation (libs.retrofit.v2110)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    //Room
    implementation (libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    implementation(project(":livePage:livePage_presentation"))
    implementation(project(":livePage:livePage_data"))
    implementation(project(":livePage:livePage_domain"))
    implementation(project(":core"))
    implementation(project(":core-ui"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.androidx.animation.core.android)
    implementation(libs.androidx.appcompat)

    // test
    // Mockito for mocking
    testImplementation (libs.mockito.core)
    testImplementation (libs.mockito.kotlin)

    // MockK for Kotlin-specific mocking
    testImplementation (libs.mockk)

    testImplementation (libs.mockwebserver)

// For unit tests
    testImplementation(libs.truth)

    // For instrumented (Android) tests
    androidTestImplementation(libs.truth)
    testImplementation (libs.hilt.android.testing)
    testImplementation("org.slf4j:slf4j-simple:2.0.7")

}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}