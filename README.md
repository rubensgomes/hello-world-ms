# hello-world-ms 
A basic Kotlin Spring Boot microservice.

- Requires Java LTS 21 or greater.

## Display Java Tools Installed

```shell
./gradlew -q javaToolchains
```

## Clean, Lint, Test, Assemble, Release

### To clean local build

```shell
./gradlew --info clean
```

```shell
./gradlew :app:spotlessApply
```

```shell
./gradlew --info build
```

```shell
./gradlew --info check
```

```shell
./gradlew --info jar
```

```shell
./gradlew --info assemble
```

```shell
./gradlew --info bootWar
```

### To run the Spring Boot webapp from IntelliJ

ATTENTION: You must select the Gradle task:
- `strutsapp > Tasks > application > bootRun`

### To run the Spring Boot webapp from the CLI

```shell
./gradlew --info bootRun
```

### To render the `Hello World!` messasge response

```shell
curl --verbose "http://localhost:8080/api/v1/helloworld"
```

### To create a realease (only Rubens Gomes)

```shell
# only Rubens can release
./gradlew --info release
```
---
Author:  [Rubens Gomes](https://rubensgomes.com/)
