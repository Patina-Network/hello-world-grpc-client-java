set shell := ["bash", "-euo", "pipefail", "-c"]

default:
    @just --list

run *args:
    HELLO_WORLD_CLIENT_URLS=http://localhost:8081,http://localhost:8082 \
    mvn spring-boot:run -Dspring-boot.run.profiles=local {{ args }}

test *args:
    mvn verify {{ args }}

lint:
    mvn spotless:check checkstyle:check test-compile

fmt:
    mvn spotless:apply

docker-build tag="hello-world-grpc-client-java":
    docker build -t {{ tag }} .
