plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()

        // Rubens personal maven repository
        maven {
            url = uri("https://repo.repsy.io/mvn/rubensgomes/default/")
        }
    }

    versionCatalogs {
        create("ctlg") {
            from("com.rubensgomes:gradle-catalog:0.0.35")
        }
    }
}

rootProject.name = "hello-world-ms"
include("app")
