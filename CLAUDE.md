# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A minimal "Hello World" starter: Vaadin Flow UI written in Kotlin with the [Karibu-DSL](https://github.com/mvysny/karibu-dsl),
launched from a plain `main()` via [Vaadin Boot](https://github.com/mvysny/vaadin-boot) (embedded Jetty — no Spring, no
servlet container, no WAR). Its Gradle twin lives at
[karibu-helloworld-application](https://github.com/mvysny/karibu-helloworld-application); keep the two in sync where it
makes sense.

Because it is a starter, the code is *documentation*: the comments in `MainView.kt` and `MainViewTest.kt` teach the reader
how Karibu-DSL and Karibu-Testing work. Don't strip them as "obvious" — they are the point.

## Architecture

Read the `cop` skill (`~/.claude/skills/cop/SKILL.md`) before designing components or app structure.

- **Views are self-sufficient components.** `MainView` extends `KComposite` — the UI tree is built declaratively in a
  `private val root = ui { ... }` initializer, and behaviour (listeners) is attached in a separate `init {}` block. New
  views follow the same shape: one class per view, `@Route`-annotated, no controller/presenter layer.
- **Routing is annotation-driven.** `@Route("")` on `MainView` is the only registration; Vaadin scans for it. There is no
  route table to update.
- **`AppShell`** (in `Main.kt`) carries `@PWA` and the `@StyleSheet` declarations — this is where app-wide theming and
  shell config go.
- **`fun main()` in `Main.kt`** just calls `VaadinBoot().run()`. Jetty, port, and shutdown handling come from Vaadin Boot;
  see its README for env vars (`-Dserver.port`, etc.).
- **Static files** are served from `src/main/resources/webapp/` (that is Vaadin Boot's webroot). `webapp/ROOT` is a marker
  file that must not be deleted — Vaadin Boot locates the webroot by finding it on the classpath. `styles.css` lives here
  and is pulled in by `@StyleSheet("styles.css")`.

### Class index

- `org.vaadin.example.Main` (`Main.kt`) — `AppShell` config class + `main()` that boots embedded Jetty.
- `org.vaadin.example.MainView` — the `@Route("")` view: a name field and a greeting button.
- `org.vaadin.example.MainViewTest` — browserless UI test; the template for all further tests.

## Build layout quirks

- **Kotlin sources live under `src/main/java/` and `src/test/java/`**, not `src/main/kotlin/`. The `kotlin-maven-plugin`
  is configured to compile them there. Put new `.kt` files in the `java` directories.
- **`kotlin-maven-plugin` must stay declared *before* `vaadin-maven-plugin`** in `pom.xml` — see
  [flow#20246](https://github.com/vaadin/flow/issues/20246). Don't reorder the `<plugins>` block.
- **The main class is `org.vaadin.example.MainKt`** (the JVM name Kotlin generates for `Main.kt`'s file-level `main`). It
  is hard-coded in three places: `exec-maven-plugin` config in `pom.xml`, `src/main/scripts/app`, and implicitly the
  Dockerfile entrypoint. Renaming `Main.kt` breaks all of them.
- **`src/main/frontend/generated/` is generated and gitignored**; `src/main/bundles/prod.bundle` is committed on purpose
  (the pre-built production bundle lets most builds skip npm entirely).
- **There is no `production` profile** even though CI passes `-Pproduction`. `vaadin-maven-plugin:build-frontend` is bound
  unconditionally, so *every* `package`/`install` is a production build; the flag is a harmless no-op left over from the
  Vaadin starter. Maven prints a warning about it.

## Commands

```bash
./mvnw test                                     # run all tests (does NOT build the frontend)
./mvnw test -Dtest='MainViewTest#smoke test'    # single test; quote it — Kotlin backtick names contain spaces
./mvnw -C exec:java                             # run the app -> http://localhost:8080
./mvnw clean package                            # full build incl. build-frontend; produces target/*.zip and *.tar.gz
./mvnw -o ...                                   # offline; fine for test-only loops, saves a lot of time
```

`build-frontend` is bound to `prepare-package`, so the test/compile loop never touches node or npm — only `package` and
`install` do.

### Distribution & Docker

`maven-assembly-plugin` (descriptor `src/main/assembly/zip.xml`) packages `lib/` + the `bin/app` launcher from
`src/main/scripts/` into a zip and a tar.gz, deliberately excluding `com.vaadin:vaadin-dev`. The multi-stage `Dockerfile`
builds, untars that artifact, and runs `./bin/app` on a clean JRE image:

```bash
docker build -t test/karibu-helloworld-application-maven:latest .
docker run --rm -ti -p8080:8080 test/karibu-helloworld-application-maven
```

## Testing

Read the `karibu-testing` skill before writing or changing tests.

Tests are browserless — no Selenium, no browser. `MainViewTest` is the canonical shape: `Routes().autoDiscoverViews(...)`
once in `@BeforeAll` (route scanning is slow), then `MockVaadin.setup(routes)` / `MockVaadin.tearDown()` per test. Interact
through the lookup DSL (`_get`, `_click`, `_value`, `expectNotifications`) rather than reaching into the component tree by
hand.

## Maintenance

This repo's history is almost entirely dependency bumps — its job is to stay current with Vaadin, Kotlin, Karibu and
Vaadin Boot releases. Versions are centralised in `pom.xml` `<properties>` (`vaadin.version`, `kotlin.version`,
`junit.jupiter.version`); Karibu-DSL, Karibu-Testing and Vaadin Boot versions are inline on their `<dependency>` elements.
Check current versions against Maven Central with the `maven-tools` MCP rather than from memory, and follow the existing
commit-message style (`vaadin bump to 25.2.6`).
