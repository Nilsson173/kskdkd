plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.example.marketplaceonly"
    compileSdk = 35
    defaultConfig { applicationId = "com.example.marketplaceonly"; minSdk = 26; targetSdk = 35; versionCode = 1; versionName = "0.1" }
}
