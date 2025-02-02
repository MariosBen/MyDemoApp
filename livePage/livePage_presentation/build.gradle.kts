plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.benetatos.livepage_presentation"
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
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.material)
    implementation(libs.androidx.material3.android)

    // Hilt Core
    implementation(libs.hilt.android)
    implementation(libs.androidx.ui.tooling.preview.android)
    kapt(libs.hilt.android.compiler)

    // Compose Core Dependencies
    implementation (libs.ui)
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    // Hilt Navigation for Compose
    implementation (libs.androidx.hilt.navigation.compose)

    //Coroutines
    implementation(libs.kotlinx.coroutines.android)

    implementation(project(":livePage:livePage_domain"))
    implementation(project(":core-ui"))
    implementation(project(":core"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.animation.core.android)
}
kapt {
    correctErrorTypes = true
}