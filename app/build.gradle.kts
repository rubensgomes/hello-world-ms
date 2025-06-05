import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
  id("idea")
  id("maven-publish")
  id("version-catalog")
  id("war")
  alias(ctlg.plugins.kotlin.jvm)
  alias(ctlg.plugins.kotlin.spring)
  alias(ctlg.plugins.release)
  alias(ctlg.plugins.spotless)
  alias(ctlg.plugins.spring.boot)
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

publishing {
  publications {
    val developerEmail: String by project

    val scmConnection: String by project
    val scmUrl: String by project

    val license: String by project
    val licenseUrl: String by project

    create<MavenPublication>("maven") {
      groupId = project.group.toString()
      artifactId = artifact
      version = project.version.toString()

      from(components["java"])

      pom {
        name = title
        description = project.description
        inceptionYear = "2024"
        packaging = "jar"

        licenses {
          license {
            name = license
            url = licenseUrl
          }
        }
        developers {
          developer {
            id = developerId
            name = developerName
            email = developerEmail
          }
        }
        scm {
          connection = scmConnection
          developerConnection = scmConnection
          url = scmUrl
        }
      }
    }
  }

  repositories {
    val repsyUrl: String by project
    val repsyUsername: String by project
    val repsyPassword: String by project

    maven {
      url = uri(repsyUrl)
      credentials {
        username = repsyUsername
        password = repsyPassword
      }
    }
  }
}

// "net.researchgate.release" configuration
release {
  with(git) {
    pushReleaseVersionBranch.set("release")
    requireBranch.set("main")
  }
}

// net.researchgate.release plugin task
tasks.afterReleaseBuild { dependsOn("publish") }

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
  implementation(ctlg.swagger.annotations)
  implementation(ctlg.jakarta.validation.api)

  // required by Spring Boot:
  implementation(ctlg.kotlin.reflect)
  implementation(ctlg.kotlin.stdlib)
  implementation(ctlg.jackson.module.kotlin)

  // ########## providedRuntime ################################################
  providedRuntime("org.apache.tomcat.embed:tomcat-embed-jasper")
  providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")

  // ########## developmentOnly #################################################
  developmentOnly("org.springframework.boot:spring-boot-devtools")

  // ########## testImplementation #############################################
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  // required to mock function returns (e.g., every {...}) during unit testing
  testImplementation(ctlg.mockk)
  // required to mock Spring Beans (e.g., @MockBean) during unit testing
  testImplementation(ctlg.springmockk)
  // required jakarta API validation engine during unit testing
  testImplementation(ctlg.bundles.jakarta.bean.validator)
}
