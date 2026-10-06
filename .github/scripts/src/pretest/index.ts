import { $ } from "bun";

async function main() {
  await $`mvn -B -ntp spotless:check checkstyle:check test-compile`;
}

void main();
