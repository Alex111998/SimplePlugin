plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.8.21"
  id("org.jetbrains.intellij") version "1.13.3"
  id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}
javafx {
//  version = "17.0.2"
  version = "11.0.2"
  modules = listOf("javafx.controls",
          "javafx.fxml",
          "javafx.web",
          "javafx.graphics",
          "javafx.media",
          "javafx.swing")
}
dependencies {
  // JavaFX 模块依赖
//  implementation("org.openjfx:javafx-base:17.0.2")
//  implementation("org.openjfx:javafx-controls:17.0.2")
//  implementation("org.openjfx:javafx-web:17.0.2")
//  implementation("org.openjfx:javafx-media:17.0.2")
//  implementation("org.openjfx:javafx-swing:17.0.2")
//  implementation("org.openjfx:javafx-fxml:17.0.2")
//  implementation("org.openjfx:javafx-graphics:17.0.2")
    implementation("org.java-websocket:Java-WebSocket:1.5.7")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
  version.set("2020.3")
  type.set("IC") // Target IDE Platform

  plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
  }

  patchPluginXml {
    sinceBuild.set("201.*")
    untilBuild.set("232.*")
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}
