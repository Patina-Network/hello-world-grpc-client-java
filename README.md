# hello-world-grpc-client-java

Hello World gRPC Java client for Patina Network

The UI is a single `frontend/index.html` (Tailwind from a CDN, no build step), packaged into the jar at build time and served by the backend.

## Prerequisites

- JDK 26 or newer [(brew.sh)](https://formulae.brew.sh/formula/openjdk)
- Maven [(brew.sh)](https://formulae.brew.sh/formula/maven)
- just [(brew.sh)](https://formulae.brew.sh/formula/just)
- Tailscale, connected to the Patina VPN [(brew.sh)](https://formulae.brew.sh/cask/tailscale-app)
- Docker, only for `just docker-build` [(brew.sh)](https://formulae.brew.sh/cask/docker-desktop)

The Patina VPN is required to download the `hello-world-grpc-service` client library and to reach the staging gRPC service.

> [!NOTE]
> You must be connected to the VPN to connect locally. You can find the instructions to connect at <https://docs.patinanetwork.org/infra/how-to-connect-to-vpn/>

## Development

```sh
just run            # run the backend + UI on :8080 against the staging gRPC service
just test           # unit tests and coverage report
just lint           # spotless, checkstyle, and compile
just fmt            # apply formatting
just docker-build
```

## Environment variables

| Variable                              | Default                          | Description                                     |
| ------------------------------------- | -------------------------------- | ----------------------------------------------- |
| `HELLO_WORLD_SERVICE_GRPC_HOST`       | `hello-world-grpc-service:50051` | `host:port` of the hello-world gRPC service     |
| `HELLO_WORLD_SERVICE_GRPC_TLS`        | `false`                          | Connect to the gRPC service over TLS            |
| `HELLO_WORLD_SERVICE_GRPC_TIMEOUT_MS` | `3000`                           | Deadline for each gRPC call, in milliseconds    |
| `HTTP_HOST`                           | `0.0.0.0`                        | Address the HTTP server binds to                |
| `HTTP_PORT`                           | `8080`                           | Port the HTTP server listens on                 |
| `VERSION`                             | `N/A`                            | Version string returned by `GET /version`       |
| `HELLO_WORLD_CLIENT_URLS`             | none                             | Comma-separated URLs returned by `GET /urls` and listed in the UI |
| `ENVIRONMENT`                         | none                             | `production` or `staging` switches logs to JSON (ECS); anything else logs human-readable |
| `SPRING_PROFILES_ACTIVE`              | none                             | Set to `local` to use `application-local.yaml`  |

`just run` uses the `local` Spring profile (`src/main/resources/application-local.yaml`), which points at staging over TLS.

## Releases

On `main`, CI creates an unprefixed semantic-version tag after both image builds succeed, using GitHub App credentials. Tags start at `1.0.0` and increment the patch version; source package versions are unchanged.

The tag triggers CD. Because the tagging step creates a new commit, CD promotes images built from its parent commit to the release tag and `latest`, then deploys production using the release tag. CI deploys only staging; production deployment runs only in CD.
