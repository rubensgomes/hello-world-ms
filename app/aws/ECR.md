# ECR

This file describes how to create and push a docker image to a private
ECR registry,

## Prerequisites

- Must be able to sign in to the AWS console using an AWS "root" access account

1. Provision the "root" account in IAM Identity Center:
    - Enable IAM Identiry Center using "organization instances"
    - Set up an "AdministratorAccess" permission set
    - Create an "admin" group in the IAM Identity Center
    - Assign the "AdministratorAccess" permission set to the "admin' group
    - Assign the "admin" group and "AdministratorAccess" permission set to the
      "root management" account

2. Provision an administrator user account
    - Create a user account in IAM Identity Center
    - Ensure user account is in the "admin" group
    - Ensure the "admin" group has the "AdministratorAccess" permission set

3. [Installing the latest version of the AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)

## Create an ECR repository

- Create a *private* repository in the ECR private registry

    ```shell
    repository="rubensgomes/helloworld-ms"
    aws ecr create-repository \
      --repository-name "${repository}"  \
      --region us-east-1 \
      --profile admin
    ```

## Push a docker image to AWS ECR registry

- Sign in to AWS ECR registry account

   ```shell
   aws sso login --profile admin
   # Rubens' personal account id: 347851559421
   registry="347851559421.dkr.ecr.us-east-1.amazonaws.com"
   aws ecr get-login-password --region us-east-1 --profile admin \
     | docker login --username AWS --password-stdin ${registry}
   ```

- Tag image to push to ECR private registry repository

   ```shell
   cd ..
   # extract version from app-x.y.z.jar (e.g., version=x.y.z)
   version="$(ls build/layer/*.jar | awk -F- '{print $2}' | awk -F.jar '{print $1}')"
   registry="347851559421.dkr.ecr.us-east-1.amazonaws.com"
   repository="rubensgomes/helloworld-ms"
   docker image tag ${repository}:${version} \
      ${registry}/${repository}:${version} || {
     printf "failed to tag image\n"
     sleep 5
   }
   ```

- Push image to ECR private registry repository

  ```shell
  cd ..
  # extract version from app-x.y.z.jar (e.g., version=x.y.z)
  version="$(ls build/layer/*.jar | awk -F- '{print $2}' | awk -F.jar '{print $1}')"
  registry="347851559421.dkr.ecr.us-east-1.amazonaws.com"
  repository="rubensgomes/helloworld-ms"
  printf "pushing image to ECR registry: %s\n" "${registry}"
  docker image push "${registry}/${repository}:${version}" || {
    printf "failed to push image\n" >&2
    sleep 5
  }
  ```

- Pull image from ECR private registry repository

  ```shell
  cd ..
  # extract version from app-x.y.z.jar (e.g., version=x.y.z)
  version="$(ls build/layer/*.jar | awk -F- '{print $2}' | awk -F.jar '{print $1}')"
  registry="347851559421.dkr.ecr.us-east-1.amazonaws.com"
  repository="rubensgomes/helloworld-ms"
  printf "pulling image from ECR registry: %s\n" "${registry}"
  docker image pull "${registry}/${repository}:${version}" || {
    printf "failed to pull image\n" >&2
    sleep 5
  }
  ```

- To remove image from local registry

  ```shell
  cd ..
  # extract version from app-x.y.z.jar (e.g., version=x.y.z)
  version="$(ls build/layer/*.jar | awk -F- '{print $2}' | awk -F.jar '{print $1}')"
  registry="347851559421.dkr.ecr.us-east-1.amazonaws.com"
  repository="rubensgomes/helloworld-ms"
  printf "removing image from local registry repository\n"
  docker image rm "${registry}/${repository}:${version}" || {
    printf "failed to remove image\n" >&2
    sleep 5
  }
  ```

- To remove image from ECR private registry repository

  ```shell
  cd ..
  # extract version from app-x.y.z.jar (e.g., version=x.y.z)
  version="$(ls build/layer/*.jar | awk -F- '{print $2}' | awk -F.jar '{print $1}')"
  registry="347851559421.dkr.ecr.us-east-1.amazonaws.com"
  repository="rubensgomes/helloworld-ms"
  aws ecr batch-delete-image \
    --repository-name ${repository} \
    --image-ids imageTag=${version} \
    --region us-east-1 \
    --profile admin
  ```

- To delete ECR private registry repository

  ```shell
  registry="347851559421.dkr.ecr.us-east-1.amazonaws.com"
  repository="rubensgomes/helloworld-ms"
  aws ecr delete-repository \
    --repository-name ${repository} \
    --force \
    --region us-east-1 \
    --profile admin
  ```

---
Author:  [Rubens Gomes](https://rubensgomes.com/)
