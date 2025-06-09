# ECS

This file describes how to set up an ECS to have tasks running docker containers.

## Prerequisites

1. Ensure you have a default VPC and subnets setup
    - VPC ID: vpc-045218fadf49af3a0
2. Ensure you have a default Security Group
3. [Local computer public IP](https://checkip.amazonaws.com/)
4. Create a key-pair in the us-east-1 region
5. Create an ECS cluster -- I had to use the root user!!!
6. Create an ECS task definition
7. Use Spring Boot SERVER_PORT to define the container port to use

   ```shell
   SERVER_PORT="5000"
   declare -rx SERVER_PORT   
   ```
   
8. [Learn how to create an Amazon ECS Linux task for the Fargate launch type](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/getting-started-fargate.html)