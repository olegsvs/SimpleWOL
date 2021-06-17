plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "ru.olegsvs.wol"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "LAN_DST_MAC_ADDRESS", "\"2C:F0:5D:87:9B:A1\"")
        buildConfigField("String", "LAN_BROADCAST_MASK", "\"192.168.1.255\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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

dependencies {}