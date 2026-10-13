# OSDU Workflow Service for Azure

[![Release](https://img.shields.io/github/v/release/Azure/osdu-spi-workflow)](https://github.com/Azure/osdu-spi-workflow/releases)
[![Validate](https://github.com/Azure/osdu-spi-workflow/actions/workflows/validate.yml/badge.svg?branch=main)](https://github.com/Azure/osdu-spi-workflow/actions/workflows/validate.yml)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

> [!NOTE]
> Shared service code comes from the [OSDU community upstream](https://community.opengroup.org/osdu/platform/data-flow/ingestion/ingestion-workflow); the [OSDU documentation](https://osdu.pages.opengroup.org/platform/data-flow/ingestion/ingestion-workflow/) covers the API.

Workflow registers ingestion workflows, starts each run as an Airflow DAG run, and tracks the run's status so callers can follow an ingestion from trigger to finish.

## At a glance

| | |
|---|---|
| API base path | `/api/workflow/v1/` |
| Swagger UI | `/api/workflow/swagger` |
| Health | `:8081/actuator/health` |
| Depends on | Partition, Entitlements, Airflow |
| Azure resources | Cosmos DB (workflow metadata and runs, plus the system database for system workflows), Storage (containers shared between the tasks of a run), Service Bus (`statuschangedtopic`), Redis (cache) |
| Deployed by | [OSDU SPI Stack](https://github.com/Azure/osdu-spi-stack) (`software/stacks/osdu/services/workflow.yaml`) |

## Repository layout

[CONTRIBUTING.md](CONTRIBUTING.md) explains where each kind of change belongs.

| Path | Owner | Contents |
|---|---|---|
| `workflow-core/` | OSDU upstream | Shared service code |
| `provider/workflow-azure/` | This repository | Azure provider |
| `workflow-acceptance-test/` | OSDU upstream | End-to-end suite run against a deployed environment |
| `testing/workflow-test-core/` | OSDU upstream | Shared integration test framework |
| `testing/workflow-test-azure/` | This repository | Legacy Azure integration tests |
| `.spi/service.yaml` | This repository | How CI deploys and tests the service on SPI Stack |

## Build

Requires Java 17 and Maven 3.6.3+. OSDU dependencies resolve from the public community registry through the settings file in `.mvn`:

```bash
mvn --settings .mvn/community-maven.settings.xml -P core,azure clean install
```

The runnable jar lands at `provider/workflow-azure/target/workflow-azure-*-spring-boot.jar`.

## Configuration

SPI Stack sets the service's environment from two places: the shared `osdu-config` ConfigMap and the service's own entry in [`services/workflow.yaml`](https://github.com/Azure/osdu-spi-stack/blob/main/software/stacks/osdu/services/workflow.yaml). Those files are the contract; the tables below list what Workflow actually reads from them.

**Shared, from `osdu-config`:**

| Variable | Purpose |
|---|---|
| `AAD_CLIENT_ID` | Application ID that caller tokens are issued for |
| `KEYVAULT_URI` | Central Key Vault |
| `SERVER_PORT` | HTTP port (`8080`) |
| `REDIS_PORT` | Redis port (`6379`) |
| `APPINSIGHTS_KEY` | Telemetry |

**Specific to Workflow**, from `services/workflow.yaml`:

| Variable and value on SPI Stack | Purpose |
|---|---|
| `SERVER_SERVLET_CONTEXTPATH`<br>`/api/workflow/` | API base path |
| `SPRING_APPLICATION_NAME`<br>`workflow` | Application name |
| `SPRING_CONFIG_NAME`<br>`common,application` | Load the Azure core library's `common.properties` with the service's own properties |
| `AZURE_ISTIOAUTH_ENABLED`<br>`true` | Trust the mesh's token validation |
| `AZURE_PAAS_WORKLOADIDENTITY_ISENABLED`<br>`true` | Authenticate to Azure with workload identity |
| `PARTITION_SERVICE_ENDPOINT`<br>`http://partition/api/partition/v1` | Per-partition resource lookup |
| `OSDU_ENTITLEMENTS_URL`<br>`http://entitlements/api/entitlements/v2` | Caller authorization |
| `OSDU_ENTITLEMENTS_APPKEY`<br>`OBSOLETE` | Legacy key passed to the Entitlements client |
| `COSMOSDB_DATABASE`<br>`osdu-db` | Database inside each partition's Cosmos DB account |
| `COSMOSDB_SYSTEM_DATABASE`<br>`osdu-system-db` | System database for system workflows |
| `OSDU_AIRFLOW_URL`<br>`http://airflow-api-server.platform.svc.cluster.local:8080/airflow` | Airflow endpoint for partitions without their own Airflow |
| `DP_AIRFLOW_FOR_SYSTEM_DAG`<br>`false` | Run system workflows on the default Airflow, not a partition's own |
| `IGNORE_DAGCONTENT`<br>`true` | Refuse DAG content in workflow registrations |
| `IGNORE_CUSTOMOPERATORCONTENT`<br>`true` | Refuse operator content in custom operator registrations |

The service authenticates to Azure with workload identity, which injects `AZURE_CLIENT_ID` and a federated token; there are no client secrets. Per-partition resources are resolved at request time through the Partition service. The Redis host and password come from the Key Vault secrets `redis-hostname` and `redis-password`, and the system Cosmos DB endpoint from `system-cosmos-endpoint`.

## Test

| Suite | Where | Runs in CI | Run it yourself |
|---|---|---|---|
| Unit | `workflow-core`, `provider/workflow-azure` | Pull requests (Java Build) | `mvn ... install` from [Build](#build) |
| Acceptance | [`workflow-acceptance-test`](workflow-acceptance-test/docs/README.md) | Pull requests, against SPI Stack (Deploy and Test) | `spi test workflow` |
| Integration | `testing/workflow-test-core`, `testing/workflow-test-azure` | No | See below |

CI runs these on pull requests from this repository that change code. Documentation-only changes skip the build, and pull requests from forks build without deploying.

**Acceptance** proves a change on real infrastructure before it merges. It calls the deployed service through the gateway as a privileged test identity, and the bindings in `.spi/service.yaml` supply its inputs. Against an environment you are connected to:

```bash
spi test workflow                   # the image and suite the environment is running
spi test workflow --source .        # this checkout's suite and descriptor
```

**Integration** is the older suite carried from upstream. It sits outside the root Maven build and CI does not run it. It mints its own tokens from service principal client secrets (`INTEGRATION_TESTER` with `TESTER_SERVICEPRINCIPAL_SECRET`, plus a no-access principal), so `spi token` cannot supply them, and it has not been proven against SPI Stack. Acceptance covers the same API surface.

To call the API by hand, `spi token` mints a bearer token:

```bash
curl -H "Authorization: Bearer $(spi token)" -H "data-partition-id: <partition>" \
  https://<gateway>/api/workflow/v1/workflow
```

## Deploy

For a pull request from this repository that changes code, CI publishes the service image and its test suite image, `osdu-spi-workflow-acceptance`, to GHCR, and the Deploy and Test lane borrows an SPI Stack environment, runs the new image there, proves it with the acceptance suite, and restores the environment's own image. When that lane runs and passes, the change is proven on real infrastructure before it merges; the Validation Summary on the pull request shows whether it ran. This repository does not own infrastructure; SPI Stack does.

To try a build by hand on an environment you are connected to, pin it by digest and release the pin when done:

```bash
spi service pin workflow --image ghcr.io/azure/osdu-spi-workflow@sha256:<digest>
spi service reset workflow
```

## Service notes

**Airflow version.** SPI Stack runs Airflow 3.2.2 in the `platform` namespace, and `OSDU_AIRFLOW_URL` points the service at its API server. The provider picks its Airflow client from `OSDU_AIRFLOW_VERSION`, which defaults to `airflow2`: the `api/v1` REST API with basic auth from `OSDU_AIRFLOW_USERNAME` and `OSDU_AIRFLOW_PASSWORD`. The Airflow 3 client (`api/v2` with JWT auth) runs only when `OSDU_AIRFLOW_VERSION` is `airflow3` and `OSDU_AIRFLOW_AIRFLOW3_URL`, `OSDU_AIRFLOW_AIRFLOW3_USERNAME`, and `OSDU_AIRFLOW_AIRFLOW3_PASSWORD` are set. The stack file sets none of these. The `OSDU_AIRFLOW_VERSION2_ENABLED` it does set is a legacy toggle this provider does not read.

**DAG content.** With `IGNORE_DAGCONTENT` on, registering a workflow whose `registrationInstructions` carry `dagContent` fails with `403`. The service stores only the workflow's metadata, so the DAG must already be deployed to Airflow. `IGNORE_CUSTOMOPERATORCONTENT` does the same for custom operators.

**Active run limit.** For partitions that use the default Airflow, the service refuses new runs with `429 Too Many Requests` once the active DAG run count reaches 50000. The count is cached in Redis, and the limit is `osdu.azure.active-dag-runs.threshold` in `application.properties`.

## License

Copyright © Microsoft Corporation

Licensed under the [Apache License 2.0](LICENSE).
