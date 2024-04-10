import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

val apikeyPropertiesFile = rootProject.file("key.properties")
val apiKeyProperties = Properties()
apiKeyProperties.load(FileInputStream(apikeyPropertiesFile))

android {
    namespace = "com.example.moviebox"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.moviebox"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "MOVIE_DB_KEY", apiKeyProperties["MOVIE_DB_KEY"] as String)
        buildConfigField("String", "YOUTUBE_KEY", apiKeyProperties["YOUTUBE_KEY"] as String)
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val daggerHiltVersion = "2.50"
    val retrofitVersion = "2.9.0"
    val coroutinesVersion = "1.8.0-RC2"
    val viewModelVersion = "2.7.0"
    val coilComposeVersion = "2.4.0"
    val navVersion = "2.7.7"
    val splashScreenVersion = "1.0.1"
    val lottieVersion = "6.4.0"
    val swipeRefreshVersion = "0.27.0"
    val youtubePlayerVersion = "12.1.0"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("io.coil-kt:coil-svg:2.2.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.4")
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")
    implementation("jp.wasabeef:glide-transformations:4.3.0")
    implementation("jp.co.cyberagent.android:gpuimage:2.1.0")
    implementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofitVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutinesVersion}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${viewModelVersion}")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.core:core-splashscreen:$splashScreenVersion")
    implementation("com.airbnb.android:lottie-compose:${lottieVersion}")
    implementation("io.coil-kt:coil-compose:${coilComposeVersion}")
    implementation("com.google.accompanist:accompanist-swiperefresh:$swipeRefreshVersion")
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:$youtubePlayerVersion")
    implementation("com.google.dagger:hilt-android:${daggerHiltVersion}")

    kapt("com.google.dagger:hilt-android-compiler:${daggerHiltVersion}")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

kapt {
    correctErrorTypes = true
}
