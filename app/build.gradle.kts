import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
  id("idea")
  id("maven-publish")
  id("version-catalog")
  // org.jetbrains.kotlin.jvm
  alias(ctlg.plugins.kotlin.jvm)
  // org.jetbrains.kotlin.plugin.spring
  alias(ctlg.plugins.kotlin.spring)
  // net.researchgate.release
  alias(ctlg.plugins.release)
  // com.diffplug.spotless
  alias(ctlg.plugins.spotless)
  // org.springframework.boot
  alias(ctlg.plugins.spring.boot)
  // io.spring.dependency-management
  alias(ctlg.plugins.spring.dependency.management)
}

val developerId: String by project
val developerName: String by project

val group: String by project
val artifact: String by project
val version: String by project
val title: String by project
val description: String by project

project.group = group

project.version = version

project.description = description

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

configurations { compileOnly { extendsFrom(configurations.annotationProcessor.get()) } }

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

tasks.jar {
  manifest {
    attributes(
        mapOf(
            "Specification-Title" to title,
            "Implementation-Title" to artifact,
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to developerName,
            "Built-By" to developerId,
            "Build-Jdk" to System.getProperty("java.home"),
            "Created-By" to
                "${System.getProperty("java.version")} (${System.getProperty("java.vendor")})"))
  }
}

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

  // ########## implementation #################################################
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  // io.swagger.core.v3:swagger-annotations
  implementation(ctlg.swagger.annotations)
  // jakarta.validation:jakarta.validation-api
  implementation(ctlg.jakarta.validation.api)

  // org.jetbrains.kotlin:kotlin-reflect
  //   -  required by Spring Boot to introspect the code  at runtime
  implementation(ctlg.kotlin.reflect)
  // org.jetbrains.kotlin:kotlin-stdlib
  implementation(ctlg.kotlin.stdlib)
  // com.fasterxml.jackson.module:jackson-module-kotlin
  //  - used to serialize/de-serialize kotlin object
  //   * not used in this project
  //  implementation(ctlg.jackson.module.kotlin)

  // ########## developmentOnly #################################################
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  developmentOnly("org.springframework.boot:spring-boot-docker-compose")

  // ########## testImplementation #############################################
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  // io.mockk:mockk
  //   - mock function (e.g., every {...}) during unit testing
  testImplementation(ctlg.mockk)
  // com.ninja-squad:springmockk
  //   - mock Spring Beans (e.g., @MockBean) during unit testing
  testImplementation(ctlg.springmockk)
  // bundle engine that implements the jakarta API validation
  testImplementation(ctlg.bundles.jakarta.bean.validator)
}
