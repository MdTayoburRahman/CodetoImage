plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.mdtayoburrahman.codetoimage"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mdtayoburrahman.codetoimage"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            debugSymbolLevel = "SYMBOL_TABLE"
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/DEPENDENCIES"
        }
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

    buildFeatures {
        viewBinding = true
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("io.github.amrdeveloper:codeview:1.3.9")
    implementation ("com.github.yukuku:ambilwarna:2.0.1")
    implementation ("androidx.browser:browser:1.8.0")

    //for in-app update
    implementation("com.google.android.play:asset-delivery:2.2.2")
    implementation("com.google.android.play:feature-delivery:2.1.0")
    implementation("com.google.android.play:review:2.0.1")
    implementation("com.google.android.play:app-update:2.1.0")
    //permission
    implementation("com.guolindev.permissionx:permissionx:1.8.0")


}