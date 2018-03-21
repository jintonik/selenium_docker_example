# Buggy Test App

## How to build

|What               |Command
|---                |---
|Build distribution | `gradle assemble -Pbuild=<build number>`
|Run tests          | `gradle e2eTest -Pbuild=<build number> -Ptarget.url=<tested site url>`
