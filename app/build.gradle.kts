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
    implementation(libs.codeview)
    implementation (libs.ambilwarna)
    implementation (libs.browser)

    //for in-app update
    implementation(libs.asset.delivery)
    implementation(libs.feature.delivery)
    implementation(libs.review)
    implementation(libs.app.update)
    //permission
    implementation(libs.permissionx)

    //glide
    implementation (libs.glide)
    annotationProcessor (libs.compiler)

    //lottie animation
    implementation (libs.lottie)


}