import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
  id("idea")
  id("version-catalog")
  id("war")
  alias(ctlg.plugins.kotlin.jvm)
  alias(ctlg.plugins.kotlin.spring)
  alias(ctlg.plugins.release)
  alias(ctlg.plugins.spotless)
  alias(ctlg.plugins.spring.boot)
  alias(ctlg.plugins.spring.dependency.management)
}

val group: String by project
val version: String by project

project.group = group

project.version = version

idea {
  module {
    isDownloadJavadoc = true
    isDownloadSources = true
  }
}

java {
  withSourcesJar()
  withJavadocJar()
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
    vendor.set(JvmVendorSpec.AMAZON)
  }
}

kotlin {
  compilerOptions {
    /**
     * Java types used by Kotlin relaxes the null-safety checks. And the Spring Framework provides
     * null-safety annotations that could be potentially used by Kotlin types. Therefore, we need to
     * make jsr305 "strict" to ensure null-safety checks is NOT relaxed in Kotlin when Java
     * annotations, which are Kotlin platform types, are used.
     */
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

// spotless static analyzer
configure<com.diffplug.gradle.spotless.SpotlessExtension> {
  kotlin {
    ktfmt()
    ktlint()
  }

  kotlinGradle {
    target("*.gradle.kts")
    ktfmt()
  }
}

// "net.researchgate.release" configuration
release {
  with(git) {
    pushReleaseVersionBranch.set("release")
    requireBranch.set("main")
  }
}

configurations { compileOnly { extendsFrom(configurations.annotationProcessor.get()) } }

tasks.named<Test>("test") {
  // Use JUnit Platform for unit tests.
  useJUnitPlatform()
  // WARNING: If a serviceability tool is in use, please run with
  // -XX:+EnableDynamicAgentLoading to hide this warning
  jvmArgs("-XX:+EnableDynamicAgentLoading")
}

// Spring Boot bootRun task
tasks.named<BootRun>("bootRun") {
  // The main function declared inside the package containing the file
  // "StrutsApp.kt" is compiled into static methods of a Java class named
  // StrutsAppKt
  mainClass.set("com.rubensgomes.helloworld.AppKt")
}

dependencies {
  // ########## compileOnly ####################################################
  compileOnly("jakarta.servlet:jakarta.servlet-api")

  // ########## implementation #################################################
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.apache.commons:commons-lang3")
  // required by Spring Boot:
  implementation(ctlg.kotlin.reflect)
  implementation(ctlg.kotlin.stdlib)
  implementation(ctlg.jackson.module.kotlin)

  // ########## providedRuntime ################################################
  providedRuntime("org.apache.tomcat.embed:tomcat-embed-jasper")
  providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")

  // ########## runtimeOnly ####################################################
  runtimeOnly("org.springframework.boot:spring-boot-devtools")

  // ########## testImplementation #############################################
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}
