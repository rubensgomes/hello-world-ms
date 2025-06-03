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

### To run Spotless static analysis

```shell
./gradlew :app:spotlessApply
```

### To run unit tests

```shell
./gradlew --info check
```

### To assemble a Spring Boot war

```shell
./gradlew --info assemble
```

### To create a Spring Boot executable war

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

### To render the `Hello World!` response

```http request
http://localhost:8080/index.do
```

### To create a realease (only Rubens Gomes)

```shell
./gradlew --info release
```
