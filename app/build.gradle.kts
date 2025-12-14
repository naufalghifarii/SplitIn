plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // The Google Services plugin requires a local `google-services.json` file which
    // is intentionally excluded from version control. Apply the plugin only when
    // that file exists so the project can be built without Firebase config.
    id ("kotlin-parcelize")

}

android {
    namespace = "com.example.fairsplit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fairsplit"
        minSdk = 26
        targetSdk = 35
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
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("androidx.compose.material3:material3:1.3.2")
    implementation(("com.google.android.material:material:1.12.0"))
}

    // Apply Google Services plugin only if `google-services.json` is present.
    // This allows building the app locally when the file is intentionally omitted.
    val googleServicesFile = file("${projectDir}/google-services.json")
    if (googleServicesFile.exists()) {
        apply(plugin = "com.google.gms.google-services")
    } else {
        logger.lifecycle("google-services.json not found in ${projectDir}; skipping com.google.gms.google-services plugin")
    }