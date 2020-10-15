# Hello, Vaadin 👋

A simple "Hello, World" application in Vaadin and Kotlin.

This project can be used as a starting point to create your own Vaadin application.
It has the necessary dependencies and files to help you get started.

There is also a [getting started tutorial](https://vaadin.com/tutorials/getting-started-with-flow) based on this project.

To access it directly from github, clone the repository and import the project to
the IDE of your choice as a Maven project. You need to have Java 8 or 11 installed.

> Note: this example project uses Maven. For Gradle-based example project please visit
> [karibu10-helloworld-application](https://github.com/mvysny/karibu10-helloworld-application).

# Preparing Environment

The Vaadin 14 build requires `node.js` and `npm`. The Vaadin Maven plugin will install it for
you automatically (handy for the CI), or you can install it manually into your operating system:

* Windows: [node.js Download site](https://nodejs.org/en/download/) - use the .msi 64-bit installer
* Linux: `sudo apt install npm`

Also make sure that you have Java 8 (or higher) JDK installed.

## Getting Started

To quickly start the app, just type this into your terminal:

```bash
git clone https://github.com/mvysny/karibu10-helloworld-application-maven
cd karibu10-helloworld-application-maven
mvn jetty:run
```

Maven will automatically download an embedded servlet container (Jetty) and will run your app in it. Your app will be running on
[http://localhost:8080](http://localhost:8080).

If you want to run your app locally in the production mode, run `mvn jetty:run -Pproduction`.

For a full Vaadin application example, there are more choices available also from [vaadin.com/start](https://vaadin.com/start) page.

## Kotlin

This is a port of [Skeleton Starter Flow](https://github.com/vaadin/skeleton-starter-flow) to Kotlin.

Please see [Vaadin Kotlin page](https://vaadin.com/kotlin) for more information.
