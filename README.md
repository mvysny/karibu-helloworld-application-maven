# Skeleton Starter for Vaadin

This project can be used as a starting point to create your own Vaadin application.
It has the necessary dependencies and files to help you get started.

> Note: this example project uses Maven. For Gradle-based example project please visit
> [karibu10-helloworld-application](https://github.com/mvysny/karibu10-helloworld-application).

# Preparing Environment

The Vaadin 14 build requires node.js and npm. You can either use the Vaadin Maven plugin to install it for
you (TODO coming in Vaadin 14.2); the `TODO: vaadinPrepareNode` task, handy for the CI), or you can install it to your OS:

* Windows: [node.js Download site](https://nodejs.org/en/download/) - use the .msi 64-bit installer
* Linux: `sudo apt install npm`

To make Vaadin Maven plugin install node.js+npm for you, just run the following command
in the project's sources (you only need to run this command once):

```
mvn TODO vaadinPrepareNode
```

Also make sure that you have Java 8 (or higher) JDK installed.


The best way to use it is via [vaadin.com/start](https://vaadin.com/start) - you can get only the necessary parts and choose the package naming you want to use.
There is also a [getting started tutorial](https://vaadin.com/tutorials/getting-started-with-flow) based on this project.

To access it directly from github, clone the repository and import the project to the IDE of your choice as a Maven project. You need to have Java 8 or 11 installed.

## Getting Started

To quickly start the app, just type this into your terminal:

```bash
git clone https://github.com/mvysny/karibu10-helloworld-application-maven
cd karibu10-helloworld-application-maven
mvn jetty:run
```

Maven will automatically download an embedded servlet container (Jetty) and will run your app in it. Your app will be running on
[http://localhost:8080](http://localhost:8080).

> This is a port of [Skeleton Starter Flow](https://github.com/vaadin/skeleton-starter-flow) to Kotlin.

If you want to run your app locally in the production mode, run `mvn jetty:run -Pproduction`.

For a full Vaadin application example, there are more choices available also from [vaadin.com/start](https://vaadin.com/start) page.
