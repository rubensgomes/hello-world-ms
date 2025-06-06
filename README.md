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

## Build and push docker image

- to build a docker image:

  ```shell
  # must add the version being built below
  version="0.0.3"
  cd app

  printf "building image using version: %s\n" "${version}"
  docker image build \
    --build-arg "VERSION=${version}" \
    --tag "rubensgomes/helloworld-ms:${version}" \
    . || exit
  ```

- to push docker image:

  ```shell
  # only Rubens can push images below
  printf "signing in to DockerHub\n"
  docker login --username "rubensgomes" || exit
  ```

  ```shell
  # must add the version being built below
  version="0.0.3"
  printf "pushing image to DockerHub\n"
  docker image push "rubensgomes/helloworld-ms:${version}"
  ```

- To remove image from local registry:

  ```shell
  version="0.0.3"
  printf "removing image from local registry\n"
  docker image rm "rubensgomes/helloworld-ms:${version}"
  ```

## Start and stop using docker compose

- Start docker container:

  ```shell
  cd app
  docker compose up --detach --no-recreate --remove-orphans || {
    printf "failed to stop container.\n" >&2
    sleep 5   
  }
  ```

- Stop docker container:

  ```shell
  cd app
  docker compose down --remove-orphans || {
    printf "failed to stop container.\n" >&2
    sleep 5
  }
  ```

- To render the `Hello World!` messasge:

  ```shell
  curl --verbose "http://localhost:8080/api/v1/helloworld"
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

---
Author:  [Rubens Gomes](https://rubensgomes.com/)
