plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.didactic_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.didactic_app"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures{ viewBinding = true;}

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("javax.inject:javax.inject:1")
    implementation("com.google.dagger:dagger:2.25.2")

    implementation("io.reactivex.rxjava2:rxjava:2.1.6")
    implementation("io.reactivex.rxjava2:rxandroid:2.0.1")

    annotationProcessor("com.jakewharton:butterknife-compiler:10.2.0")
    implementation("com.jakewharton:butterknife:10.2.0")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0-alpha03")

    implementation("org.osmdroid:osmdroid-android:6.1.14")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}