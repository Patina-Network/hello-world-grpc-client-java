import { SonarScannerClient } from "@tahminator/pipeline";
import { $ } from "bun";

import { exclusions } from "../../../../exclusions";
import { SONAR_ORGANIZATION, SONAR_PROJECT_KEY } from "../consts";

async function main() {
  const { sonarToken } = parseCiEnv(process.env);

  const sonarClient = new SonarScannerClient({
    auth: {
      token: sonarToken,
    },
    scan: {
      additionalArgs: {
        "java.binaries": "target/classes",
        "java.test.binaries": "target/test-classes",
        tests: "src/test/java",
        "coverage.jacoco.xmlReportPaths": "target/site/jacoco/jacoco.xml",
        "coverage.exclusions": `${exclusions}`,
      },
      organization: SONAR_ORGANIZATION,
      sourceCodeDir: "src/main/java",
      projectKey: SONAR_PROJECT_KEY,
    },
    run: {
      runTestsCmd: $`mvn -B -ntp verify`,
    },
  });

  await sonarClient.runTests();
  await sonarClient.uploadTestCoverage();
}

function parseCiEnv(ciEnv: Record<string, string | undefined>) {
  const sonarToken = (() => {
    const v = ciEnv["SONAR_TOKEN"];
    if (!v) {
      throw new Error("Missing SONAR_TOKEN from .env.ci");
    }
    return v;
  })();

  return { sonarToken };
}

void main();
