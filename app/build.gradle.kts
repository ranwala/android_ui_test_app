plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

val scanbotSdkVersion: String by rootProject.extra
println("APP Scanbot SDK = $scanbotSdkVersion")

android {
    namespace = "com.example.android_ui_test_app"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.android_ui_test_app"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // app/build.gradle.kts (dependencies section):
    implementation("io.scanbot:sdk-package-1:$scanbotSdkVersion")
    implementation("io.scanbot:rtu-ui-v2-bundle:$scanbotSdkVersion")
    implementation("io.scanbot:sdk-docqualityanalyzer-assets:$scanbotSdkVersion")
}