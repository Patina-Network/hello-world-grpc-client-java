/**
 * See https://github.com/Patina-Network/hello-world-grpc-client-java/blob/main/.github/scripts/src/test/index.ts
 * for test exclusion usages.
 */

const baseDir = "src/main/java/org/patinanetwork/helloworld";

export const exclusions = [
  `${baseDir}/configuration/**`,
  `${baseDir}/controller/version/**`,
  `${baseDir}/dto/**`,
  `${baseDir}/utilities/exception/**`,
];
