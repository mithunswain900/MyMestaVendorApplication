plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinKapt)

}

android {
    namespace = "com.mymestavendorapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mymestavendorapplication"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
           viewBinding{
               enable = true
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //glide
    implementation(libs.glide)
    //recyclerview
    implementation(libs.androidx.recyclerview)
    //retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.convertor)
    //viewmodels
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.runtime)
    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.livedata)
    //dagger
    implementation(libs.dagger)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    annotationProcessor(libs.dagger.compiler)
    kapt(libs.dagger.kapt)
    kapt(libs.dagger.kapt.processor)


    //browser
    implementation(libs.androidx.browser)

    //room
    implementation(libs.androidx.room)
    annotationProcessor(libs.room.kapt)
    kapt(libs.room.kapt)
    implementation(libs.room.ktx)
    //circular Image
    implementation("de.hdodenhof:circleimageview:3.1.0")
    //slider


}