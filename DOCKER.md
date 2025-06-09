# docker

This file describes the steps to build/push a containerized image of the
application to a local and remote Docker registry.

## Docker Containerized Image Best Practices

- Minimum # of dependencies in the built artifact
- Layered build separating dependency libraries from application code
- Labels to identify version, author, title and others
- Remove tools used only during create/add user/group
- Use a non-root user to limit root access

## Further Information

- [Design Principles](https://docs.aws.amazon.com/wellarchitected/latest/container-build-lens/design-principles.html)
- [Best practices for Amazon ECS container images](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/container-considerations.html)
- [Docker Best Practices](https://www.docker.com/blog/tag/best-practices/)

## Create a Spring Boot layered build

1. Create a fat bootJar of the application:

    ```shell
    ./gradlew --info clean
    (./gradlew --info bootJar) || {
      printf "failed to run bootJar\n" >&2
      sleep 3
    }
    ```

2. Break the fat bootJar into layers:

    ```shell
    cd app/build/libs
    # extract version from build/libs/app-x.y.z.jar (e.g., version=x.y.z)
    version="$(ls *.jar | awk -F- '{print $2}' | awk -F.jar '{print $1}')"
    rm -fr ../layer
    java -Djarmode=tools \
      -jar app-${version}.jar extract \
      --destination ../layer || {
      printf "failed to extract bootJar into layers.\n" >&2
      sleep 3
    }
    ```

## Build Spring Boot layered docker image

- to build a Spring Boot layered docker image:

  ```shell
  cd app
  # extract version from app-x.y.z.jar (e.g., version=x.y.z)
  version="$(ls build/layer/*.jar | awk -F- '{print $2}' | awk -F.jar '{print $1}')"
  datetime="$(date -u '+%Y-%m-%dT%H:%M:%S.%NZ')"
  repository="rubensgomes/helloworld-ms"
  printf "building image using version: %s\n" "${version}"
  docker image build \
    --build-arg "BUILD_DATE=${datetime}" \
    --build-arg "VERSION=${version}" \
    --tag "${repository}:${version}" \
    . || {
    printf "failed to create image\n" >&2
    sleep 5
  }
  ```

## Push docker image to Rubens' DockerHub registry:

- Sign in to DockerHub registry account

  ```shell
  # only Rubens can push images below
  printf "signing in to DockerHub\n"
  docker login --username "rubensgomes" || {
    printf "failed to login\n" >&2
    sleep 5
  }
  ```

- Tag image to push to docker.io registry repository

   ```shell
   cd app
   # extract version from app-x.y.z.jar (e.g., version=x.y.z)
   version="$(ls build/layer/*.jar | awk -F- '{print $2}' | awk -F.jar '{print $1}')"
   registry="docker.io"
   repository="rubensgomes/helloworld-ms"
   docker image tag ${repository}:${version} \
      ${registry}/${repository}:${version} || {
     printf "failed to tag image\n"
     sleep 5
   }
   ```

- Push image to DockerHub registry repository

  ```shell
  cd app
  # extract version from app-x.y.z.jar (e.g., version=x.y.z)
  version="$(ls build/layer/*.jar | awk -F- '{print $2}' | awk -F.jar '{print $1}')"
  printf "pushing image to DockerHub\n"
  docker image push "rubensgomes/helloworld-ms:${version}" || {
    printf "failed to push image\n" >&2
    sleep 5
  }
  ```

- Pull image from DockerHub registry repository

  ```shell
  cd app
  # extract version from app-x.y.z.jar (e.g., version=x.y.z)
  version="$(ls build/layer/*.jar | awk -F- '{print $2}' | awk -F.jar '{print $1}')"
  printf "pulling image from DockerHub\n"
  docker image pull "rubensgomes/helloworld-ms:${version}" || {
    printf "failed to pull image\n" >&2
    sleep 5
  }
  ```

- To remove image from local registry

  ```shell
  cd app
  # extract version from app-x.y.z.jar (e.g., version=x.y.z)
  version="$(ls build/layer/*.jar | awk -F- '{print $2}' | awk -F.jar '{print $1}')"
  printf "removing image from local registry repository\n"
  docker image rm "rubensgomes/helloworld-ms:${version}" || {
    printf "failed to remove image\n" >&2
    sleep 5
  }
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
