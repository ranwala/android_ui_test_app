// Top-level build file where you can add configuration options common to all sub-projects/modules.
extra["scanbotSdkVersion"] =
    (findProperty("SCANBOT_SDK") as String?) ?: "7.1.2"

println("ROOT Scanbot SDK = ${extra["scanbotSdkVersion"]}")

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}