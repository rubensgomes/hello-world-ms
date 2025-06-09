# hello-world-ms

A basic Kotlin Spring Boot microservice.

- Requires Java LTS 21 or greater.

## Display Java tools installed

```shell
./gradlew -q javaToolchains
```

## Clean, lint, test, and assemble

```shell
./gradlew --info clean
```

```shell
./gradlew :app:spotlessApply
```

```shell
./gradlew --info check
```

```shell
./gradlew --info bootJar
```

### Start and stop using bootRun

- Start using "bootRun":

  ```shell
  ./gradlew --info bootRun
  ```

- Stop "bootRun"

  ```shell
  ./gradlew --stop
  ```

- To render the `Hello World!` messasge:

  ```shell
  curl --verbose "http://localhost:8080/api/v1/helloworld"
  ```

### To create a release

```shell
# only Rubens can release
./gradlew --info release
```

## Public API in AWS ECS

- Ensure cluster is setup
- Ensure task definition is setup (SERVER_PORT=8888)
- Ensure service is up and running
- Ensure security group allows proper inbound traffic to port 8888
- http://<ECS-TASK-PUBLIC_IP>:8888/api/v1/helloworld

---
Author:  [Rubens Gomes](https://rubensgomes.com/)
